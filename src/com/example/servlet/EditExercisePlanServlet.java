package com.example.servlet;

import com.example.dao.ActivityDAO;
import com.example.dao.ExercisePlanDAO;
import com.example.dao.ExercisePlanDetailDAO;
import com.example.model.ActivityType;
import com.example.model.ExercisePlan;
import com.example.model.ExercisePlanDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "EditExercisePlanServlet", value = "/EditExercisePlanServlet")
public class EditExercisePlanServlet extends HttpServlet {

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
            String planIdStr = request.getParameter("planId");
            if (planIdStr == null || planIdStr.trim().isEmpty()) {
                // 如果没有planId参数，先获取用户当前计划
                ExercisePlanDAO planDAO = new ExercisePlanDAO();
                ExercisePlan currentPlan = planDAO.getCurrentPlan(username);
                if (currentPlan == null) {
                    response.sendRedirect("ViewExercisePlanServlet");
                    return;
                }
                planIdStr = String.valueOf(currentPlan.getPlanId());
            }
            
            int planId = Integer.parseInt(planIdStr);
            
            // 获取计划详情
            ExercisePlanDAO planDAO = new ExercisePlanDAO();
            ExercisePlan plan = planDAO.getPlanById(planId);
            
            // 获取计划项目详情
            ExercisePlanDetailDAO detailDAO = new ExercisePlanDetailDAO();
            List<ExercisePlanDetail> details = detailDAO.getDetailsByPlanId(planId);
            
            // 获取所有运动类型
            ActivityDAO activityDAO = new ActivityDAO();
            List<ActivityType> activityTypes = activityDAO.getActivityTypes();
            
            // 设置请求属性
            request.setAttribute("plan", plan);
            request.setAttribute("details", details);
            request.setAttribute("activityTypes", activityTypes);
            
            // 转发到编辑页面
            request.getRequestDispatcher("editExercisePlan.jsp").forward(request, response);
            
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
            int planId = Integer.parseInt(request.getParameter("planId"));
            
            // 更新计划基本信息
            ExercisePlan plan = new ExercisePlan();
            plan.setPlanId(planId);
            plan.setStartDate(Date.valueOf(request.getParameter("startDate")));
            plan.setEndDate(Date.valueOf(request.getParameter("endDate")));
            plan.setTotalDuration(Integer.parseInt(request.getParameter("totalDuration")));
            
            ExercisePlanDAO planDAO = new ExercisePlanDAO();
            planDAO.updatePlan(plan);
            
            // 更新计划详情
            String[] activityTypes = request.getParameterValues("activityType[]");
            String[] targetDurations = request.getParameterValues("targetDuration[]");
            String[] frequencies = request.getParameterValues("weeklyFrequency[]");
            String[] detailIds = request.getParameterValues("detailId[]");
            
            ExercisePlanDetailDAO detailDAO = new ExercisePlanDetailDAO();
            
            // 更新或添加计划详情
            for (int i = 0; i < activityTypes.length; i++) {
                ExercisePlanDetail detail = new ExercisePlanDetail();
                if (detailIds != null && i < detailIds.length) {
                    detail.setDetailId(Integer.parseInt(detailIds[i]));
                }
                detail.setPlanId(planId);
                detail.setActivityType(activityTypes[i]);
                detail.setTargetDuration(Integer.parseInt(targetDurations[i]));
                detail.setWeeklyFrequency(Integer.parseInt(frequencies[i]));
                
                if (detail.getDetailId() > 0) {
                    detailDAO.updateDetail(detail);
                } else {
                    detailDAO.saveDetail(detail);
                }
            }
            
            response.sendRedirect("ViewExercisePlanServlet");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
} 