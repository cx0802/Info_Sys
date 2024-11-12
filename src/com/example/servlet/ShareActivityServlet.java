package com.example.servlet;

import com.example.dao.ActivityDAO;
import com.example.dao.SharedActivityDAO;
import com.example.model.Activity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShareActivityServlet", value = "/ShareActivityServlet")
public class ShareActivityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            ActivityDAO activityDAO = new ActivityDAO();
            int userId = activityDAO.getUserIdByUsername(username);
            List<Activity> activities = activityDAO.getActivitiesByUserId(userId);
            
            request.setAttribute("activities", activities);
            request.getRequestDispatcher("shareActivity.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            int activityId = Integer.parseInt(request.getParameter("activityId"));
            String shareText = request.getParameter("shareText");
            
            ActivityDAO activityDAO = new ActivityDAO();
            int userId = activityDAO.getUserIdByUsername(username);
            
            Activity activity = activityDAO.getActivityById(activityId);
            if (activity == null || activity.getUserId() != userId) {
                response.sendRedirect("error.jsp");
                return;
            }
            
            SharedActivityDAO sharedActivityDAO = new SharedActivityDAO();
            sharedActivityDAO.shareActivity(activityId, userId, shareText);
            
            // 设置活动相关信息
            request.setAttribute("activityType", activity.getActivityType());
            request.setAttribute("duration", activity.getDurationMinutes());
            request.setAttribute("date", activity.getDate());
            request.setAttribute("startTime", activity.getStartTime());
            request.setAttribute("temperature", activity.getTemperature());
            request.setAttribute("weatherCondition", activity.getWeatherCondition());
            request.setAttribute("totalCaloriesBurned", activity.getTotalCaloriesBurned());
            
            request.getRequestDispatcher("shareActivityResult.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

