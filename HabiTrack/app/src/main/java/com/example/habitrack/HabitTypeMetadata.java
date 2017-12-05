package com.example.habitrack;

import java.util.ArrayList;

/**
 * Created by sshussai on 11/29/17.
 */

public class HabitTypeMetadata {

    private Integer localID;
    private String esID;
    private String title;
    private Boolean canBeScheduled;
    private Boolean scheduledToday;
    private ArrayList<Integer> schedule;


    public HabitTypeMetadata(Integer localID, String esID) {
        this.localID = localID;
        this.esID = esID;
        this.scheduledToday = Boolean.FALSE;
    }

    public Integer getLocalID() {
        return localID;
    }

    public void setLocalID(Integer localID) {
        this.localID = localID;
    }

    public String getEsID() {
        return esID;
    }

    public void setEsID(String esID) {
        this.esID = esID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCanBeScheduled() {
        return canBeScheduled;
    }

    public void setCanBeScheduled(Boolean canBeScheduled) {
        this.canBeScheduled = canBeScheduled;
    }

    public Boolean getScheduledToday() {
        return scheduledToday;
    }

    public void setScheduledToday(Boolean scheduledToday) {
        this.scheduledToday = scheduledToday;
    }

    public ArrayList<Integer> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Integer> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return title;
    }
}
