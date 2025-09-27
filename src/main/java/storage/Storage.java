package storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import Bert.Deadline;
import Bert.Event;
import Bert.Task;
import Bert.Todo;

import ui.Ui;

import static Bert.Bert.taskAL;


public class Storage {
    private static String saveFilePath;
    private static Path saveFileDirectoryPath;

    public Storage(String incomingSaveFilePath) {
        this.saveFilePath = incomingSaveFilePath;
        this.saveFileDirectoryPath = Paths.get(incomingSaveFilePath);
    }

    /**
     * Check if file exists, if file is empty or if file has valid data.
     * If file has valid data, intialize data from file to ArrayList: taskAL
     */
    public void readFromSaveFile() {
        try {
            File saveFile = new File(saveFilePath);
            if(saveFile.exists()) {
                Ui.fileFoundMessage();
                Scanner s = new Scanner(saveFile);
                if (!s.hasNext()) { //if file exists but empty
                    Ui.fileEmptyMessage();
                    return;
                }
                while (s.hasNext()) { //if file exists and have contents, import
                    parsingFromSaveFile(s);
                }
                if (!taskAL.isEmpty()) { //if file has imported all its data
                    Ui.fileIntializedMessage();
                }
                return;
            }
            Ui.fileNotFoundMessage();
        } catch (IOException e) {
            Ui.IOExceptionErrorMessage();
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
            String byDateTime =  description.substring(description.indexOf("(by:")+4).trim();
            byDateTime = byDateTime.replace(")","");
            byDateTime = byDateTime.replace(" ","T");
            LocalDateTime parsedByDateTime = LocalDateTime.parse(byDateTime);
            taskAL.add(new Deadline(taskName, parsedByDateTime));
        } else if(taskType.equalsIgnoreCase("E")){
            String taskName = description.substring(0,description.indexOf("(From:")).trim();
            String fromTime =  description.substring(description.indexOf("(From:")+6,description.indexOf("--")).trim();
            String toTime =  description.substring(description.indexOf("To:")+3).trim();
            toTime = toTime.replace(")","");

            fromTime = fromTime.replace(" ","T");
            toTime = toTime.replace(" ","T");

            LocalDateTime parsedFromTime = LocalDateTime.parse(fromTime);
            LocalDateTime parsedToTime = LocalDateTime.parse(toTime);
            taskAL.add(new Event(taskName, parsedFromTime, parsedToTime));
        }
    }

    /**
     * Called upon exit. Write all current tasks into the save file
     */
    public static void writeToSaveFile(){
        try {
            createDirectory();
            File saveFile = new File(saveFilePath);
            createFile(saveFile);

            FileWriter fw = new FileWriter(saveFilePath);
            for (Task task : taskAL) {
                fw.write(task.toString());
                fw.write(System.lineSeparator());
            }
            fw.close();
            Ui.fileWrittenMessage();
        }
        catch(IOException e){
            Ui.IOExceptionErrorMessage();
        }
    }
    private static void createDirectory() {
        if(Files.exists(saveFileDirectoryPath)) {
            return;
        }
        try {
            Files.createDirectories(saveFileDirectoryPath.getParent());
            Files.createFile(saveFileDirectoryPath);
        } catch (IOException e){
            Ui.fileDirectoryErrorMessage();
        }
    }
    private static void createFile(File saveFile){
        try{
            saveFile.createNewFile();
        } catch (IOException e) {
            Ui.fileErrorMessage();
        }
    }
}
