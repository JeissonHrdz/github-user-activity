import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;

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
        // System.out.println(jsonResponse);
        processJSON(jsonResponse);
    }

    public void processJSON(String jsonResponse) throws IOException {
        jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
        String[] Events = splitJsonObjects(jsonResponse); // jsonResponse.split("\\},\\{");
        for (String event : Events) {

            event = "{" + event + "}";

            String createdAt = "";
            String type = extractField(event, "type");
            String repoName = extractField(event, "name", "repo");
            System.out.println("Type event: " + type);
            System.out.println("Repo name: " + repoName);

            if (event.contains("commits") && !type.contains("PullRequestEvent")) {
                createdAt = extractField(event, "message", "commits", "payload");
                System.out.println("<-- Commit --> ");
                System.out.println("Message: " + createdAt);
            }
            System.out.println("..........................................................");


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
        //  System.out.println(json);
        int mainStartIndex = json.indexOf("\"" + mainField + "\":{") + mainField.length() + 3;
        int mainEndIndex = json.indexOf("}]", mainStartIndex);
        String mainJson = json.substring(mainStartIndex, (mainEndIndex + 1)) + "}]";


        int parentStartIndex = mainJson.indexOf("\"" + parentField + "\":[") + parentField.length() + 4;
        int parentEndIndex = mainJson.indexOf("]", parentStartIndex);

        String parentJson = mainJson.substring(parentStartIndex, parentEndIndex);

        return extractField(parentJson, fieldName);
    }

    private static String[] splitJsonObjects(String json) {

        java.util.ArrayList<String> objects = new java.util.ArrayList<>();
        int start = 0;
        int depth = 0;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0) {
                    objects.add(json.substring(start, i + 1));
                    start = i + 2;
                }
            }
        }
        return objects.toArray(new String[0]);
    }
}
