package com.example.servlet;

import com.example.dao.ActivityDAO;
import com.example.model.Activity;
import com.example.model.ActivityType;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AddActivityServlet", value = "/AddActivityServlet")
public class AddActivityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 调用 DAO 层获取活动类型列表
            ActivityDAO activityDAO = new ActivityDAO();
            List<ActivityType> activityTypes = activityDAO.getActivityTypes();

            // 将活动类型列表设置为请求属性
            request.setAttribute("activityTypes", activityTypes);

            // 转发到 JSP 页面
            request.getRequestDispatcher("addActivity.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String activityType = request.getParameter("activityType");
        String dateStr = request.getParameter("date");
        String startTimeStr = request.getParameter("startTime") + ":00";  // Ensure time format
        int duration = Integer.parseInt(request.getParameter("duration"));
        try {
            ActivityDAO activityDAO = new ActivityDAO();
            int userId = activityDAO.getUserIdByUsername(username);

            if (userId != -1) {
                Activity activity = new Activity();
                activity.setUserId(userId);
                activity.setActivityType(activityType);
                activity.setDate(Date.valueOf(dateStr));
                activity.setStartTime(Time.valueOf(startTimeStr));
                activity.setDurationMinutes(duration);

                activityDAO.addActivity(activity);

                response.sendRedirect("home.jsp");
            } else {
                response.sendRedirect("error.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}


