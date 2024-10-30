package com.example.servlet;

import com.example.dao.ActivityDAO;
import com.example.model.Activity;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "ViewAllActivitiesServlet", value = "/ViewAllActivitiesServlet")
public class ViewAllActivitiesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ActivityDAO activityDAO = new ActivityDAO();
            List<Activity> activities = activityDAO.getAllActivities();

            request.setAttribute("activities", activities);
            request.getRequestDispatcher("viewAllActivities.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}

