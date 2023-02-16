package it.unitn.disi.sdeproject.beans;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Professional {
    @Expose
    protected int professional_id;
    @Expose
    protected String name;
    @Expose
    protected String surname;
    @Expose
    protected String title;
    @Expose
    protected String description;

    public Professional() {}

    public Professional(int professional_id, String name, String surname, String title, String description) {
        this.professional_id = professional_id;
        this.name = name;
        this.surname = surname;
        this.title = title;
        this.description = description;
    }

    public int getProfessional_id() {
        return professional_id;
    }

    public void setProfessional_id(int professional_id) {
        this.professional_id = professional_id;
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
        return "Professional{" +
                "professional_id=" + professional_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
