package file;

import java.io.Serializable;

/**
 * Created by Vaibhav Mahant on 9/6/2017.
 */
public class FilesInDB implements Serializable{
    private String name;
    private String password;
    private String location;

    public FilesInDB(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
