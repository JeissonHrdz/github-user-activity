import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static Cli cli = new Cli();

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        int opccion = 0;

        System.out.println("\n ");
        System.out.println(" <-■■■■■■■■■■■■■■■■■■■■■-| Github User Activity |■■■■■■■■■■■■■■■■■■■■■->");
        System.out.println("Please Enter the username : ");
        String userName = sc.nextLine();

        do {
            System.out.println("\n ");
            System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
            System.out.println("Menu: ");
            System.out.println("1. List All Events");
            System.out.println("2. List For Events");
            System.out.println("3. Change User");
            System.out.println("4. Exit");
            opccion = Integer.parseInt(sc.nextLine());

            switch (opccion) {
                case 1:
                    cli.listEvents(userName, "all");
                    break;
                case 2:

                    System.out.println("\n ");
                    System.out.println("Select Option: ");
                    System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
                    System.out.println("a. PushEvent");
                    System.out.println("b. DeleteEvent");
                    System.out.println("c. ForkEvent");
                    System.out.println("d. GollumEvent");
                    System.out.println("e. IssuesEvent");
                    System.out.println("f. MemberEvent");
                    System.out.println("g. PublicEvent");
                    System.out.println("h. PullRequestEvent");
                    System.out.println("i. PullRequestReviewEvent ");
                    System.out.println("j. PullRequestReviewCommentEvent");
                    System.out.println("k. ReleaseEvent");
                    System.out.println("l. RepositoryEvent");
                    System.out.println("m. StatusEvent");
                    System.out.println("n. WatchEvent");
                    System.out.println("o. CreateEvent");
                    System.out.println("---------------------------------");
                    String event = sc.nextLine();
                    cli.listActivityForEvent(userName, event);
                    break;

                case 3:
                    System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
                    System.out.println("Please Enter the username : ");
                    userName = sc.nextLine();
                    break;

                case 4:
                    System.out.println("\n ");
                    System.out.println("Exiting...");
                    break;

            }
        } while (opccion != 4);

    }
}