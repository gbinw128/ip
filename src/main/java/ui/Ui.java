package ui;

import Bert.Task;
import tasklist.TaskList;

import java.util.ArrayList;

import static Bert.Bert.taskAL;

public class Ui { //deals with interactions with the user
    public static void println(String line) {
        System.out.println(line);
    }
    public static void print(String line) {
        System.out.print(line);
    }

    public static void welcomeMenu(){
        String logo= """
                 .────────────────.  .────────────────.  .────────────────.  .────────────────.
                │ .──────────────. ││ .──────────────. ││ .──────────────. ││ .──────────────. │
                │ │   ______     │ ││ │  _________   │ ││ │  _______     │ ││ │  _________   │ │
                │ │  │_   _ ╲    │ ││ │ │_   ___  │  │ ││ │ │_   __ ╲    │ ││ │ │  _   _  │  │ │
                │ │    │ │_) │   │ ││ │   │ │_  ╲_│  │ ││ │   │ │__) │   │ ││ │ │_╱ │ │ ╲_│  │ │
                │ │    │  __'.   │ ││ │   │  _│  _   │ ││ │   │  __ ╱    │ ││ │     │ │      │ │
                │ │   _│ │__) │  │ ││ │  _│ │___╱ │  │ ││ │  _│ │  ╲ ╲_  │ ││ │    _│ │_     │ │
                │ │  │_______╱   │ ││ │ │_________│  │ ││ │ │____│ │___│ │ ││ │   │_____│    │ │
                │ │              │ ││ │              │ ││ │              │ ││ │              │ │
                │ '──────────────' ││ '──────────────' ││ '──────────────' ││ '──────────────' │
                 '────────────────'  '────────────────'  '────────────────'  '────────────────'
                """;
        String welcomeMessage = "\tHello! I'm BERT - Bot for Echo, Response and Talk" +
                "\n\tHere are the following commands:";
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
    public static void exitMessage(){
        String goodbyeMessage = "\tBye. Hope to see you again soon!";
        println(goodbyeMessage);
    }

    public static void fileErrorMessage() {
        println("Error: could not create file");
    }
    public static void fileDirectoryErrorMessage() {
        println("\tError: could not create directory");
    }
    public static void fileEmptyMessage(){
        println("\tFile empty, nothing initialized");
    }
    public static void fileIntializedMessage() {
        println("\tTasks have been initialized. Use 'list' to see.");
    }
    public static void fileWrittenMessage() {
        println("\tTasks have been written to the file.");
    }
    public static void fileNotFoundMessage() {
        println("Save File not Found");
    }
    public static void fileFoundMessage() {
        println("Save File Found");
    }

    public static void successfulAddTaskMessage(ArrayList<Task> taskAL) {
        println("\tGot it. I've added this task:\n\t\t" + taskAL.get(taskAL.size()-1));
        println("\tNow you have " + taskAL.size() + " tasks in the list.");
    }
    public static void listAllTasksMessage() {
        println("\tHere are the tasks in your list:");
        for(Task task : taskAL) {
            int printIndex = taskAL.indexOf(task)+1;
            println("\t\t" + printIndex + ". " + task);
        }
    }
    public static void markTaskMessage(Task taskToMarkDone) {
        println("\tNice! I've marked this task as done:");
        println("\t\t" + taskToMarkDone);
    }
    public static void unmarkTaskMessage(Task taskToUnmarkDone) {
        println("\tOk, I've unmarked this task:");
        println("\t\t" + taskToUnmarkDone);
    }
    public static void deleteTaskMessage(int taskNumToDelete) {
        println("\tUnderstood, I've Deleted this task:");
        println("\t\t" + taskAL.get(taskNumToDelete));
    }

    public static void emptyTodoExceptionMessage() {
        println("\tERROR(Todo): Empty item in ToDo");
    }
    public static void emptyDeadlineItemExceptionMessage() {
        println("\tERROR(Deadline): Empty item in Deadline");
    }
    public static void emptyDeadlineDateExceptionMessage() {
        println("\tERROR(Deadline): Empty date in Deadline");
    }
    public static void emptyEventDateExceptionMessage() {
        println("\tERROR(Event): Empty date in Event");
    }
    public static void emptyEventItemExceptionMessage() {
        println("\tERROR(Event): Empty item in Event");
    }

    public static void missingNumberMessage(String commandType) {
        println("\tERROR(" + commandType + ") : Number Missing");
    }
    public static void inoperableItemMessage(String commandType) {
        println("\tERROR(" + commandType + "): Item does not exist");
    }
    public static void invalidCommand() {
        println("\tERROR: Invalid command");
    }

    public static void IOExceptionErrorMessage() {
        println("IO: something wrong");
    }
}
