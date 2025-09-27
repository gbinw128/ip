package parser;

import ui.Ui;
import tasklist.TaskList;

public class Parser {

    public static void handleCommand(String userInput) {
        String command = commandCheck(userInput);
        switch (command) {
            case "mark":
                TaskList.markTask(userInput);
                break;
            case "unmark":
                TaskList.unmarkTask(userInput);
                break;
            case "find":
                TaskList.findTasks(userInput);
                break;
            case "list":
                TaskList.listTasks();
                break;
            case "todo":
            case "deadline":
            case "event":
                TaskList.addTask(userInput);
                break;
            case "delete":
                TaskList.deleteTask(userInput);
                break;
            default:
                Ui.invalidCommandMessage();
                break;
        }
    }

    public static String commandCheck(String line) {
        String appendedLine = line.trim().toLowerCase();
        if (appendedLine.startsWith("bye")) {
            return "bye";
        } else if (line.trim().startsWith("mark")) {
            return "mark";
        } else if (appendedLine.startsWith("unmark")) {
            return "unmark";
        } else if (appendedLine.startsWith("list")) {
            return "list";
        } else if (appendedLine.startsWith("todo")) {
            return "todo";
        } else if (appendedLine.startsWith("deadline")) {
            return "deadline";
        } else if (appendedLine.startsWith("event")) {
            return "event";
        } else if (appendedLine.startsWith("delete")) {
            return "delete";
        } else if (appendedLine.startsWith("find")) {
            return "find";
        }
        return "";
    }
}
