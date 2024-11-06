package com.example.model;

public class ExercisePlanDetail {
    private int detailId;
    private int planId;
    private String activityType;
    private int targetDuration;
    private int weeklyFrequency;
    private int completedDuration;

    // Getters and Setters
    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public int getTargetDuration() {
        return targetDuration;
    }

    public void setTargetDuration(int targetDuration) {
        this.targetDuration = targetDuration;
    }

    public int getWeeklyFrequency() {
        return weeklyFrequency;
    }

    public void setWeeklyFrequency(int weeklyFrequency) {
        this.weeklyFrequency = weeklyFrequency;
    }

    public int getCompletedDuration() {
        return completedDuration;
    }

    public void setCompletedDuration(int completedDuration) {
        this.completedDuration = completedDuration;
    }
} 