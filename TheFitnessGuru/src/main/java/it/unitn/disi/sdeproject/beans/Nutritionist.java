package it.unitn.disi.sdeproject.beans;

public class Nutritionist extends User{
    String title;
    String description;

    public Nutritionist() {
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
}
