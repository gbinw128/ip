package tasklist;

import exceptions.*;

import Bert.Deadline;
import Bert.Event;
import Bert.Task;
import Bert.Todo;

import parser.Parser;
import ui.Ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static Bert.Bert.taskAL;


public class TaskList {
    /**
     * Marks task based on input number from user,
     * then prints out the task itself to show user that it is marked
     *
     * @param line User's direct input
     */
    public static void markTask(String line) {
        try {
            int markWordSize = "mark".length();
            String cleanLine = line.trim();
            if (cleanLine.length() <= markWordSize) {
                throw new MarkUnmarkItemNumberOutOfBounds();
            }
            String markNumber = cleanLine.substring(markWordSize).trim();
            int taskNumToMark = Integer.parseInt(markNumber) - 1;
            if (taskNumToMark >= taskAL.size() || taskNumToMark < 0) {
                throw new MarkUnmarkEmptyNumber();
            }
            Task taskToMarkDone = taskAL.get(taskNumToMark);
            taskToMarkDone.markAsDone();
            Ui.markTaskMessage(taskToMarkDone);
        } catch (MarkUnmarkItemNumberOutOfBounds e) {
            Ui.missingNumberMessage("mark");
        } catch (MarkUnmarkEmptyNumber e) {
            Ui.inoperableItemMessage("mark");
        }
    }
    /**
     * Unmarks task based on input number from user,
     * then prints out the task itself to show user that it is unmarked
     *
     * @param line User's direct input
     */
    public static void unmarkTask(String line) {
        try {
            int unmarkWordSize = "unmark".length();

            String cleanLine = line.trim();
            if (cleanLine.length() <= unmarkWordSize) {
                throw new MarkUnmarkItemNumberOutOfBounds();
            }
            String unmarkNumber = cleanLine.substring(unmarkWordSize).trim();
            int taskNumToUnmark = Integer.parseInt(unmarkNumber) - 1;
            if (taskNumToUnmark >= taskAL.size() || taskNumToUnmark < 0) {
                throw new MarkUnmarkEmptyNumber();
            }
            Task taskToUnmarkDone = taskAL.get(taskNumToUnmark);
            taskToUnmarkDone.unmarkAsDone();
            Ui.unmarkTaskMessage(taskToUnmarkDone);
        } catch (MarkUnmarkItemNumberOutOfBounds e) {
            Ui.missingNumberMessage("unmark");
        } catch (MarkUnmarkEmptyNumber e) {
            Ui.inoperableItemMessage("unmark");
        }
    }
    /**
     * Find tasks based on input from user
     * then prints out all matching tasks to show user
     *
     * @param line User's direct input
     */
    public static void findTasks(String line) {
        try{
            int findWordSize = "find".length();
            String cleanLine = line.trim();
            if (cleanLine.length() <= findWordSize) {
                throw new FindEmptyKeyword();
            }
            String keyword = cleanLine.substring(findWordSize).trim();

            int[] printIndex = {1};
            boolean found = taskAL.stream()
                    .anyMatch(task -> task.getDescription().contains(keyword));

            if(!found){
                throw new FindNothingException();
            } else{
                Ui.findMatchingTaskHeaderMessage();
                taskAL.stream()
                        .filter(task -> task.getDescription().contains(keyword))
                        .forEach(task -> Ui.findAllTasksMessage(task, printIndex[0]++));
            }
        } catch (FindEmptyKeyword e) {
            Ui.emptyFindStringExceptionMessage();
        } catch (FindNothingException e) {
            Ui.findNothingMessage();
        }
    }
    /**
     * List all tasks store in ArrayList: taskAL
     */
    public static void listTasks() {
        Ui.listAllTasksMessage();
    }
    /**
     * Delete task based on input number from user,
     * then prints out the task that is being deleted to show user
     *
     * @param line User's direct input
     */
    public static void deleteTask(String line) {
        try {
            int deleteWordSize = "delete".length();
            String cleanLine = line.trim();
            if (cleanLine.length() <= deleteWordSize) {
                throw new DeleteEmptyNumber();
            }
            String deleteNumber = cleanLine.substring(deleteWordSize).trim();
            int taskNumToDelete = Integer.parseInt(deleteNumber) - 1;
            if (taskNumToDelete >= taskAL.size() || taskNumToDelete < 0) {
                throw new DeleteItemNumberOutOfBounds();
            }
            Ui.deleteTaskMessage(taskNumToDelete);
            taskAL.remove(taskNumToDelete);
        } catch (DeleteEmptyNumber e) {
            Ui.missingNumberMessage("delete");
        } catch (DeleteItemNumberOutOfBounds e) {
            Ui.inoperableItemMessage("delete");
        }
    }
    /**
     * Add task based on input from user,
     * then prints out the task itself to show user that it is added
     *
     * @param line User's direct input
     */
    public static void addTask(String line) {
        String taskType = Parser.commandCheck(line);
        String cleanLine = line.trim();
        switch (taskType) {
            case "todo":
                try {
                    addTodo(cleanLine);
                    Ui.successfulAddTaskMessage(taskAL);
                } catch (TodoEmptyItem e) {
                    Ui.emptyTodoExceptionMessage();
                }
                break;
            case "deadline":
                try {
                    addDeadline(cleanLine);
                    Ui.successfulAddTaskMessage(taskAL);
                } catch (DeadlineEmptyItem e) {
                    Ui.emptyDeadlineItemExceptionMessage();
                } catch (DeadlineEmptyDate e) {
                    Ui.emptyDeadlineDateExceptionMessage();
                } catch (DateTimeParseException e) {
                    Ui.invalidDateFormatMessage();
                }
                break;
            case "event":
                try {
                    addEvent(cleanLine);
                    Ui.successfulAddTaskMessage(taskAL);
                } catch (EventEmptyItem e) {
                    Ui.emptyEventItemExceptionMessage();
                } catch (EventEmptyDate e) {
                    Ui.emptyEventDateExceptionMessage();
                } catch (DateTimeParseException e) {
                    Ui.invalidDateFormatMessage();
                } catch (EventTimelineError e) {
                    Ui.invalidTimelineMessage();
                }
                break;
        }
    }

