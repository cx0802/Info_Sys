package com.example.servlet;

import com.example.dao.ExercisePlanDAO;
import com.example.dao.ExercisePlanDetailDAO;
import com.example.dao.ActivityDAO;
import com.example.model.ExercisePlan;
import com.example.model.ExercisePlanDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewExercisePlanServlet", value = "/ViewExercisePlanServlet")
public class ViewExercisePlanServlet extends HttpServlet {
    
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
            ExercisePlanDAO planDAO = new ExercisePlanDAO();
            ExercisePlanDetailDAO detailDAO = new ExercisePlanDetailDAO();
            ActivityDAO activityDAO = new ActivityDAO();
            
            // 获取用户当前的运动计划
            ExercisePlan plan = planDAO.getCurrentPlan(username);
            
            if (plan != null) {
                // 获取计划的详细信息
                List<ExercisePlanDetail> details = detailDAO.getDetailsByPlanId(plan.getPlanId());
                
                // 获取用户ID
                int userId = activityDAO.getUserIdByUsername(username);
                
                // 计算每个运动类型的已完成时长
                for (ExercisePlanDetail detail : details) {
                    int completedDuration = activityDAO.getCompletedDurationByType(
                        userId, 
                        detail.getActivityType(), 
                        plan.getStartDate(), 
                        plan.getEndDate()
                    );
                    detail.setCompletedDuration(completedDuration);
                }
                
                // 设置请求属性
                request.setAttribute("exercisePlan", plan);
                request.setAttribute("exerciseDetails", details);
            }
            
            // 转发到 JSP 页面
            request.getRequestDispatcher("viewExercisePlan.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
} 