package it.unitn.disi.sdeproject.beans;

import java.util.Date;

@SuppressWarnings("ALL")
public class Nutritionist extends User{
    protected String title;
    protected String description;

    public Nutritionist() {
    }

    public Nutritionist(int user_id, char user_type, String name, String surname, Date birthdate, char gender, String username, String title, String description) {
        super(user_id, user_type, name, surname, birthdate, gender, username);
        this.title = title;
        this.description = description;
    }

    @SuppressWarnings("unused")
    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Nutritionist{" +
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
