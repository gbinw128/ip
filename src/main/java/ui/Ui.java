package ui;

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

    public static void println(String line) {
        System.out.println(line);
    }
    public static void print(String line) {
        System.out.print(line);
    }
}
