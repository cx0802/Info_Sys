package com.example.dao;

import com.example.model.ExercisePlan;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExercisePlanDAO {
    
    // 创建新的运动计划
    public void createPlan(ExercisePlan plan) throws Exception {
        String sql = "INSERT INTO exercise_plans (user_id, start_date, end_date, total_duration, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, plan.getUserId());
            stmt.setDate(2, plan.getStartDate());
            stmt.setDate(3, plan.getEndDate());
            stmt.setInt(4, plan.getTotalDuration());
            stmt.setString(5, "active");
            
            stmt.executeUpdate();
            
            // 获取生成的计划ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    plan.setPlanId(rs.getInt(1));
                }
            }
        }
    }
    
    // 获取用户当前的运动计划
    public ExercisePlan getCurrentPlan(String username) throws Exception {
        String sql = "SELECT p.* FROM exercise_plans p " +
                     "JOIN users u ON p.user_id = u.user_id " +
                     "WHERE u.username = ? AND p.status = 'active' " +
                     "AND p.end_date >= CURRENT_DATE " +
                     "ORDER BY p.created_at DESC LIMIT 1";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPlan(rs);
                }
            }
        }
        return null;
    }
    
    // 更新运动计划
    public void updatePlan(ExercisePlan plan) throws Exception {
        String sql = "UPDATE exercise_plans SET start_date = ?, end_date = ?, " +
                     "total_duration = ? WHERE plan_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, plan.getStartDate());
            stmt.setDate(2, plan.getEndDate());
            stmt.setInt(3, plan.getTotalDuration());
            stmt.setInt(4, plan.getPlanId());
            
            stmt.executeUpdate();
        }
    }
    
    // 删除运动计划
    public void deletePlan(int planId) throws Exception {
        String sql = "UPDATE exercise_plans SET status = 'cancelled' WHERE plan_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, planId);
            stmt.executeUpdate();
        }
    }
    
    public ExercisePlan getPlanById(int planId) throws Exception {
        String sql = "SELECT * FROM exercise_plans WHERE plan_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, planId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPlan(rs);
                }
            }
        }
        return null;
    }
    
    private ExercisePlan mapResultSetToPlan(ResultSet rs) throws SQLException {
        ExercisePlan plan = new ExercisePlan();
        plan.setPlanId(rs.getInt("plan_id"));
        plan.setUserId(rs.getInt("user_id"));
        plan.setStartDate(rs.getDate("start_date"));
        plan.setEndDate(rs.getDate("end_date"));
        plan.setTotalDuration(rs.getInt("total_duration"));
        plan.setCreatedAt(rs.getTimestamp("created_at"));
        plan.setStatus(rs.getString("status"));
        return plan;
    }
} 