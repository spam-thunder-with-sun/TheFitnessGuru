package it.unitn.disi.sdeproject.beans;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class Workout {
    @Expose
    protected int request_id;
    @Expose
    protected Date request_date;
    @Expose
    protected String health_notes;
    @Expose
    protected String workout_goals;
    @Expose
    protected int workout_days;
    @Expose
    protected String json;

    public Workout(int request_id, Date request_date, String health_notes, String workout_goals, int workout_days, String json) {
        this.request_id = request_id;
        this.request_date = request_date;
        this.health_notes = health_notes;
        this.workout_goals = workout_goals;
        this.workout_days = workout_days;
        this.json = json;
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

    public String getHealth_notes() {
        return health_notes;
    }

    public void setHealth_notes(String health_notes) {
        this.health_notes = health_notes;
    }

    public String getWorkout_goals() {
        return workout_goals;
    }

    public void setWorkout_goals(String workout_goals) {
        this.workout_goals = workout_goals;
    }

    public int getWorkout_days() {
        return workout_days;
    }

    public void setWorkout_days(int workout_days) {
        this.workout_days = workout_days;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }


    @Override
    public String toString() {
        return "Workout{" +
                "request_id=" + request_id +
                ", request_date=" + request_date +
                ", health_notes='" + health_notes + '\'' +
                ", workout_goals='" + workout_goals + '\'' +
                ", workout_days=" + workout_days +
                ", json='" + json + '\'' +
                '}';
    }
}
