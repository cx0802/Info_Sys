package com.example.dao;

import com.example.model.ActivityCalories;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivityCaloriesDAO {
    // Method to set calories burned per minutes for each sport
    public void setCalories(ActivityCalories activityCalories) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            String query = "INSERT INTO ActivityCalories (activity_type, calories_per_minute) VALUES (?, ?) ON DUPLICATE KEY UPDATE calories_per_minute=?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, activityCalories.getActivityType());
                pst.setFloat(2, activityCalories.getCaloriesPerMinute());
                pst.setFloat(3, activityCalories.getCaloriesPerMinute());
                pst.executeUpdate();
            }
        }
    }
}
