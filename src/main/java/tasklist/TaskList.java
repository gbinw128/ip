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
        Ui.markTaskMessage(taskToMarkDone);
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
        Task taskToUnmarkDone = taskAL.get(taskNumToUnmark);
        taskToUnmarkDone.unmarkAsDone();
        Ui.unmarkTaskMessage(taskToUnmarkDone);
    }

    public static void listTasks() {
        Ui.listAllTasksMessage();
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
        Ui.deleteTaskMessage(taskNumToDelete);
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
                    Ui.emptyTodoExceptionMessage();
                }
                break;
            case "deadline":
                try
                {
                    addDeadline(cleanLine);
                    Ui.successfulAddTaskMessage(taskAL);
                } catch (DeadlineItemError e){
                    Ui.emptyDeadlineItemExceptionMessage();
                } catch (DeadlineDateError e){
                    Ui.emptyDeadlineDateExceptionMessage();
                }
                break;
            case "event":
                try{
                    addEvent(cleanLine);
                    Ui.successfulAddTaskMessage(taskAL);
                } catch(EventItemError e){
                    Ui.emptyEventItemExceptionMessage();
                } catch(EventDateError e){
                    Ui.emptyEventDateExceptionMessage();
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


}
