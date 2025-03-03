import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;

public class GitHubActivity {

    public void getActivity(String userName,String eventOp) {
        String urlString = "https://api.github.com/users/" + userName + "/events";

        try {
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
        processJSON(jsonResponse, eventOp);
    } catch (Exception e) {
            System.out.println("This user not exist or not have activity "+ e.getMessage());
        }
    }

    public void processJSON(String jsonResponse, String eventOp){
        jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
        String[] Events = splitJsonObjects(jsonResponse); // jsonResponse.split("\\},\\{");
        boolean errorStatus = false;

        for (String event : Events) {
            event = "{" + event + "}";
            String createdAt = extractField(event, "created_at");
            String type = extractField(event, "type");
            String repoName = extractField(event, "name", "repo");

            if (type.equals(eventOp) || eventOp.equals("all")) {

                System.out.println("Type event: " + type);
                System.out.println("Repo name: " + repoName);
                System.out.println("Created at: " + createdAt);

                if (event.contains("commits") && !type.contains("PullRequestEvent")) {
                    String message = extractField(event, "message", "commits", "payload");
                    System.out.println("<-- Commit --> ");
                    System.out.println("" + message);
                }
                System.out.println("..........................................................");
            } else if(!errorStatus){
                System.out.println("Not event activity");
                errorStatus = true;
            }
        }
    }

    public String extractField(String json, String fieldName) {

        int startIndex = json.indexOf("\"" + fieldName + "\":") + fieldName.length() +3;
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }
        return json.substring(startIndex, endIndex).replace("\"", "").trim();
    }

    private String extractField(String json, String fieldName, String parentField) {

        int parentStartIndex = json.indexOf("\"" + parentField + "\":{") + parentField.length() + 3;
        int parentEndIndex = json.indexOf("}", parentStartIndex);
        String parentJson = json.substring(parentStartIndex, parentEndIndex);

        return extractField(parentJson+"}", fieldName);
    }

    private String extractField(String json, String fieldName, String parentField, String mainField) {

        int mainStartIndex = json.indexOf("\"" + mainField + "\":{") + mainField.length() + 3;
        int mainEndIndex = json.indexOf("}]", mainStartIndex);
        String mainJson = json.substring(mainStartIndex, (mainEndIndex + 1)) + "}]";

        int parentStartIndex = mainJson.indexOf("\"" + parentField + "\":[") + parentField.length() + 4;
        int parentEndIndex = mainJson.indexOf("]", parentStartIndex);

        String parentJson = mainJson.substring(parentStartIndex, parentEndIndex);
        //System.out.println(parentJson);
        String messages = "";
        String[] commitMessages = splitJsonObjects(parentJson);
        for (String commitMessage : commitMessages) {
            messages += "Author: " + extractField(commitMessage, "name","author") + "\n" +
                    "Message: "+  extractField(commitMessage, fieldName) + "\n";
        }
    return  messages;
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
