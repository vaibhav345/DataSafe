package dataAccses;

import file.FilesInDB;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vaibhav Mahant on 9/14/2017.
 */
public class DataAccessClass {
    public DataAccessClass() { }

    public void storeFile(FilesInDB file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("database/filesLocked.txt", true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(file);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    public void sotreMultiple(List<FilesInDB> filesInDBList) {
        /** Clear the file.
         */
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("database/filesLocked.txt"));
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }

        for(FilesInDB file : filesInDBList) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File("database/filesLocked.txt"), true);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                objectOutputStream.writeObject(file);

                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                System.out.println("Error initializing stream");
            }
        }
    }

    public List<FilesInDB> getFiles() {
        List<FilesInDB> filesInDB = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(new File("database/filesLocked.txt"));

            FilesInDB file = null;
            do {
                try {
                    /** Opening the inputStream to solve the StreamCorruptException.
                     */
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    file = (FilesInDB) objectInputStream.readObject();
                    if(file != null) {
                        filesInDB.add(file);
                    }
                } catch (EOFException e) {
                    break;
                }
            } while(file != null);

            fileInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return filesInDB;
    }
}
