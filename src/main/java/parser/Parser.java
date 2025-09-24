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

    public static void handleCommand() {
        Scanner in = new Scanner(System.in);
        while(true){
            String line = in.nextLine();
            String command = commandCheck(line);
            switch (command){
                case "bye":
                    Ui.exitMessage();
                    try{
                        Storage.writeToFile();
                    } catch(IOException e){
                        println("IO: smth wrong");
                    }
                    return;
                case "mark":
                    try{
                        TaskList.markTask(line);
                    } catch (MarkUnmarkNumberError e) {
                        println("\tERROR(Mark): Number Missing");
                    } catch (MarkUnmarkItemError e) {
                        println("\tERROR(Mark): Item does not exist");
                    }
                    break;
                case "unmark":
                    try{
                        TaskList.unmarkTask(line);
                    } catch (MarkUnmarkNumberError e) {
                        println("\tERROR(Unmark): Number Missing");
                    } catch (MarkUnmarkItemError e) {
                        println("\tERROR(Unmark): Item does not exist");
                    }
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
                    try{
                        TaskList.deleteTask(line);
                    } catch (DeleteNumberError e){
                        println("\tERROR(Delete): Number Missing");
                    } catch (DeleteItemError e){
                        println("\tERROR(Delete): Item does not exist");
                    }
                    break;
                default:
                    println("\tERROR: Invalid command");
                    break;
            }
        }
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

    public static void println(String line) {
        System.out.println(line);
    }
}
