package ui;

import Bert.Task;
import tasklist.TaskList;

import java.util.ArrayList;

public class Ui { //deals with interactions with the user
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

    public static void fileEmptyMessage(){
        println("\tFile empty, nothing initialized");
    }
    public static void fileIntializedMessage() {
        println("\tTasks have been initialized. Use 'list' to see.");
    }
    public static void fileDirectoryExistsMessage() {
        println("\tDirectory exists.");
    }
    public static void fileDirectoryErrorMessage() {
        println("\tError: could not create directory");
    }
    public static void fileDirectoryCreatedMessage() {
        println("\tDirectory created.");
    }
    public static void fileCreatedMessage() {
        println("\tFile created.");
    }
    public static void fileExistsMessage() {
        println("\tFile already exists.");
    }
    public static void fileWrittenMessage() {
        println("\tTasks have been written to the file.");
    }

    public static void successfulAddTaskMessage(ArrayList<Task> taskAL) {
        println("\tGot it. I've added this task:\n\t\t" + taskAL.get(taskAL.size()-1));
        println("\tNow you have " + taskAL.size() + " tasks in the list.");
    }

    public static void println(String line) {
        System.out.println(line);
    }
    public static void print(String line) {
        System.out.print(line);
    }


}
