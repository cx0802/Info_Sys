package com.example.dao;

import com.example.model.User;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    // Method to get one user's information from TABLE users and TABLE userprofile
    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT u.user_id, u.username, p.region, p.gender, p.age, p.weight, p.height " +
                    "FROM Users u LEFT JOIN UserProfile p ON u.user_id = p.user_id";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        User user = new User();
                        user.setUserId(rs.getInt("user_id"));
                        user.setUsername(rs.getString("username"));
                        user.setRegion(rs.getString("region"));
                        user.setGender(rs.getString("gender"));
                        user.setAge(rs.getInt("age"));
                        user.setWeight(rs.getDouble("weight"));
                        user.setHeight(rs.getDouble("height"));
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    // Method to delete one user by userID from TABLE Activities, TABLE userprofile, and finally TABLE users
    public void deleteUser(int userId) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            // 删除活动表中的用户活动记录
            String deleteActivitiesQuery = "DELETE FROM Activities WHERE user_id = ?";
            try (PreparedStatement deleteActivitiesPst = con.prepareStatement(deleteActivitiesQuery)) {
                deleteActivitiesPst.setInt(1, userId);
                deleteActivitiesPst.executeUpdate();
            }
            // 删除用户资料表中的用户信息
            String deleteUserProfileQuery = "DELETE FROM UserProfile WHERE user_id = ?";
            try (PreparedStatement deleteUserProfilePst = con.prepareStatement(deleteUserProfileQuery)) {
                deleteUserProfilePst.setInt(1, userId);
                deleteUserProfilePst.executeUpdate();
            }
            // 删除用户表中的用户信息
            String deleteUserQuery = "DELETE FROM Users WHERE user_id = ?";
            try (PreparedStatement deleteUserPst = con.prepareStatement(deleteUserQuery)) {
                deleteUserPst.setInt(1, userId);
                deleteUserPst.executeUpdate();
            }
        }
    }
    
    // Method to change one user's password and username.
    public void updateUser(String oldUsername, String newUsername, String newPassword) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            String query = "UPDATE Users SET username = ?, password = ? WHERE username = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, newUsername);
                pst.setString(2, newPassword);
                pst.setString(3, oldUsername);
                pst.executeUpdate();
            }
        }
    }

    // Method to change one user's profile. (not include password and username)
    public void updateUserProfile(String username, String region, String gender, int age, double weight, double height) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            String query = "UPDATE UserProfile SET region = ?, gender = ?, age = ?, weight = ?, height = ? WHERE user_id = (SELECT user_id FROM Users WHERE username = ?)";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, region);
                pst.setString(2, gender);
                pst.setInt(3, age);
                pst.setDouble(4, weight);
                pst.setDouble(5, height);
                pst.setString(6, username);
                pst.executeUpdate();
            }
        }
    }

    // Method to get rankings of users. (user can search by different condition)
    public List<User> getUsersForLeaderboard(String region, String gender, String ageRange, String startDate, String endDate, String activityType) throws Exception {
        List<User> users = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            StringBuilder query = new StringBuilder(
                    "SELECT U.username, UP.region, UP.gender, UP.age, UP.weight, UP.height, SUM(A.duration_minutes) AS total_duration " +
                            "FROM Users U " +
                            "JOIN Activities A ON U.user_id = A.user_id " +
                            "LEFT JOIN UserProfile UP ON U.user_id = UP.user_id " +
                            "WHERE 1=1 ");

            if (region != null && !region.isEmpty()) {
                query.append("AND UP.region = ? ");
            }
            if (gender != null && !gender.isEmpty()) {
                query.append("AND UP.gender = ? ");
            }
            if (ageRange != null && !ageRange.isEmpty()) {
                switch (ageRange) {
                    case "0-18":
                        query.append("AND UP.age BETWEEN 0 AND 18 ");
                        break;
                    case "19-30":
                        query.append("AND UP.age BETWEEN 19 AND 30 ");
                        break;
                    case "31-50":
                        query.append("AND UP.age BETWEEN 31 AND 50 ");
                        break;
                    case "51+":
                        query.append("AND UP.age >= 51 ");
                        break;
                }
            }
            if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
                query.append("AND A.date BETWEEN ? AND ? ");
            }
            if (activityType != null && !activityType.isEmpty()) {
                query.append("AND A.activity_type = ? ");
            }

            query.append("GROUP BY U.username, UP.region, UP.gender, UP.age, UP.weight, UP.height " +
                    "ORDER BY total_duration DESC");

            PreparedStatement pst = con.prepareStatement(query.toString());
            int index = 1;
            if (region != null && !region.isEmpty()) {
                pst.setString(index++, region);
            }
            if (gender != null && !gender.isEmpty()) {
                pst.setString(index++, gender);
            }
            if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
                pst.setString(index++, startDate);
                pst.setString(index++, endDate);
            }
            if (activityType != null && !activityType.isEmpty()) {
                pst.setString(index++, activityType);
            }

            // Print the generated SQL query
            System.out.println("Executing query: " + pst);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setRegion(rs.getString("region"));
                    user.setGender(rs.getString("gender"));
                    user.setAge(rs.getInt("age"));
                    user.setWeight(rs.getDouble("weight"));
                    user.setHeight(rs.getDouble("height"));
                    user.setTotalDuration(rs.getInt("total_duration"));
                    users.add(user);
                }
            }
        }
        return users;
    }

    // Method to login user.
    public User validateUser(String username, String password) throws Exception {
        User user = null;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, username);
                pst.setString(2, password);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        user = new User();
                        user.setUserId(rs.getInt("user_id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                    }
                }
            }
        }
        return user;
    }

    // Method to register user to TABLE Users.
    public void registerUser(User user) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, user.getUsername());
                pst.setString(2, user.getPassword());
                pst.executeUpdate();
            }
        }
    }

    // Method to set up one user's profile.
    public void setUserProfile(User user) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            // 首先确保用户存在于 Users 表中
            String userQuery = "SELECT user_id FROM Users WHERE username = ?";
            int userId = 0;
            try (PreparedStatement userPst = con.prepareStatement(userQuery)) {
                userPst.setString(1, user.getUsername());
                try (ResultSet rs = userPst.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("user_id");
                    }
                }
            }

            if (userId == 0) {
                // 用户不存在，插入新用户
                String insertUserQuery = "INSERT INTO Users (username, password) VALUES (?, ?)";
                try (PreparedStatement insertUserPst = con.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                    insertUserPst.setString(1, user.getUsername());
                    insertUserPst.setString(2, user.getPassword());
                    insertUserPst.executeUpdate();
                    try (ResultSet generatedKeys = insertUserPst.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            userId = generatedKeys.getInt(1);
                        }
                    }
                }
            }




            // 插入或更新 UserProfile 表中的用户资料信息
            String query = "INSERT INTO UserProfile (user_id, region, gender, age, weight, height) VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE region = ?, gender = ?, age = ?, weight = ?, height = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, userId);
                pst.setString(2, user.getRegion());
                pst.setString(3, user.getGender());
                pst.setInt(4, user.getAge());
                pst.setDouble(5, user.getWeight());
                pst.setDouble(6, user.getHeight());
                pst.setString(7, user.getRegion());
                pst.setString(8, user.getGender());
                pst.setInt(9, user.getAge());
                pst.setDouble(10, user.getWeight());
                pst.setDouble(11, user.getHeight());
                pst.executeUpdate();
            }
        }
    }
    // Method to get user profile by username
    public User getUserProfileByUsername(String username) throws Exception {
        User user = null;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT region, gender, age, weight, height FROM UserProfile WHERE user_id = (SELECT user_id FROM Users WHERE username = ?)";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, username);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        user = new User();
                        user.setRegion(rs.getString("region"));
                        user.setGender(rs.getString("gender"));
                        user.setAge(rs.getInt("age"));
                        user.setWeight(rs.getDouble("weight"));
                        user.setHeight(rs.getDouble("height"));
                    }
                }
            }
        }
        return user;
    }


    // UserDAO.java
    public int getUserIdByUsername(String username) throws Exception {
        int userId = 0;
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



}