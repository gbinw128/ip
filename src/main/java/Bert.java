import java.util.Scanner;

public class Bert {
    public static void main(String[] args) {
        String welcomeMessage = "\t Hello! I'm BERT - Bot for Echo, Response and Talk\n" +
                "\t What can I do for you?";
        String goodbyeMessage = "\t Bye. Hope to see you again soon!";
        System.out.println(welcomeMessage);

        String line;
        Scanner in = new Scanner(System.in);
        line = in.nextLine();

        String[] list = new String[100];
        int listIndex = 0;

        while(!line.contentEquals("bye"))
        {
            if(line.contentEquals("list"))
            {
                for(int i = 0; i < listIndex; i++)
                {
                    System.out.println("\t"+i+". " + list[i]);
                }
            }
            else
            {
                list[listIndex] = line;
                listIndex++;
                System.out.println("\tadded " + line);
            }
            line = in.nextLine();
        }

        System.out.println(goodbyeMessage);
    }
}