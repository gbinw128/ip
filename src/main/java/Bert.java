import java.util.Scanner;

public class Bert {
    private static void unmarkTask(String line, Task[] tasks) {
        String[] markNumber = line.split(" ");
        int taskNumToUnmark = Integer.parseInt(markNumber[1]) - 1;
        tasks[taskNumToUnmark].unmarkAsDone();
        println("\tOk, I've unmarked this task:");
        println("\t" + tasks[taskNumToUnmark]);
    }

    private static void markTask(String line, Task[] tasks) {
        String[] markNumber = line.split(" ");
        int taskNumToMark = Integer.parseInt(markNumber[1]) - 1;
        tasks[taskNumToMark].markAsDone();
        println("\tNice! I've marked this task as done:");
        println("\t" + tasks[taskNumToMark]);
    }

    private static void listTasks(int taskIndex, Task[] tasks) {
        println("\tHere are the tasks in your list:");
        for(int i = 1; i < taskIndex + 1; ++i) {
            println("\t" + i + ". " + tasks[i - 1]);
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
        println(logo + welcomeMessage);
    }
    public static void addTask(String line, Task[] tasks,int taskIndex) {
        String taskType = line.split(" ")[0];
        String description = line.substring(line.indexOf(" ") + 1);
        int dividerPosition;
        switch (taskType) {
            case "todo":
                tasks[taskIndex] = new Todo(description);
                break;
            case "deadline":
                dividerPosition = description.indexOf("/");
                String deadlineDescription = description.substring(0, dividerPosition).trim();
                String deadline = description.substring(dividerPosition + 1);
                tasks[taskIndex] = new Deadline(deadlineDescription,deadline);
                break;
            case "event":
                dividerPosition = description.indexOf("/");
                int secondDividerPosition = description.indexOf("/",dividerPosition+1);
                String eventDescription = description.substring(0, dividerPosition).trim();
                String startTime = description.substring(dividerPosition + 1, secondDividerPosition).trim();
                String endTime = description.substring(secondDividerPosition+1).trim();
                tasks[taskIndex] = new Event(eventDescription, startTime, endTime);
                break;
        }
        println("\tadded " + description + " as " + taskType + " type");
    }
    public static void println(String line) {
        System.out.println(line);
    }
    public static void print(String line) {
        System.out.print(line);
    }

    public static void main(String[] args) {

        welcomeMenu();

        String goodbyeMessage = "\t Bye. Hope to see you again soon!";
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskIndex=0;

        while(true){
            String line = in.nextLine();
            String command = line.split(" ")[0];
            switch (command){
                default:
                    println("Invalid command");
                    break;
                case "bye":
                    println(goodbyeMessage);
                    return;
                case "list":
                    listTasks(taskIndex, tasks);
                    break;
                case "mark":
                    markTask(line, tasks);
                    break;
                case "unmark":
                    unmarkTask(line, tasks);
                    break;
                case "todo":
                case "deadline":
                case "event":
                    addTask(line, tasks, taskIndex);
                    ++taskIndex;
                    break;
            }
        }
    }
}
