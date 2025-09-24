package parser;

import exceptions.DeleteItemError;
import exceptions.DeleteNumberError;
import exceptions.MarkUnmarkItemError;
import exceptions.MarkUnmarkNumberError;

import java.io.IOException;
import java.util.Scanner;

import storage.Storage;
import ui.Ui;
import tasklist.TaskList;

public class Parser {

    public static void handleCommand(String userInput) {

            String command = commandCheck(userInput);
            switch (command){
                case "mark":
                    try{
                        TaskList.markTask(userInput);
                    } catch (MarkUnmarkNumberError e) {
                        Ui.missingNumberMessage("mark");
                    } catch (MarkUnmarkItemError e) {
                        Ui.inoperableItemMessage("mark");
                    }
                    break;
                case "unmark":
                    try{
                        TaskList.unmarkTask(userInput);
                    } catch (MarkUnmarkNumberError e) {
                        Ui.missingNumberMessage("unmark");
                    } catch (MarkUnmarkItemError e) {
                        Ui.inoperableItemMessage("unmark");
                    }
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
                    try{
                        TaskList.deleteTask(userInput);
                    } catch (DeleteNumberError e){
                        Ui.missingNumberMessage("delete");
                    } catch (DeleteItemError e){
                        Ui.inoperableItemMessage("delete");
                    }
                    break;
                default:
                    Ui.invalidCommand();
                    break;
            }
        //}
    }

    public static String commandCheck(String line) {
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
        }else if(appendedLine.startsWith("delete")) {
            return "delete";
        }
        return "";
    }

    private static String cleanFrontSpacing(String line){
        return line.replaceFirst("^\\s*", "");
    }
}
