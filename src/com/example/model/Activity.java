package com.example.model;

public class Activity {
    private int activityId;
    private int userId;
    private int typeId; // 使用 typeId 替代原来的 activityType
    private java.sql.Date date;
    private java.sql.Time startTime;
    private int durationMinutes;

    // Getters and Setters
    public int getActivityId() { return activityId; }
    public void setActivityId(int activityId) { this.activityId = activityId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTypeId() { return typeId; } // 修改后的字段
    public void setTypeId(int typeId) { this.typeId = typeId; }

    public java.sql.Date getDate() { return date; }
    public void setDate(java.sql.Date date) { this.date = date; }

    public java.sql.Time getStartTime() { return startTime; }
    public void setStartTime(java.sql.Time startTime) { this.startTime = startTime; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
}
