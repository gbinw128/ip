import java.util.Scanner;

public class Bert {

    private static int taskIndex=0;

    public static void main(String[] args) {
        String goodbyeMessage = "\tBye. Hope to see you again soon!";
        welcomeMenu();

        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        while(true){
            String line = in.nextLine();
            String command = commandCheck(line);
            switch (command){
                case "bye":
                    println(goodbyeMessage);
                    return;
                case "mark":
                    markTask(line, tasks);
                    break;
                case "unmark":
                    unmarkTask(line, tasks);
                    break;
                case "list":
                    listTasks(taskIndex, tasks);
                    break;
                case "todo":
                case "deadline":
                case "event":
                    addTask(line, tasks, taskIndex);
                    println("\tGot it. I've added this task:\n\t\t" + tasks[taskIndex]);
                    ++taskIndex;
                    println("\tNow you have " + taskIndex + " tasks in the list.");
                    break;
                default:
                    println("Invalid command");
                    break;
            }
        }
    }


    private static String cleanLine(String line){
        String tempLine = line.replaceFirst("^\\s*", "");
        return tempLine;
    }
    private static String commandCheck(String line) {
        String appendedLine = line.replaceAll("\\s","");
        if(appendedLine.startsWith("bye")){
            return "bye";
        }else if(appendedLine.startsWith("mark")){
            return "mark";
        }else if(appendedLine.startsWith("unmark")){
            return "unmark";
        }else if(appendedLine.startsWith("list")) {
            return "list";
        }else if(appendedLine.startsWith("todo")) {
            return "todo";
        }else if(appendedLine.startsWith("deadline")) {
            return "deadline";
        }else if(appendedLine.startsWith("event")) {
            return "event";
        }
        return "";
    }

    private static void welcomeMenu(){
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
        String welcomeMessage = "\tHello! I'm BERT - Bot for Echo, Response and Talk \n\tHere are the following commands:";
        String commandMessage = """
                \n\t-todo <item>
                \t-deadline <item> /by <date>
                \t-event <item> /from <date> /to <date>
                \t-mark <itemNumber>
                \t-unmark <itemNumber>
                \t-list
                \t-bye""";
        println(logo + welcomeMessage+commandMessage);
    }

    private static void markTask(String line, Task[] tasks) {
        String[] markNumber = line.split(" ");
        int taskNumToMark = Integer.parseInt(markNumber[1]) - 1;
        tasks[taskNumToMark].markAsDone();
        println("\tNice! I've marked this task as done:");
        println("\t" + tasks[taskNumToMark]);
    }

    private static void unmarkTask(String line, Task[] tasks) {
        String[] markNumber = line.split(" ");
        int taskNumToUnmark = Integer.parseInt(markNumber[1]) - 1;
        tasks[taskNumToUnmark].unmarkAsDone();
        println("\tOk, I've unmarked this task:");
        println("\t" + tasks[taskNumToUnmark]);
    }

    private static void listTasks(int taskIndex, Task[] tasks) {
        println("\tHere are the tasks in your list:");
        for(int i = 1; i < taskIndex + 1; ++i) {
            println("\t" + i + ". " + tasks[i - 1]);
        }
    }

    public static void addTask(String line, Task[] tasks,int taskIndex) {
        String taskType = commandCheck(line);
        String cleanLine = cleanLine(line);
        String description = cleanLine.substring(cleanLine.indexOf(" ") + 1);
        switch (taskType) {
            case "todo":
                addTodo(tasks, taskIndex, description);
                break;
            case "deadline":
                addDeadline(tasks, taskIndex, description);
                break;
            case "event":
                addEvent(tasks, taskIndex, description);
                break;
        }
    }

    private static void addTodo(Task[] tasks, int taskIndex, String description) {
        tasks[taskIndex] = new Todo(description);
    }

    private static void addEvent(Task[] tasks, int taskIndex, String description) {
        int dividerPosition;
        dividerPosition = description.indexOf("/from");
        int secondDividerPosition = description.indexOf("/to",dividerPosition+1);
        String eventDescription = description.substring(0, dividerPosition).trim();
        String startTime = description.substring(dividerPosition + 5, secondDividerPosition).trim();
        String endTime = description.substring(secondDividerPosition+3).trim();
        tasks[taskIndex] = new Event(eventDescription, startTime, endTime);
    }

    private static void addDeadline(Task[] tasks, int taskIndex, String description) {
        int dividerPosition;
        dividerPosition = description.indexOf("/by");
        String deadlineDescription = description.substring(0, dividerPosition).trim();
        String deadline = description.substring(dividerPosition + 3).trim();
        tasks[taskIndex] = new Deadline(deadlineDescription,deadline);
    }

    public static void println(String line) {
        System.out.println(line);
    }
    public static void print(String line) {
        System.out.print(line);
    }
    public static void pt(String test)
    {
        System.out.println("XX"+test+"XX");
    }
}
