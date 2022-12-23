package it.unitn.disi.sdeproject.beans;

public class Athlete extends User{
    String sport;
    float height;
    float weight;

    public Athlete() {
        super();
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
}
