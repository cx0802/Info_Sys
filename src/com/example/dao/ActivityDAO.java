package com.example.dao;

import com.example.model.Activity;
import com.example.model.Weather;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

package com.example.dao;

import com.example.model.Activity;
import com.example.model.Weather;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {

    // 获取所有活动
    public List<Activity> getAllActivities() throws Exception {
        List<Activity> activities = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT activity_id, user_id, type_id, date, start_time, duration_minutes FROM Activities";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Activity activity = new Activity();
                        activity.setActivityId(rs.getInt("activity_id"));
                        activity.setUserId(rs.getInt("user_id"));
                        activity.setTypeId(rs.getInt("type_id")); // 使用 typeId
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

    // 根据活动ID获取单个活动
    public Activity getActivityById(int activityId) throws Exception {
        Activity activity = null;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT activity_id, user_id, type_id, date, start_time, duration_minutes FROM Activities WHERE activity_id = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, activityId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        activity = new Activity();
                        activity.setActivityId(rs.getInt("activity_id"));
                        activity.setUserId(rs.getInt("user_id"));
                        activity.setTypeId(rs.getInt("type_id")); // 使用 typeId
                        activity.setDate(rs.getDate("date"));
                        activity.setStartTime(rs.getTime("start_time"));
                        activity.setDurationMinutes(rs.getInt("duration_minutes"));
                    }
                }
            }
        }
        return activity;
    }

    // 删除活动
    public void deleteActivity(int activityId) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            String query = "DELETE FROM Activities WHERE activity_id = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, activityId);
                pst.executeUpdate();
            }
        }
    }

    // 根据用户名获取用户ID
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

    // 插入新活动
    public boolean addActivity(Activity activity) {
        String sql = "INSERT INTO activities (user_id, type_id, date, start_time, duration_minutes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, activity.getUserId());
            stmt.setInt(2, activity.getTypeId()); // 使用 typeId 代替 activityType
            stmt.setDate(3, activity.getDate());
            stmt.setTime(4, activity.getStartTime());
            stmt.setInt(5, activity.getDurationMinutes());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 搜索用户活动
    public List<Activity> searchActivities(String username, Integer typeId, String date) throws Exception {
        List<Activity> activities = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            StringBuilder query = new StringBuilder(
                    "SELECT activity_id, user_id, type_id, date, start_time, duration_minutes " +
                            "FROM Activities WHERE user_id = (SELECT user_id FROM Users WHERE username = ?) ");
            if (typeId != null) {
                query.append("AND type_id = ? ");
            }
            if (date != null && !date.isEmpty()) {
                query.append("AND date = ? ");
            }

            PreparedStatement pst = con.prepareStatement(query.toString());
            pst.setString(1, username);
            int index = 2;
            if (typeId != null) {
                pst.setInt(index++, typeId);
            }
            if (date != null && !date.isEmpty()) {
                pst.setString(index, date);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Activity activity = new Activity();
                    activity.setActivityId(rs.getInt("activity_id"));
                    activity.setUserId(rs.getInt("user_id"));
                    activity.setTypeId(rs.getInt("type_id")); // 使用 typeId
                    activity.setDate(rs.getDate("date"));
                    activity.setStartTime(rs.getTime("start_time"));
                    activity.setDurationMinutes(rs.getInt("duration_minutes"));
                    activities.add(activity);
                }
            }
        }
        return activities;
    }

    // 获取用户在时间段内的总活动时长
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

    // 获取特定类型运动的每分钟消耗卡路里
    public float getCaloriesPerMinute(int typeId) throws Exception {
        float caloriesPerMinute = 0;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT calories_per_minute FROM ActivityCalories WHERE type_id = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, typeId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        caloriesPerMinute = rs.getFloat("calories_per_minute");
                    }
                }
            }
        }
        return caloriesPerMinute;
    }

    // 获取所有活动类型
    public List<String> getAllActivityTypes() {
        List<String> activityTypes = new ArrayList<>();
        String sql = "SELECT type_name FROM activitytypes"; // 假设活动类型表有 type_name 字段表示类型名称
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                activityTypes.add(rs.getString("type_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activityTypes;
    }

    // 根据日期获取天气状况
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
}
