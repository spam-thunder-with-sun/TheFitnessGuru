package it.unitn.disi.sdeproject.beans;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Diet {
    @Expose
    protected int request_id;
    @Expose
    protected Date request_date;
    @Expose
    protected String alergies;
    @Expose
    protected String intolerances;
    @Expose
    protected int basal_metabolic_rate;
    @Expose
    protected String diet_goals;
    @Expose
    protected int lifestyle;
    @Expose
    protected boolean response;

    public Diet(int request_id, Date request_date, String alergies, String intolerances, int basal_metabolic_rate, String diet_goals, int lifestyle, String response) {
        this(request_id, request_date, alergies, intolerances, basal_metabolic_rate, diet_goals, lifestyle, false);
        if(response != null && !response.equals(""))
            this.response = true;
    }

    public Diet(int request_id, Date request_date, String alergies, String intolerances, int basal_metabolic_rate, String diet_goals, int lifestyle, boolean response) {
        this.request_id = request_id;
        this.request_date = request_date;
        this.alergies = alergies;
        this.intolerances = intolerances;
        this.basal_metabolic_rate = basal_metabolic_rate;
        this.diet_goals = diet_goals;
        this.lifestyle = lifestyle;
        this.response = response;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    public String getAlergies() {
        return alergies;
    }

    public void setAlergies(String alergies) {
        this.alergies = alergies;
    }

    public String getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(String intolerances) {
        this.intolerances = intolerances;
    }

    public int getBasal_metabolic_rate() {
        return basal_metabolic_rate;
    }

    public void setBasal_metabolic_rate(int basal_metabolic_rate) {
        this.basal_metabolic_rate = basal_metabolic_rate;
    }

    public String getDiet_goals() {
        return diet_goals;
    }

    public void setDiet_goals(String diet_goals) {
        this.diet_goals = diet_goals;
    }

    public int getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(int lifestyle) {
        this.lifestyle = lifestyle;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Diet{" +
                "request_id=" + request_id +
                ", request_date=" + request_date +
                ", alergies='" + alergies + '\'' +
                ", intolerances='" + intolerances + '\'' +
                ", basal_metabolic_rate=" + basal_metabolic_rate +
                ", diet_goals='" + diet_goals + '\'' +
                ", lifestyle=" + lifestyle +
                ", response=" + response +
                '}';
    }
}
