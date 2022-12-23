package it.unitn.disi.sdeproject.beans;

import java.util.Date;

public class User {

    private int user_id;
    private char user_type;
    private String name;
    private String surname;
    private Date birthdate;
    private char gender;
    private String username;

    public User() {
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

    public char getUser_type() {
        return user_type;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public char getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }
}
