package com.example.servlet;

import com.example.dao.AdminDAO;
import com.example.model.Admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "AdminLoginServlet", value = "/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            AdminDAO adminDAO = new AdminDAO();
            Admin admin = adminDAO.validateAdmin(username, password);

            if (admin != null) {
                // Login successful, redirect to admin home page
                response.sendRedirect("adminHome.jsp");
            } else {
                // Login failed, redirect back to admin login page
                response.sendRedirect("adminLogin.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
