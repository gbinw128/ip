package tasklist;

import java.util.ArrayList;

import exceptions.DeadlineDateError;
import exceptions.DeadlineItemError;
import exceptions.DeleteItemError;
import exceptions.DeleteNumberError;
import exceptions.EventDateError;
import exceptions.EventItemError;
import exceptions.MarkUnmarkItemError;
import exceptions.MarkUnmarkNumberError;
import exceptions.TodoItemError;

import Bert.Deadline;
import Bert.Event;
import Bert.Task;
import Bert.Todo;

import parser.Parser;
import ui.Ui;

import static Bert.Bert.taskAL;


public class TaskList {

    public static void markTask(String line)
            throws MarkUnmarkNumberError, MarkUnmarkItemError {
        int markWordSize = "mark".length();
        String cleanLine = cleanFrontSpacing(line).trim();
        if(cleanLine.length() <=markWordSize){
            throw new MarkUnmarkNumberError();
        }
        String markNumber = cleanLine.substring(markWordSize).trim();
        int taskNumToMark = Integer.parseInt(markNumber) - 1;
        if(taskNumToMark >= taskAL.size()|| taskNumToMark < 0){
            throw new MarkUnmarkItemError();
        }
        Task taskToMarkDone =taskAL.get(taskNumToMark);
        taskToMarkDone.markAsDone();
        println("\tNice! I've marked this task as done:");
        println("\t\t" + taskToMarkDone);
    }
    public static void unmarkTask(String line)
            throws MarkUnmarkNumberError, MarkUnmarkItemError {
        int unmarkWordSize = "unmark".length();
        String cleanLine = cleanFrontSpacing(line).trim();
        if(cleanLine.length() <=unmarkWordSize){
            throw new MarkUnmarkNumberError();
        }
        String unmarkNumber = cleanLine.substring(unmarkWordSize).trim();
        int taskNumToUnmark = Integer.parseInt(unmarkNumber) - 1;
        if(taskNumToUnmark >= taskAL.size()  || taskNumToUnmark < 0){
            throw new MarkUnmarkItemError();
        }
        Task taskToUnnarkDone = taskAL.get(taskNumToUnmark);
        taskToUnnarkDone.unmarkAsDone();
        println("\tOk, I've unmarked this task:");
        println("\t\t" + taskToUnnarkDone);
    }
    public static void listTasks() {
        println("\tHere are the tasks in your list:");
        for(Task task : taskAL) {
            int printIndex = taskAL.indexOf(task)+1;
            println("\t\t" + printIndex + ". " + task);
        }
    }
    public static void deleteTask(String line) {
        int deleteWordSize = "delete".length();
        String cleanLine = cleanFrontSpacing(line).trim();
        if(cleanLine.length() <=deleteWordSize){
            throw new DeleteNumberError();
        }
        String deleteNumber = cleanLine.substring(deleteWordSize).trim();
        int taskNumToDelete = Integer.parseInt(deleteNumber) - 1;
        if(taskNumToDelete >= taskAL.size() || taskNumToDelete < 0){
            throw new DeleteItemError();
        }
        println("\tUnderstood, I've Deleted this task:");
        println("\t\t" + taskAL.get(taskNumToDelete));
        taskAL.remove(taskNumToDelete);
    }

    public static void addTask(String line) {
        String taskType = Parser.commandCheck(line);
        String cleanLine = cleanFrontSpacing(line);
        switch (taskType) {
            case "todo":
                try{
                    addTodo(cleanLine);
                    Ui.successfulAddTaskMessage(taskAL);
                } catch(TodoItemError e){
                    println("\tERROR(Todo): Empty item in ToDo");
                }
                break;
            case "deadline":
                try
                {
                    addDeadline(cleanLine);
                    Ui.successfulAddTaskMessage(taskAL);
                } catch (DeadlineItemError e){
                    println("\tERROR(Deadline): Empty item in Deadline");
                } catch (DeadlineDateError e){
                    println("\tDeadline): Empty date in Deadline");
                }
                break;
            case "event":
                try{
                    addEvent(cleanLine);
                    Ui.successfulAddTaskMessage(taskAL);
                } catch(EventItemError e){
                    println("\tERROR(Event): Empty item in Event");
                } catch(EventDateError e){
                    println("\tERROR(Event): Empty date in Event");
                }
                break;
        }
    }

    private static void addTodo(String cleanLine)
            throws TodoItemError {
        String item = cleanLine.substring(4).trim();
        if(item.isEmpty()) {
            throw new TodoItemError();
        }
        taskAL.add(new Todo(item));
    }

    private static void addDeadline(String cleanLine)
            throws DeadlineItemError, DeadlineDateError {
        int commmandLength = "deadline".length();
        deadlineExceptionCheck(cleanLine, commmandLength);
        int dividerPosition = cleanLine.indexOf("/by");
        String deadlineDescription = cleanLine.substring(commmandLength, dividerPosition).trim();
        String deadline = cleanLine.substring(dividerPosition + 3).trim();
        taskAL.add(new Deadline(deadlineDescription,deadline));
    }
    private static void deadlineExceptionCheck(String cleanLine, int commandLength) {
        String itemCheck =cleanLine.substring(commandLength).trim();
        if(itemCheck.isEmpty()) {
            throw new DeadlineItemError();
        }
        if(!cleanLine.contains("/by")){
            throw new DeadlineDateError();
        }
        itemCheck = cleanLine.substring(commandLength, cleanLine.indexOf("/by")).trim();
        if(itemCheck.isEmpty()) {
            throw new DeadlineItemError();
        }
        String dateCheck = cleanLine.substring(cleanLine.indexOf("/by")).trim();
        if(dateCheck.length()<=3){
            throw new DeadlineDateError();
        }
    }

    private static void addEvent(String cleanLine)
            throws EventItemError,EventDateError {
        int commmandLength = "event".length();
        eventExceptionCheck(cleanLine, commmandLength);
        int dividerPosition = cleanLine.indexOf("/from");
        int secondDividerPosition = cleanLine.indexOf("/to",dividerPosition+1);
        String eventDescription = cleanLine.substring(commmandLength, dividerPosition).trim();
        String startTime = cleanLine.substring(dividerPosition + commmandLength, secondDividerPosition).trim();
        String endTime = cleanLine.substring(secondDividerPosition+3).trim();
        taskAL.add(new Event(eventDescription, startTime, endTime));
    }
    private static void eventExceptionCheck(String cleanLine, int commandLength) {
        String itemCheck = cleanLine.substring(commandLength).trim();
        if(itemCheck.isEmpty()) {
            throw new EventItemError();
        }
        if(!cleanLine.contains("/from") || !cleanLine.contains("/to")){
            throw new EventDateError();
        }
        itemCheck = cleanLine.substring(commandLength, cleanLine.indexOf("/from")).trim();
        if(itemCheck.isEmpty()) {
            throw new EventItemError();
        }
        String from_DateCheck = cleanLine.substring(cleanLine.indexOf("/from"), cleanLine.indexOf("/to")).trim();
        String to_DateCheck = cleanLine.substring(cleanLine.indexOf("/to")).trim();
        if(from_DateCheck.length()<=5 ||  to_DateCheck.length()<=3){
            throw new EventDateError();
        }
    }

    private static String cleanFrontSpacing(String line){
        return line.replaceFirst("^\\s*", "");
    }

    public static void println(String line) {
        System.out.println(line);
    }

}
