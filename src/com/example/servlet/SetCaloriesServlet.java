package com.example.servlet;

import com.example.dao.ActivityCaloriesDAO;
import com.example.model.ActivityCalories;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SetCaloriesServlet", value = "/SetCaloriesServlet")
public class SetCaloriesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activityType = request.getParameter("activity_type");
        float caloriesPerMinute = Float.parseFloat(request.getParameter("calories_per_minute"));

        try {
            ActivityCalories activityCalories = new ActivityCalories();
            activityCalories.setActivityType(activityType);
            activityCalories.setCaloriesPerMinute(caloriesPerMinute);

            ActivityCaloriesDAO activityCaloriesDAO = new ActivityCaloriesDAO();
            activityCaloriesDAO.setCalories(activityCalories);

            response.sendRedirect("adminHome.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
