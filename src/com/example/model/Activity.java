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
    private float temperature;
    private String weatherCondition;
    private float totalCaloriesBurned;

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
    public float getTemperature() { return temperature; }
    public void setTemperature(float temperature) { this.temperature = temperature; }
    public String getWeatherCondition() { return weatherCondition; }
    public void setWeatherCondition(String weatherCondition) { this.weatherCondition = weatherCondition; }
    public float getTotalCaloriesBurned() { return totalCaloriesBurned; }
    public void setTotalCaloriesBurned(float totalCaloriesBurned) { this.totalCaloriesBurned = totalCaloriesBurned; }
}
