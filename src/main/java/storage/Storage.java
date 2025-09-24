package storage;

import Bert.Deadline;
import Bert.Event;
import Bert.Task;
import Bert.Todo;
import ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import static Bert.Bert.taskAL;

public class Storage {
    protected static String saveFilePath;
    protected static String saveFileDirectory;
    public Storage(String saveFilePath,String saveFileDirectory) {
        this.saveFilePath = saveFilePath;
        this.saveFileDirectory = saveFileDirectory;
    }
    public void readFromSaveFile() throws IOException {
        File saveFile = new File(saveFilePath);
        initializeDirectory();
        checkIfSaveFileExists(saveFile);
        Scanner s = new Scanner(saveFile); // create a Scanner using the File as the source
        if(!s.hasNext()){
            Ui.fileEmptyMessage();
        }
        while (s.hasNext()) {
            parsingFromSaveFile(s);
        }
        if(!taskAL.isEmpty()){
            Ui.fileIntializedMessage();
        }
    }
    private void initializeDirectory() {
        File d = new File(saveFileDirectory);
        if(d.exists()){
            Ui.fileDirectoryExistsMessage();
            return;
        }
        boolean dirCreated = d.mkdir();
        if(!dirCreated){
            Ui.fileDirectoryErrorMessage();
        } else {
            Ui.fileDirectoryCreatedMessage();
        }
    }
    private void checkIfSaveFileExists(File saveFile) throws IOException {
        if (saveFile.createNewFile()) {
            Ui.fileCreatedMessage();
        } else {
            Ui.fileExistsMessage();
        }
    }

    private void parsingFromSaveFile(Scanner s) {
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

    public static void writeToFile() throws IOException {
        File saveFile = new File(saveFilePath); //create file obj
        //saveFile.createNewFile(); //create file in directory
        FileWriter fw = new FileWriter(saveFilePath);
        for(Task task : taskAL)
        {
            fw.write(task.toString());
            fw.write(System.lineSeparator());
        }
        fw.close();
        Ui.fileWrittenMessage();
    }
}
