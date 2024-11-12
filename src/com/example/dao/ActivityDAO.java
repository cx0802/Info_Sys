package com.example.dao;

import com.example.model.Activity;
import com.example.model.ActivityType;
import com.example.model.Weather;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityDAO {
    // Method to get all activities from TABLE Activities
    public List<Activity> getAllActivities() throws Exception {
        List<Activity> activities = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT activity_id, user_id, activity_type, date, start_time, duration_minutes FROM Activities";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Activity activity = new Activity();
                        activity.setActivityId(rs.getInt("activity_id"));
                        activity.setUserId(rs.getInt("user_id"));
                        activity.setActivityType(rs.getString("activity_type"));
                        activity.setDate(rs.getDate("date"));
                        activity.setStartTime(rs.getTime("start_time"));
                        activity.setDurationMinutes(rs.getInt("duration_minutes"));
                        activities.add(activity);
                    }
                }
            }
        }
        return activities;
    }

    // Method to get one user's activities from TABLE Activities
    public Activity getActivityById(int activityId) throws Exception {
        Activity activity = null;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT activity_id, user_id, activity_type, date, start_time, duration_minutes FROM Activities WHERE activity_id = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, activityId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        activity = new Activity();
                        activity.setActivityId(rs.getInt("activity_id"));
                        activity.setUserId(rs.getInt("user_id"));
                        activity.setActivityType(rs.getString("activity_type"));
                        activity.setDate(rs.getDate("date"));
                        activity.setStartTime(rs.getTime("start_time"));
                        activity.setDurationMinutes(rs.getInt("duration_minutes"));
                    }
                }
            }
        }
        return activity;
    }

    // Method to get one activity from TABLE Activities
    public void deleteActivity(int activityId) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            String query = "DELETE FROM Activities WHERE activity_id = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, activityId);
                pst.executeUpdate();
            }
        }
    }

    // Method to get one user's username by user_id in TABLE Users
    public int getUserIdByUsername(String username) throws Exception {
        int userId = -1;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT user_id FROM Users WHERE username = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, username);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                    }
                }
            }
        }
        return userId;
    }

    // Method to add one activity to TABLE Activities
    public void addActivity(Activity activity) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            String query = "INSERT INTO Activities (user_id, activity_type, date, start_time, duration_minutes) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, activity.getUserId());
                pst.setString(2, activity.getActivityType());
                pst.setDate(3, activity.getDate());
                pst.setTime(4, activity.getStartTime());
                pst.setInt(5, activity.getDurationMinutes());
                pst.executeUpdate();
            }
        }
    }

    // Method to search one user's activities from TABLE Activities. (User can search by condition)
    public List<Activity> searchActivities(String username, String activityType, String date) throws Exception {
        List<Activity> activities = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            StringBuilder query = new StringBuilder(
                    "SELECT activity_id, user_id, activity_type, date, start_time, duration_minutes " +
                            "FROM Activities WHERE user_id = (SELECT user_id FROM Users WHERE username = ?) ");
            if (activityType != null && !activityType.isEmpty()) {
                query.append("AND activity_type = ? ");
            }
            if (date != null && !date.isEmpty()) {
                query.append("AND date = ? ");
            }

            PreparedStatement pst = con.prepareStatement(query.toString());
            pst.setString(1, username);
            int index = 2;
            if (activityType != null && !activityType.isEmpty()) {
                pst.setString(index++, activityType);
            }
            if (date != null && !date.isEmpty()) {
                pst.setString(index, date);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Activity activity = new Activity();
                    activity.setActivityId(rs.getInt("activity_id"));
                    activity.setUserId(rs.getInt("user_id"));
                    activity.setActivityType(rs.getString("activity_type"));
                    activity.setDate(rs.getDate("date"));
                    activity.setStartTime(rs.getTime("start_time"));
                    activity.setDurationMinutes(rs.getInt("duration_minutes"));
                    activities.add(activity);
                }
            }
        }
        return activities;
    }

    // Method to get one user's total sport time in a time period
    public int getTotalDuration(String username, String startDate, String endDate) throws Exception {
        int totalDuration = 0;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT SUM(duration_minutes) AS total_duration FROM Activities WHERE user_id = (SELECT user_id FROM Users WHERE username = ?) AND date BETWEEN ? AND ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, username);
                pst.setString(2, startDate);
                pst.setString(3, endDate);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        totalDuration = rs.getInt("total_duration");
                    }
                }
            }
        }
        return totalDuration;
    }

    // Method to get calories burned per minutes for one type of sport
    public float getCaloriesPerMinute(String activityType) throws Exception {
        float caloriesPerMinute = 0;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT calories_per_minute FROM ActivityCalories WHERE activity_type = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, activityType);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        caloriesPerMinute = rs.getFloat("calories_per_minute");
                    }
                }
            }
        }
        return caloriesPerMinute;
    }

    // Method to get weather condition by data
    public Weather getWeatherByDate(String date) throws Exception {
        Weather weather = null;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT temperature, weather_condition FROM Weather WHERE date = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, date);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        weather = new Weather();
                        weather.setTemperature(rs.getFloat("temperature"));
                        weather.setWeatherCondition(rs.getString("weather_condition"));
                    }
                }
            }
        }
        return weather;
    }

    public List<ActivityType> getActivityTypes() throws Exception {
        List<ActivityType> activityTypes = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT activity_type FROM ActivityCalories";
            try (PreparedStatement pst = con.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    ActivityType type = new ActivityType();
                    type.setActivityType(rs.getString("activity_type"));
                    activityTypes.add(type);
                }
            }
            // 调试输出
            System.out.println("Fetched activity types: " + activityTypes);
        }
        return activityTypes;
    }

    public int getCompletedDurationByType(int userId, String activityType, Date startDate, Date endDate) throws Exception {
        String sql = "SELECT SUM(duration_minutes) FROM activities " +
                     "WHERE user_id = ? AND activity_type = ? " +
                     "AND date >= ? AND date <= ?";
                 
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setString(2, activityType);
            stmt.setDate(3, (java.sql.Date) startDate);
            stmt.setDate(4, (java.sql.Date) endDate);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public List<Activity> getActivitiesByUserId(int userId) throws Exception {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT a.*, w.temperature, w.weather_condition " +
                     "FROM activities a " +
                     "LEFT JOIN weather w ON DATE(a.date) = w.date " +
                     "WHERE a.user_id = ? " +
                     "ORDER BY a.date DESC, a.start_time DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setActivityId(rs.getInt("activity_id"));
                activity.setUserId(rs.getInt("user_id"));
                activity.setActivityType(rs.getString("activity_type"));
                activity.setDate(rs.getDate("date"));
                activity.setStartTime(rs.getTime("start_time"));
                activity.setDurationMinutes(rs.getInt("duration_minutes"));
                
                // 设置天气相关信息
                activity.setTemperature(rs.getFloat("temperature"));
                activity.setWeatherCondition(rs.getString("weather_condition"));
                
                activities.add(activity);
            }
        }
        
        return activities;
    }

}

