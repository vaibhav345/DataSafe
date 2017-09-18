package revealer;

import dataAccses.DataAccessClass;
import file.FilesInDB;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by VaibhavMahant on 11/24/16.
 */
public class Revealer {
    public Revealer() {
    }

    public void reveal() {
        List<FilesInDB> files = new ArrayList<>();

        DataAccessClass dataAccessClass = new DataAccessClass();
        files = dataAccessClass.getFiles();

        if(files.isEmpty()) {
            System.out.println("There are no files to unlock!");
        }
        else {
            System.out.println("Files available to unlock : ");

            for (FilesInDB file: files) {
                System.out.println(file.getName());
            }
            System.out.println("Enter the file name to unlock: ");

            Scanner in = new Scanner(System.in);
            String fileName = in.nextLine();

            FilesInDB fileToRemove = null;

            for (FilesInDB file : files) {
                if(fileName.equals(file.getName())) {

                    System.out.println("Enter Password : ");
                    String password = in.nextLine();

                    if(file.getPassword().equals(password)) {
                        try {
                            fileToRemove = file;
                            Path path = FileSystems.getDefault().getPath(file.getLocation());

                            Files.setAttribute(path, "dos:system", false);
                            Files.setAttribute(path, "dos:hidden", false);

                            System.out.println("Password Matched, File unlocked");

                        } catch (IOException e) {
                            e.getStackTrace();
                        }
                    }
                    else
                        System.out.println("Password Incorrect!");
                }
            }
            files.remove(fileToRemove);

            dataAccessClass.sotreMultiple(files);
        }
    }
}