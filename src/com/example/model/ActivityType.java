package com.example.model;

// ActivityType.java
public class ActivityType {
    private int typeId;
    private String activityType;
    private float caloriesPerMinute;

    // Getters and Setters
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public float getCaloriesPerMinute() {
        return caloriesPerMinute;
    }

    public void setCaloriesPerMinute(float caloriesPerMinute) {
        this.caloriesPerMinute = caloriesPerMinute;
    }
}