    private static void addTodo(String cleanLine)
            throws TodoEmptyItem {
        String item = cleanLine.substring(4).trim();
        if (item.isEmpty()) {
            throw new TodoEmptyItem();
        }
        taskAL.add(new Todo(item));
    }

    private static void addDeadline(String cleanLine)
            throws DeadlineEmptyItem, DeadlineEmptyDate, DateTimeParseException {
        int commmandLength = "deadline".length();
        int dateFormatLength = 10;
        deadlineExceptionCheck(cleanLine, commmandLength);
        int dividerPosition = cleanLine.indexOf("/by");

        String deadlineDescription = cleanLine.substring(commmandLength, dividerPosition).trim();

        String deadline = cleanLine.substring(dividerPosition + 3).trim();
        String deadlineTime = deadline.substring(dateFormatLength).trim();
        deadlineTime = addCharToString(deadlineTime, ':', 2);
        deadline = deadline.substring(0, dateFormatLength).trim();

        LocalDate parsedDeadline = LocalDate.parse(deadline);
        LocalTime parsedDeadlineTime = LocalTime.parse(deadlineTime);
        LocalDateTime parsedDeadlineDateTime = LocalDateTime.parse(parsedDeadline + "T" + parsedDeadlineTime);

        taskAL.add(new Deadline(deadlineDescription, parsedDeadlineDateTime));
    }

    private static void deadlineExceptionCheck(String cleanLine, int commandLength) {
        String itemCheck = cleanLine.substring(commandLength).trim();
        if (itemCheck.isEmpty()) {
            throw new DeadlineEmptyItem();
        }
        if (!cleanLine.contains("/by")) {
            throw new DeadlineEmptyDate();
        }
        itemCheck = cleanLine.substring(commandLength, cleanLine.indexOf("/by")).trim();
        if (itemCheck.isEmpty()) {
            throw new DeadlineEmptyItem();
        }
        String dateCheck = cleanLine.substring(cleanLine.indexOf("/by")).trim();
        if (dateCheck.length() <= 3) {
            throw new DeadlineEmptyDate();
        }
    }

    private static void addEvent(String cleanLine)
            throws EventEmptyItem, EventEmptyDate, DateTimeParseException {
        int commmandLength = "event".length();
        int dateFormatLength = 10;
        eventExceptionCheck(cleanLine, commmandLength);
        int dividerPosition = cleanLine.indexOf("/from");
        int secondDividerPosition = cleanLine.indexOf("/to", dividerPosition + 1);

        String eventDescription = cleanLine.substring(commmandLength, dividerPosition).trim();

        String startDate = cleanLine.substring(dividerPosition + commmandLength, secondDividerPosition).trim();
        String startDateTime = startDate.substring(dateFormatLength).trim();
        startDateTime = addCharToString(startDateTime, ':', 2);
        startDate = startDate.substring(0, dateFormatLength).trim();

        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalTime parsedStartTime = LocalTime.parse(startDateTime);

        String endDate = cleanLine.substring(secondDividerPosition + 3).trim();
        String endDateTime = endDate.substring(dateFormatLength).trim();
        endDateTime = addCharToString(endDateTime, ':', 2);
        endDate = endDate.substring(0, dateFormatLength).trim();

        LocalDate parsedEndDate = LocalDate.parse(endDate);
        LocalTime parsedEndTime = LocalTime.parse(endDateTime);

        LocalDateTime parsedStartDateTime = LocalDateTime.parse(parsedStartDate + "T" + parsedStartTime);
        LocalDateTime parsedEndDateTime = LocalDateTime.parse(parsedEndDate + "T" + parsedEndTime);

        if (parsedStartDate.isAfter(parsedEndDate)) {
            throw new EventTimelineError();
        }
        if(parsedStartDate.isEqual(parsedEndDate) && parsedStartTime.isAfter(parsedEndTime)) {
            throw new EventTimelineError();
        }
        taskAL.add(new Event(eventDescription, parsedStartDateTime, parsedEndDateTime));
    }
    private static void eventExceptionCheck(String cleanLine, int commandLength) {
        String itemCheck = cleanLine.substring(commandLength).trim();
        if (itemCheck.isEmpty()) {
            throw new EventEmptyItem();
        }
        if (!cleanLine.contains("/from") || !cleanLine.contains("/to")) {
            throw new EventEmptyDate();
        }
        itemCheck = cleanLine.substring(commandLength, cleanLine.indexOf("/from")).trim();
        if (itemCheck.isEmpty()) {
            throw new EventEmptyItem();
        }
        String from_DateCheck = cleanLine.substring(cleanLine.indexOf("/from"), cleanLine.indexOf("/to")).trim();
        String to_DateCheck = cleanLine.substring(cleanLine.indexOf("/to")).trim();
        if (from_DateCheck.length() <= 5 || to_DateCheck.length() <= 3) {
            throw new EventEmptyDate();
        }
    }

    private static String addCharToString(String str, char c,
                                         int pos) {

        // Creating an object of StringBuffer class
        StringBuffer stringBuffer = new StringBuffer(str);

        // insert() method where position of character to be
        // inserted is specified as in arguments
        stringBuffer.insert(pos, c);

        // Return the updated string
        // Concatenated string
        return stringBuffer.toString();
    }


}
