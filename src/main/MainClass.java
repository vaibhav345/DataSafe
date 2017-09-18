package main;

import java.io.*;

import hider.Hider;
import revealer.Revealer;
import java.util.Scanner;

/**
 * Created by VaibhavMahant on 11/24/16.
 */
public class MainClass {

    static MainClass mainClass = new MainClass();

    /** Variable to check the following:
     * 1. If the file is present in the system.
     * 2. Get the location of the file.
     * 3. A counter to get the number of files that have the same name.
     */
    private boolean isFilePresent = false;
    private String fileLocation;
    private int numOfFiles = 0;
    private int menuSelected;

    public static void main(String[] args) {
        System.out.println("Select the Action you want to perform...");
        System.out.println("1. Hide Folder/File");
        System.out.println("2. Show Folder/File");
        System.out.println("3. Exit");

        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();

        while (choice <= 0 || choice > 3) {
            System.out.println("Invalid choice, enter again");
            choice = in.nextInt();
        }

        // Locking the File/Folder
        if (choice == 1) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the Folder/File name... ");
            String fileName = scan.nextLine();

            File[] paths = new File[0];

            try {
                paths = File.listRoots(); // Gets the available root directories.
            } catch (Exception e) {
                System.out.println("No Roots available");
            }

            /** Checking all the available directories for the given file.
             */
            for (File path : paths) {
                if(!mainClass.isFilePresent) {
                    findFile(fileName, new File(String.valueOf(path)));
                }
            }

            if(mainClass.isFilePresent && (mainClass.numOfFiles == 1) ){
                System.out.println("File Found.");
                Hider hider = new Hider();
                hider.hide(fileName, mainClass.fileLocation);
            }
            else if (mainClass.numOfFiles > 1)
                System.out.println("More than one file with the same name. Please give a unique name to your file!");
            else
                System.out.println("File not found. Please enter a valid Folder/File name.");
        }

        // Unlocking the File/Folder
        if (choice == 2) {
            Revealer revealer = new Revealer();
            revealer.reveal();
        }
        if (choice == 3) {
            System.out.println("Exiting....");
        }
    }

    /**
     * Searching the file in the System.
     * @param fileName
     * @param file
     */
    public static void findFile(String fileName, File file) {
        File[] list = file.listFiles();

        if (list != null) {
            for (File fil : list) {
                if (fileName.equalsIgnoreCase(fil.getName().split("\\.", 2)[0])) {
                    mainClass.isFilePresent = true;
                    mainClass.fileLocation = fil.getAbsolutePath();
                    mainClass.numOfFiles++;
                    break;
                }
                else
                    findFile(fileName, fil);
            }
        }
    }

}

