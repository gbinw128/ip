import java.util.Scanner;

public class Bert {
    public static void main(String[] args) {
        String welcomeMessage = " Hello! I'm BERT - Bot for Echo, Response and Talk\n" +
                " What can I do for you?";
        String goodbyeMessage = "Bye. Hope to see you again soon!";
        System.out.println(welcomeMessage);

        String line;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();

        while(!line.contentEquals("bye"))
        {
            System.out.println(line);
            line = in.nextLine();
        }

        System.out.println(goodbyeMessage);
    }
}