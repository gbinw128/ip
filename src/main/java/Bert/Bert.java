package Bert;

import exceptions.DeadlineDateError;
import exceptions.DeadlineItemError;
import exceptions.DeleteItemError;
import exceptions.DeleteNumberError;
import exceptions.EventDateError;
import exceptions.EventItemError;
import exceptions.MarkUnmarkItemError;
import exceptions.MarkUnmarkNumberError;
import exceptions.TodoItemError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

public class Bert {


    private static ArrayList<Task> taskAL = new ArrayList<Task>();
    private static String saveFilePath = "./StorageData/data.txt";

    public static void main(String[] args) {
        
        Ui ui = new Ui();
        ui.welcomeMenu();
        try{
            readFromSaveFile();
        } catch (IOException e){
            println("READERROR");
        }
        Scanner in = new Scanner(System.in);
        while(true){
            String line = in.nextLine();
            String command = commandCheck(line);
            switch (command){
                case "bye":
                    println(goodbyeMessage);
                    try{
                        writeToFile();
                    } catch(IOException e){
                        println("IO: smth wrong");
                    }
                    return;
                case "mark":
                    try{
                        markTask(line);
                    } catch (MarkUnmarkNumberError e) {
                        println("\tERROR(Mark): Number Missing");
                    } catch (MarkUnmarkItemError e) {
                        println("\tERROR(Mark): Item does not exist");
                    }
                    break;
                case "unmark":
                    try{
                        unmarkTask(line);
                    } catch (MarkUnmarkNumberError e) {
                        println("\tERROR(Unmark): Number Missing");
                    } catch (MarkUnmarkItemError e) {
                        println("\tERROR(Unmark): Item does not exist");
                    }
                    break;
                case "list":
                    listTasks();
                    break;
                case "todo":
                case "deadline":
                case "event":
                    addTask(line);
                    break;
                case "delete":
                    try{
                        deleteTask(line);
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



    private static String cleanFrontSpacing(String line){
        return line.replaceFirst("^\\s*", "");
    }
    private static String commandCheck(String line) {
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


    private static void markTask(String line)
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
    private static void unmarkTask(String line)
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
    private static void listTasks() {
        println("\tHere are the tasks in your list:");
        for(Task task : taskAL) {
            int printIndex = taskAL.indexOf(task)+1;
            println("\t\t" + printIndex + ". " + task);
        }
    }
    private static void deleteTask(String line) {
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

    private static void addTask(String line) {
        String taskType = commandCheck(line);
        String cleanLine = cleanFrontSpacing(line);
        switch (taskType) {
            case "todo":
                try{
                    addTodo(cleanLine);
                    successfulAddMessage();
                } catch(TodoItemError e){
                    println("\tERROR(Todo): Empty item in ToDo");
                }
                break;
            case "deadline":
                try
                {
                    addDeadline(cleanLine);
                    successfulAddMessage();
                } catch (DeadlineItemError e){
                    println("\tERROR(Deadline): Empty item in Deadline");
                } catch (DeadlineDateError e){
                    println("\tDeadline): Empty date in Deadline");
                }
                break;
            case "event":
                try{
                    addEvent(cleanLine);
                    successfulAddMessage();
                } catch(EventItemError e){
                    println("\tERROR(Event): Empty item in Event");
                } catch(EventDateError e){
                    println("\tERROR(Event): Empty date in Event");
                }
                break;
        }
    }
    private static void successfulAddMessage() {
        println("\tGot it. I've added this task:\n\t\t" + taskAL.get(taskAL.size()-1));
        println("\tNow you have " + taskAL.size() + " tasks in the list.");
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

    public static void println(String line) {
        System.out.println(line);
    }
    public static void print(String line) {
        System.out.print(line);
    }
    public static void pt(String test) {
        System.out.println("XX"+test+"XX");
    }

    private static void writeToFile() throws IOException {
        File f = new File(saveFilePath); //create file obj
        f.createNewFile(); //create file in directory
        System.out.println("full path: " + f.getAbsolutePath());
        System.out.println("file exists?: " + f.exists());
        FileWriter fw = new FileWriter(saveFilePath);
        for(Task task : taskAL)
        {
            fw.write(task.toString());
            fw.write(System.lineSeparator());
        }
        fw.close();
    }
    private static void readFromSaveFile() throws IOException {
        File saveFile = new File(saveFilePath);
        initializeDirectory();
        checkIfSaveFileExists(saveFile);
        Scanner s = new Scanner(saveFile); // create a Scanner using the File as the source
        while (s.hasNext()) {
            parsingFromSaveFile(s);
        }
        if(!taskAL.isEmpty()){
            println("Tasks have been initialized.");
            listTasks();
        }
    }
    private static void checkIfSaveFileExists(File saveFile) throws IOException {
        if (saveFile.createNewFile()) {
            System.out.println("File created: " + saveFile.getName());
        } else {
            println("File already exists.");
        }
    }
    private static void initializeDirectory() {
        String dirPath = "./StorageData";
        File d = new File(dirPath);
        boolean dirCreated = d.mkdir();
        if(!dirCreated){
            println("Error: could not create directory or directory exists");
        } else {
            println("Directory created");
        }
    }

    private static void parsingFromSaveFile(Scanner s) {
        String line = s.nextLine();
        String taskType = line.substring(1,2);
        String description = line.substring(7);

        if(taskType.equalsIgnoreCase("T")){
            taskAL.add(new Todo(description));
        }
        else if(taskType.equalsIgnoreCase("D")){
            String taskName = description.substring(0,description.indexOf("(by:")).trim();
            String byDate =  description.substring(description.indexOf("(by:")+4).trim();
            byDate = byDate.replace(")","");
            taskAL.add(new Deadline(taskName, byDate));
        } else if(taskType.equalsIgnoreCase("E")){
            String taskName = description.substring(0,description.indexOf("(From:")).trim();
            String fromTime =  description.substring(description.indexOf("(From:")+6,description.indexOf("--")).trim();
            String toTime =  description.substring(description.indexOf("To:")+3).trim();
            toTime = toTime.replace(")","");
            taskAL.add(new Event(taskName, fromTime, toTime));
        }
    }
}
