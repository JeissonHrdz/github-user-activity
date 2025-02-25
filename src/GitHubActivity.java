import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubActivity {

    public void getActivity(String userName) throws IOException {
        String urlString = "https://api.github.com/users/" + userName + "/events";

        //Create Conection HTTP
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        String jsonResponse = content.toString();
      //  System.out.println(jsonResponse);
        processJSON(jsonResponse);
    }

    public void processJSON(String jsonResponse) throws IOException {
        jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
        String[] Events = jsonResponse.split("\\},\\{");
        for (String event : Events) {
            event = "{" + event + "}";

            String type = extractField(event, "type");
            String repoName = extractField(event, "name","repo");
            String createdAt = extractField(event, "message","commits","payload");

            System.out.println("Type event: " + type);
            System.out.println("Repo name: " + repoName);
            System.out.println("Message: " + createdAt);
            System.out.println("----------------------------------");
        }
    }

    public String extractField(String json, String fieldName) {
        int startIndex = json.indexOf("\"" + fieldName + "\":") + fieldName.length() + 3;
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("\"", startIndex);
        }
        return json.substring(startIndex, endIndex).replace("\"", "").trim();
    }

    private String extractField(String json, String fieldName, String parentField) {
        int parentStartIndex = json.indexOf("\"" + parentField + "\":{") + parentField.length() + 3;
        int parentEndIndex = json.indexOf("}", parentStartIndex);
        String parentJson = json.substring(parentStartIndex, parentEndIndex);
        return extractField(parentJson, fieldName);
    }

    private String extractField(String json, String fieldName, String parentField, String mainField) {
        System.out.println(json);
        int mainStartIndex = json.indexOf("\"" + mainField + "\":{") + mainField.length() + 3;
        int mainEndIndex = json.indexOf("},{,", mainStartIndex);

        String mainJson = json.substring(mainStartIndex, mainEndIndex);
        System.out.println(mainJson);

        int parentStartIndex = mainJson.indexOf("\"" + parentField + "\":[{") + parentField.length() + 3;
        int parentEndIndex = mainJson.indexOf("}]", parentStartIndex);
        System.out.println(parentStartIndex + "+"+ parentEndIndex);

        String parentJson = json.substring(parentStartIndex, parentEndIndex);
        return extractField(parentJson, fieldName);
    }
}
