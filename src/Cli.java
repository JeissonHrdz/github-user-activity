import java.io.IOException;

public class Cli {
    GitHubActivity gitHubActivity = new GitHubActivity();
    public void listEvents(String userName,String event) throws IOException {
        gitHubActivity.getActivity(userName,event);
    }

    public void listActivityForEvent(String userName,String event) throws IOException {
        String EventSelected = switch (event) {
            case "a" -> "PushEvent";
            case "b" -> "DeleteEvent";
            case "c" -> "ForkEvent";
            case "d" -> "GollumEvent";
            case "e" -> "IssuesEvent";
            case "f" -> "MemberEvent";
            case "g" -> "PublicEvent";
            case "h" -> "PullRequestEvent";
            case "i" -> "PullRequestReviewEvent";
            case "j" -> "PullRequestReviewCommentEvent";
            case "k" -> "ReleaseEvent";
            case "l" -> "RepositoryEvent";
            case "m" -> "StatusEvent";
            case "n" -> "WatchEvent";
            case "o" -> "CreateEvent";
            default -> "";
        };
        gitHubActivity.getActivity(userName,EventSelected);


    }

}
