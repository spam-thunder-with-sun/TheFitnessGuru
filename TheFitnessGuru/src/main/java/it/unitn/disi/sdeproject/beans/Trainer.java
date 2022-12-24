package it.unitn.disi.sdeproject.beans;

import java.util.Date;

@SuppressWarnings("unused")
public class Trainer extends User{
    protected String title;
    protected String description;

    public Trainer() {
    }

    public Trainer(int user_id, char user_type, String name, String surname, Date birthdate, char gender, String username, String title, String description) {
        super(user_id, user_type, name, surname, birthdate, gender, username);
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Trainer{" +
                "user_id=" + user_id +
                ", user_type=" + user_type +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
