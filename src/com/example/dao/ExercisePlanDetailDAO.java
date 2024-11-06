package com.example.dao;

import com.example.model.ExercisePlanDetail;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExercisePlanDetailDAO {
    
    public void saveDetail(ExercisePlanDetail detail) throws Exception {
        String sql = "INSERT INTO exercise_plan_details (plan_id, activity_type, target_duration, weekly_frequency, completed_duration) VALUES (?, ?, ?, ?, 0)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, detail.getPlanId());
            stmt.setString(2, detail.getActivityType());
            stmt.setInt(3, detail.getTargetDuration());
            stmt.setInt(4, detail.getWeeklyFrequency());
            
            stmt.executeUpdate();
        }
    }
    
    public List<ExercisePlanDetail> getDetailsByPlanId(int planId) throws Exception {
        String sql = "SELECT * FROM exercise_plan_details WHERE plan_id = ?";
        List<ExercisePlanDetail> details = new ArrayList<>();
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, planId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    details.add(mapResultSetToDetail(rs));
                }
            }
        }
        return details;
    }
    
    public void updateCompletedDuration(int detailId, int completedDuration) throws Exception {
        String sql = "UPDATE exercise_plan_details SET completed_duration = ? WHERE detail_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, completedDuration);
            stmt.setInt(2, detailId);
            
            stmt.executeUpdate();
        }
    }
    
    public void updateDetail(ExercisePlanDetail detail) throws Exception {
        String sql = "UPDATE exercise_plan_details SET activity_type = ?, " +
                     "target_duration = ?, weekly_frequency = ? WHERE detail_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, detail.getActivityType());
            stmt.setInt(2, detail.getTargetDuration());
            stmt.setInt(3, detail.getWeeklyFrequency());
            stmt.setInt(4, detail.getDetailId());
            
            stmt.executeUpdate();
        }
    }
    
    private ExercisePlanDetail mapResultSetToDetail(ResultSet rs) throws SQLException {
        ExercisePlanDetail detail = new ExercisePlanDetail();
        detail.setDetailId(rs.getInt("detail_id"));
        detail.setPlanId(rs.getInt("plan_id"));
        detail.setActivityType(rs.getString("activity_type"));
        detail.setTargetDuration(rs.getInt("target_duration"));
        detail.setWeeklyFrequency(rs.getInt("weekly_frequency"));
        detail.setCompletedDuration(rs.getInt("completed_duration"));
        return detail;
    }
} 