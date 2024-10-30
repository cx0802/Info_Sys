package com.example.servlet;

import com.example.dao.ActivityDAO;
import com.example.model.Activity;
import com.example.model.Weather;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "ShareActivityServlet", value = "/ShareActivityServlet")
public class ShareActivityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activityIdStr = request.getParameter("activityId");
        int activityId = Integer.parseInt(activityIdStr);

        try {
            ActivityDAO activityDAO = new ActivityDAO();
            Activity activity = activityDAO.getActivityById(activityId);

            if (activity != null) {
                String activityType = activity.getActivityType();
                int duration = activity.getDurationMinutes();
                String date = activity.getDate().toString();
                String startTime = activity.getStartTime().toString();

                Weather weather = activityDAO.getWeatherByDate(date);
                float caloriesPerMinute = activityDAO.getCaloriesPerMinute(activityType);
                float totalCaloriesBurned = caloriesPerMinute * duration;

                request.setAttribute("activityType", activityType);
                request.setAttribute("duration", duration);
                request.setAttribute("date", date);
                request.setAttribute("startTime", startTime);
                request.setAttribute("temperature", weather != null ? weather.getTemperature() : 0);
                request.setAttribute("weatherCondition", weather != null ? weather.getWeatherCondition() : "Unknown");
                request.setAttribute("totalCaloriesBurned", totalCaloriesBurned);

                request.getRequestDispatcher("shareActivityResult.jsp").forward(request, response);
            } else {
                response.sendRedirect("error.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}

