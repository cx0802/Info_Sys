package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.model.User;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "LeaderboardServlet", value = "/LeaderboardServlet")
public class LeaderboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String region = request.getParameter("region");
        String gender = request.getParameter("gender");
        String ageRange = request.getParameter("ageRange");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String activityType = request.getParameter("activityType");

        try {
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.getUsersForLeaderboard(region, gender, ageRange, startDate, endDate, activityType);

            // Print debug information
            System.out.println("Found " + users.size() + " users");

            request.setAttribute("users", users);
            request.getRequestDispatcher("leaderboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
