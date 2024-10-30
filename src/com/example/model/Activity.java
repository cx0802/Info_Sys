package com.example.model;

import java.sql.Date;
import java.sql.Time;

public class Activity {
    private int activityId;
    private int userId;
    private String activityType;
    private Date date;
    private Time startTime;
    private int durationMinutes;

    // Getters and Setters
    public int getActivityId() { return activityId; }
    public void setActivityId(int activityId) { this.activityId = activityId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Time getStartTime() { return startTime; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
}
