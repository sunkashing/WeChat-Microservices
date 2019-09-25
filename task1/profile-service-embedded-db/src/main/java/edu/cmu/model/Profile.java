package edu.cmu.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Profile bean, which contains username, name, gender and age.
 *
 * @author lucas
 */
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    private String username;
    private String name;
    private String gender;
    private int age;
    private String imgURL;

    /**
     * Default constructor for the requirement of ORM tools.
     */
    protected Profile() {}

    /**
     * Instantiate with username, name, gender and age.
     * @param username username
     * @param name name
     * @param gender gender
     * @param age age
     * @param imgURL imgURL
     */
    public Profile(String username, String name, String gender, int age, String imgURL) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.imgURL = imgURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    /**
     * Override toString.
     * @return profile as a string
     */
    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", imgURL=" + imgURL +
                '}';
    }
}
