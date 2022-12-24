package it.unitn.disi.sdeproject.beans;

import java.util.Date;

@SuppressWarnings("unused")
public class Athlete extends User {
    protected String sport;
    protected float height;
    protected float weight;

    public Athlete() {
    }

    public Athlete(int user_id, char user_type, String name, String surname, Date birthdate, char gender, String username, String sport, float height, float weight) {
        super(user_id, user_type, name, surname, birthdate, gender, username);
        this.sport = sport;
        this.height = height;
        this.weight = weight;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return "Athlete{" +
                "user_id=" + user_id +
                ", user_type=" + user_type +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                ", gender=" + gender +
                ", username='" + username + '\'' +
                ", sport='" + sport + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}

