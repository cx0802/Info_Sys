package com.example.servlet;

import com.example.dao.ExercisePlanDAO;
import com.example.dao.ExercisePlanDetailDAO;
import com.example.dao.ActivityDAO;
import com.example.model.ExercisePlan;
import com.example.model.ExercisePlanDetail;
import com.example.model.ActivityType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "CreateExercisePlanServlet", value = "/CreateExercisePlanServlet")
public class CreateExercisePlanServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            ActivityDAO activityDAO = new ActivityDAO();
            List<ActivityType> activityTypes = activityDAO.getActivityTypes();
            request.setAttribute("activityTypes", activityTypes);
            request.getRequestDispatcher("createExercisePlan.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
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
            // 获取基本信息
            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));
            int totalDuration = Integer.parseInt(request.getParameter("totalDuration"));
            
            // 创建计划
            ExercisePlan plan = new ExercisePlan();
            plan.setStartDate(startDate);
            plan.setEndDate(endDate);
            plan.setTotalDuration(totalDuration);
            
            // 获取用户ID并保存计划
            ActivityDAO activityDAO = new ActivityDAO();
            int userId = activityDAO.getUserIdByUsername(username);
            plan.setUserId(userId);
            
            ExercisePlanDAO planDAO = new ExercisePlanDAO();
            planDAO.createPlan(plan);
            
            // 保存计划详情
            String[] activityTypes = request.getParameterValues("activityType[]");
            String[] targetDurations = request.getParameterValues("targetDuration[]");
            String[] frequencies = request.getParameterValues("weeklyFrequency[]");
            
            ExercisePlanDetailDAO detailDAO = new ExercisePlanDetailDAO();
            
            for (int i = 0; i < activityTypes.length; i++) {
                ExercisePlanDetail detail = new ExercisePlanDetail();
                detail.setPlanId(plan.getPlanId());
                detail.setActivityType(activityTypes[i]);
                detail.setTargetDuration(Integer.parseInt(targetDurations[i]));
                detail.setWeeklyFrequency(Integer.parseInt(frequencies[i]));
                detailDAO.saveDetail(detail);
            }
            
            response.sendRedirect("ViewExercisePlanServlet");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
} 