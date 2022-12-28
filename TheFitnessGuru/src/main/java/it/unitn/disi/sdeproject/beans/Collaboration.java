package it.unitn.disi.sdeproject.beans;

import java.util.Date;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Collaboration {
    @Expose
    protected int collaboration_id;
    @Expose
    protected String name;
    @Expose
    protected String surname;
    @Expose
    protected int other_id;
    @Expose
    protected Date init_date;
    @Expose
    protected boolean status;

    public Collaboration(int collaboration_id, String name, String surname, int other_id, Date init_date, boolean status) {
        this.collaboration_id = collaboration_id;
        this.name = name;
        this.surname = surname;
        this.other_id = other_id;
        this.init_date = init_date;
        this.status = status;
    }

    public int getCollaboration_id() {
        return collaboration_id;
    }

    public void setCollaboration_id(int collaboration_id) {
        this.collaboration_id = collaboration_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getOther_id() {
        return other_id;
    }

    public void setOther_id(int other_id) {
        this.other_id = other_id;
    }

    public Date getInit_date() {
        return init_date;
    }

    public void setInit_date(Date init_date) {
        this.init_date = init_date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Collaboration{" +
                "collaboration_id=" + collaboration_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", other_id=" + other_id +
                ", init_date=" + init_date +
                ", status=" + status +
                '}';
    }
}
