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
import parser.Parser;

public class Bert {

    public static ArrayList<Task> taskAL = new ArrayList<Task>();

    public static void main(String[] args) {
        String saveFileDirectory = "./StorageData";
        String saveFilePath = "./StorageData/data.txt";
        Storage storage = new Storage(saveFilePath,saveFileDirectory);


        Ui ui = new Ui();
        ui.welcomeMenu();
        storage.readFromSaveFile();

        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine().trim();
        while(!userInput.equals("bye")){
            Parser.handleCommand(userInput);
            userInput = in.nextLine().trim();
        }

        Storage.writeToFile();
        Ui.exitMessage();

    }
}
