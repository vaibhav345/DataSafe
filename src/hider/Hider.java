package hider;

import dataAccses.DataAccessClass;
import file.FilesInDB;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Created by VaibhavMahant on 11/24/16.
 */
public class Hider {
    public Hider() { }

    public void hide(String fileName, String location) {
        FilesInDB file  = new FilesInDB(fileName); // File object to store in the internal database.
        DataAccessClass dataAccessClass = new DataAccessClass();

        Scanner in = new Scanner(System.in);
        String passeord;

        System.out.println("Enter password to lock this file...");
        System.out.println("1. Must be at-least 8 letters or long. " +
                "2. Should contain a digit." +
                "3. Should contain an upercase letter.");
        passeord = in.nextLine();

        while(isValid(passeord) == false) {
            System.out.println("Invalid Password, Enter again");
            passeord = in.nextLine();
        }
        if(isValid(passeord)) {
            System.out.println("Password is valid, hiding the file now...");

            file.setPassword(passeord);
            file.setLocation(location);

            dataAccessClass.storeFile(file); // Store the file object in the database.

            try {
                Path path = FileSystems.getDefault().getPath(location);

                Files.setAttribute(path, "dos:system", true);
                Files.setAttribute(path, "dos:hidden", true);

                System.out.println("File Locked");
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    /** FUnction to check if the password is valid or not.
     * @param password
     * @return
     */
    public static boolean isValid(String password) {
        boolean flagUppercase = false;
        boolean flagLowercase = false;
        boolean flagDigit = false;
        boolean flag = false;

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {

                if (!Character.isLetterOrDigit(password.charAt(i))) {
                    return false;
                }
                if (Character.isDigit(password.charAt(i)) && !flagDigit) {
                    flagDigit = true;

                }
                if (Character.isUpperCase(password.charAt(i)) && !flagUppercase) {
                    flagUppercase = true;
                }
                if (Character.isLowerCase(password.charAt(i)) && !flagLowercase) {
                    flagLowercase = true;
                }
            }
        }

        if (flagDigit && flagUppercase && flagLowercase) {
            flag = true;
        }

        return flag;
    }
}
