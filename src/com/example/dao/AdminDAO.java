package com.example.dao;

import com.example.model.Admin;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {
    // Method to login admins
    public Admin validateAdmin(String username, String password) throws Exception {
        Admin admin = null;
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, username);
                pst.setString(2, password);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        admin = new Admin();
                        admin.setAdminId(rs.getInt("admin_id"));
                        admin.setUsername(rs.getString("username"));
                        admin.setPassword(rs.getString("password"));
                    }
                }
            }
        }
        return admin;
    }
}
