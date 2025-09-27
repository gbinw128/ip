package parser;

import ui.Ui;
import tasklist.TaskList;

public class Parser {

    /**
     * Read's user input and run different switch case depending on command by user
     *
     * @param line User's direct input
     */
    public static void handleCommand(String line) {
        String command = commandCheck(line);
        switch (command) {
            case "mark":
                TaskList.markTask(line);
                break;
            case "unmark":
                TaskList.unmarkTask(line);
                break;
            case "find":
                TaskList.findTasks(line);
                break;
            case "list":
                TaskList.listTasks();
                break;
            case "todo":
            case "deadline":
            case "event":
                TaskList.addTask(line);
                break;
            case "delete":
                TaskList.deleteTask(line);
                break;
            default:
                Ui.invalidCommandMessage();
                break;
        }
    }

    /**
     * Return different command strings after verifying the first word of the user's input
     *
     * @param line User's direct input
     */
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
