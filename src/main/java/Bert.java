import exceptions.*;

import java.util.Scanner;

public class Bert {

    private static int taskIndex;

    public static void main(String[] args) {
        String goodbyeMessage = "\tBye. Hope to see you again soon!";
        welcomeMenu();

        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        taskIndex = 0;
        while(true){
            String line = in.nextLine();
            String command = commandCheck(line);
            switch (command){
                case "bye":
                    println(goodbyeMessage);
                    return;
                case "mark":
                    try{
                        markTask(line, tasks);
                    } catch (MarkUnmarkNumberError e) {
                        println("ERROR: Number wrong");
                    } catch (MarkUnmarkItemError e) {
                        println("ERROR: Item does not exist");
                    }
                    break;
                case "unmark":
                    try{
                        unmarkTask(line, tasks);
                    } catch (MarkUnmarkNumberError e) {
                        println("ERROR: Number wrong");
                    } catch (MarkUnmarkItemError e) {
                        println("ERROR: Item does not exist");
                    }
                    break;
                case "list":
                    listTasks(taskIndex, tasks);
                    break;
                case "todo":
                case "deadline":
                case "event":
                    addTask(line, tasks);
                    break;
                default:
                    println("Invalid command");
                    break;
            }
        }
    }


    private static String cleanFrontSpacing(String line){
        return line.replaceFirst("^\\s*", "");
    }
    private static String commandCheck(String line) {
        String appendedLine = cleanFrontSpacing(line);
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

    private static void markTask(String line, Task[] tasks) throws MarkUnmarkNumberError, MarkUnmarkItemError {
        int markWordSize = "mark".length();
        String cleanLine = cleanFrontSpacing(line);
        if(cleanLine.length() <=markWordSize){
            throw new MarkUnmarkNumberError();
        }
        String markNumber = cleanLine.substring(markWordSize).trim();
        int taskNumToMark = Integer.parseInt(markNumber) - 1;
        if(taskNumToMark >= taskIndex){
            throw new MarkUnmarkItemError();
        }
        tasks[taskNumToMark].markAsDone();
        println("\tNice! I've marked this task as done:");
        println("\t" + tasks[taskNumToMark]);
    }

    private static void unmarkTask(String line, Task[] tasks) throws MarkUnmarkNumberError, MarkUnmarkItemError {
        int unmarkWordSize = "unmark".length();
        String cleanLine = cleanFrontSpacing(line);
        if(cleanLine.length() <=unmarkWordSize){
            throw new MarkUnmarkNumberError();
        }
        String unmarkNumber = cleanLine.substring(unmarkWordSize).trim();
        int taskNumToUnmark = Integer.parseInt(unmarkNumber) - 1;
        if(taskNumToUnmark >= taskIndex){
            throw new MarkUnmarkItemError();
        }
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

    public static void addTask(String line, Task[] tasks) {
        String taskType = commandCheck(line);
        String cleanLine = cleanFrontSpacing(line);
        switch (taskType) {
            case "todo":
                try{
                    addTodo(tasks, taskIndex, cleanLine);
                    ++taskIndex;
                    successfulAddMessage(tasks);
                } catch(TodoItemError e){
                    println("ERROR: Empty item in ToDo");
                }
                break;
            case "deadline":
                try
                {
                    addDeadline(tasks, taskIndex, cleanLine);
                    ++taskIndex;
                    successfulAddMessage(tasks);
                } catch (DeadlineItemError e){
                    println("ERROR: Empty item in Deadline");
                } catch (DeadlineDateError e){
                    println("ERROR: Empty date in Deadline");
                }
                break;
            case "event":
                try{
                    addEvent(tasks, taskIndex, cleanLine);
                    ++taskIndex;
                    successfulAddMessage(tasks);
                } catch(EventItemError e){
                    println("ERROR: Empty item in Event");
                } catch(EventDateError e){
                    println("ERROR: Empty date in Event");
                }
                break;
        }
    }

    private static void successfulAddMessage(Task[] tasks) {
        println("\tGot it. I've added this task:\n\t\t" + tasks[taskIndex-1]);
        println("\tNow you have " + taskIndex + " tasks in the list.");
    }

    private static void addTodo(Task[] tasks, int taskIndex, String cleanLine) throws TodoItemError {
        String item = cleanLine.substring(4).trim();
        if(item.isEmpty()) {
            throw new TodoItemError();
        }
        tasks[taskIndex] = new Todo(item);
    }

    private static void addDeadline(Task[] tasks, int taskIndex, String cleanLine)
            throws DeadlineItemError, DeadlineDateError {
        String itemCheck =cleanLine.substring(8,cleanLine.indexOf("/by")).trim();
        if(itemCheck.isEmpty()) {
            throw new DeadlineItemError();
        }
        if(!cleanLine.contains("/by")){
            throw new DeadlineDateError();
        }
        String dateCheck =cleanLine.substring(cleanLine.indexOf("/by")).trim();
        if(dateCheck.length()<=3){
            throw new DeadlineDateError();
        }
        int dividerPosition = cleanLine.indexOf("/by");
        String deadlineDescription = cleanLine.substring(8, dividerPosition).trim();
        String deadline = cleanLine.substring(dividerPosition + 3).trim();
        tasks[taskIndex] = new Deadline(deadlineDescription,deadline);
    }

    private static void addEvent(Task[] tasks, int taskIndex, String cleanLine)
            throws EventItemError,EventDateError {
        String itemCheck =cleanLine.substring(5,cleanLine.indexOf("/from")).trim();
        if(itemCheck.isEmpty()) {
            throw new EventItemError();
        }
        if(!cleanLine.contains("/from") || !cleanLine.contains("/to")){
            throw new EventDateError();
        }
        String from_DateCheck =cleanLine.substring(cleanLine.indexOf("/from"),cleanLine.indexOf("/to")).trim();
        String to_DateCheck =cleanLine.substring(cleanLine.indexOf("/to")).trim();
        if(from_DateCheck.length()<=5 ||  to_DateCheck.length()<=3){
            throw new EventDateError();
        }
        int dividerPosition = cleanLine.indexOf("/from");
        int secondDividerPosition = cleanLine.indexOf("/to",dividerPosition+1);
        String eventDescription = cleanLine.substring(5, dividerPosition).trim();
        String startTime = cleanLine.substring(dividerPosition + 5, secondDividerPosition).trim();
        String endTime = cleanLine.substring(secondDividerPosition+3).trim();
        tasks[taskIndex] = new Event(eventDescription, startTime, endTime);
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
