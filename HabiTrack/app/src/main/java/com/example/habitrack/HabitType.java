package com.example.habitrack;

import java.util.ArrayList;
import java.util.Calendar;

import io.searchbox.annotations.JestId;

/**
 *
 * HabitType
 *
 * Version 1.01
 *
 * Created by sshussai on 10/21/17.
 *
 */

public class HabitType {
    /**
     * This class is the template for a habit type entity.
     *
     */

    private final Integer ID;
    private String title;                   // title of the habit type
    private String reason;                  // reason for the habit type
    private Calendar startDate;             // Calendar object representing the start date
    private ArrayList<Integer> schedule;    // ArrayList of ints representing days of the week for the plan
    private Integer completedCounter;       // habit events currently completed
    private Integer currentMaxCounter;      // total number of habits events possible so far
    private Boolean canBeScheduled;         // Boolean variable that determines if events can be scheduled
    private String userID;                  // userID of the current user
    private HabitTypeMetadata myData;       // Metadata object for the current habit type
    private HabitEvent mostRecentEvent;     // Most recent habitEvent
    @JestId
    private String id;                      // Elastic Search ID for the habit

    public HabitType(Integer id) {
        this.ID = id;
        this.completedCounter = 0;
        this.currentMaxCounter = 0;
        this.canBeScheduled = Boolean.FALSE;
        this.myData = new HabitTypeMetadata(this.ID, this.id);
        this.myData.setCanBeScheduled(this.canBeScheduled);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.myData.setTitle(this.title);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {

        this.startDate = startDate;
        // Check if ht can be scheduled ONLY if start date is BEFORE OR EQUAL today
        Calendar today = Calendar.getInstance();
        if(startDate.get(Calendar.YEAR) < today.get(Calendar.YEAR)
                || (startDate.get(Calendar.YEAR) <= today.get(Calendar.YEAR)
                && startDate.get(Calendar.MONTH) < today.get(Calendar.MONTH))
                || (startDate.get(Calendar.MONTH) <= today.get(Calendar.MONTH)
                && startDate.get(Calendar.YEAR) <= today.get(Calendar.YEAR)
                && startDate.get(Calendar.DATE) <= today.get(Calendar.DATE))) {
            this.canBeScheduled = Boolean.TRUE;
            this.myData.setCanBeScheduled(this.canBeScheduled);
        }
    }

    public ArrayList<Integer> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Integer> plan) {
        this.schedule = plan;
        this.myData.setSchedule(this.schedule);
    }

    public Integer getID() {
        return ID;
    }

    public Integer getCompletedCounter() {
        return completedCounter;
    }

    public Integer getCurrentMaxCounter() {
        return currentMaxCounter;
    }

    public String getElasticSearchId() {
        return id;
    }

    public void incrementCompletedCounter(){
        completedCounter++;
    }

    public void incrementMaxCounter(){
        currentMaxCounter++;
    }

    public void setId(String id) {
        this.id = id;
        this.myData.setEsID(this.id);
    }

    public HabitTypeMetadata getMyData() {
        return myData;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public HabitEvent getMostRecentEvent() {
        return mostRecentEvent;
    }

    public void setMostRecentEvent(HabitEvent mostRecentEvent) {
        this.mostRecentEvent = mostRecentEvent;
    }

    @Override
    public String toString(){return getTitle();}
}
