package it.unitn.disi.sdeproject.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

    protected int user_id;
    protected char user_type;
    protected String name;
    protected String surname;
    protected Date birthdate;
    protected char gender;
    protected String username;

    //To print date
    static private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public User() {
    }

    public User(int user_id, char user_type, String name, String surname, Date birthdate, char gender, String username) {
        this.user_id = user_id;
        this.user_type = user_type;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.username = username;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_type(char user_type) {
        this.user_type = user_type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public char getUser_type() { return user_type;}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthdate() {
        return dateFormat.format(birthdate);
    }

    public String getGender() {
        return (gender == 'F') ? "Female" : "Male";
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_type=" + user_type +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                ", username='" + username + '\'' +
                '}';
    }
}
