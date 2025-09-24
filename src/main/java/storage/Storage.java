package storage;

import Bert.Deadline;
import Bert.Event;
import Bert.Task;
import Bert.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static Bert.Bert.taskAL;

public class Storage {
    protected static String saveFilePath;
    public Storage(String saveFilePath) {
        Storage.saveFilePath = saveFilePath;
    }

    public static void writeToFile() throws IOException {
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
    public void readFromSaveFile() throws IOException {
        File saveFile = new File(saveFilePath);
        initializeDirectory();
        checkIfSaveFileExists(saveFile);
        Scanner s = new Scanner(saveFile); // create a Scanner using the File as the source
        if(!s.hasNext()){
            println("File empty, nothing initialized");
        }
        while (s.hasNext()) {
            parsingFromSaveFile(s);
        }
        if(!taskAL.isEmpty()){
            println("Tasks have been initialized.");
            //listTasks();
        }
    }
    private void checkIfSaveFileExists(File saveFile) throws IOException {
        if (saveFile.createNewFile()) {
            System.out.println("File created: " + saveFile.getName());
        } else {
            println("File already exists.");
        }
    }
    private void initializeDirectory() {
        String dirPath = "./StorageData";
        File d = new File(dirPath);
        boolean dirCreated = d.mkdir();
        if(!dirCreated){
            println("Error: could not create directory or directory exists");
        } else {
            println("Directory created");
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
    public static void println(String line) {
        System.out.println(line);
    }
}
