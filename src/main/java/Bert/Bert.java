package Bert;

import java.util.ArrayList;
import java.util.Scanner;

import storage.Storage;
import ui.Ui;
import parser.Parser;

public class Bert {

    public static ArrayList<Task> taskAL = new ArrayList<Task>();

    public static void main(String[] args) {
        String saveFilePath = "./StorageData/data.txt";
        Storage storage = new Storage(saveFilePath);

        Ui ui = new Ui();
        ui.welcomeMenu();
        storage.readFromSaveFile();

        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine().trim();
        while(!userInput.equals("bye")){
            Parser.handleCommand(userInput);
            userInput = in.nextLine().trim();
        }

        Storage.writeToSaveFile();
        Ui.exitMessage();

    }
}
