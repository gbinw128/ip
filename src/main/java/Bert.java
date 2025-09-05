import java.util.Scanner;

public class Bert {
    private static void unmarkTask(String line, Task[] tasks) {
        String[] markNumber = line.split(" ");
        int taskNumToUnmark = Integer.parseInt(markNumber[1]) - 1;
        tasks[taskNumToUnmark].unmarkAsDone();
        System.out.println("\tOk, I've unmarked this task:");
        System.out.println("\t" + tasks[taskNumToUnmark]);
    }

    private static void markTask(String line, Task[] tasks) {
        String[] markNumber = line.split(" ");
        int taskNumToMark = Integer.parseInt(markNumber[1]) - 1;
        tasks[taskNumToMark].markAsDone();
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t" + tasks[taskNumToMark]);
    }

    private static void listTasks(int taskIndex, Task[] tasks) {
        System.out.println("\tHere are the tasks in your list:");
        for(int i = 1; i < taskIndex + 1; ++i) {
            System.out.println("\t" + i + ". " + tasks[i - 1]);
        }
    }
    private static void welcomeMenu(){
        String welcomeMessage = "\t Hello! I'm BERT - Bot for Echo, Response and Talk\n\t What can I do for you?";
        String logo= " .────────────────.  .────────────────.  .────────────────.  .────────────────. \n" +
                "│ .──────────────. ││ .──────────────. ││ .──────────────. ││ .──────────────. │\n" +
                "│ │   ______     │ ││ │  _________   │ ││ │  _______     │ ││ │  _________   │ │\n" +
                "│ │  │_   _ ╲    │ ││ │ │_   ___  │  │ ││ │ │_   __ ╲    │ ││ │ │  _   _  │  │ │\n" +
                "│ │    │ │_) │   │ ││ │   │ │_  ╲_│  │ ││ │   │ │__) │   │ ││ │ │_╱ │ │ ╲_│  │ │\n" +
                "│ │    │  __'.   │ ││ │   │  _│  _   │ ││ │   │  __ ╱    │ ││ │     │ │      │ │\n" +
                "│ │   _│ │__) │  │ ││ │  _│ │___╱ │  │ ││ │  _│ │  ╲ ╲_  │ ││ │    _│ │_     │ │\n" +
                "│ │  │_______╱   │ ││ │ │_________│  │ ││ │ │____│ │___│ │ ││ │   │_____│    │ │\n" +
                "│ │              │ ││ │              │ ││ │              │ ││ │              │ │\n" +
                "│ '──────────────' ││ '──────────────' ││ '──────────────' ││ '──────────────' │\n" +
                " '────────────────'  '────────────────'  '────────────────'  '────────────────'\n";
        System.out.println(logo + welcomeMessage);
    }

    public static void main(String[] args) {

        welcomeMenu();

        String goodbyeMessage = "\t Bye. Hope to see you again soon!";
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        Task[] tasks = new Task[100];

        for(int taskIndex = 0; !line.contentEquals("bye"); line = in.nextLine()) {
            if (line.contentEquals("list")) {
                listTasks(taskIndex, tasks);
            } else if (line.startsWith("mark")) {
                markTask(line, tasks);
            } else if (line.startsWith("unmark")) {
                unmarkTask(line, tasks);
            } else if (line.startsWith("todo")) {
                tasks[taskIndex] = new Todo(line);
                ++taskIndex;
                System.out.println("\tadded " + line);
            } else if (line.startsWith("deadline")) {
                int dividerPosition = line.indexOf("/");
                String deadlineDescription = line.substring(0, dividerPosition).trim();
                String deadline = line.substring(dividerPosition + 1);
                tasks[taskIndex] = new Deadline(deadlineDescription,deadline);
                ++taskIndex;
                System.out.println("\tadded " + line);
            } else if (line.startsWith("event")) {
                int dividerPosition = line.indexOf("/");
                int secondDividerPosition = line.indexOf("/",dividerPosition+1);

                String deadlineDescription = line.substring(0, dividerPosition).trim();
                String startTime = line.substring(dividerPosition + 1, secondDividerPosition).trim();
                String endTime = line.substring(secondDividerPosition+1).trim();

                tasks[taskIndex] = new Event(deadlineDescription, startTime, endTime);
                ++taskIndex;
                System.out.println("\tadded " + line);
            } else {
                tasks[taskIndex] = new Task(line);
                ++taskIndex;
                System.out.println("\tadded " + line);
            }
        }
        System.out.println(goodbyeMessage);
    }
}
