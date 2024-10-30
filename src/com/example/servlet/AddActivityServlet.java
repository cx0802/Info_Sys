package com.example.servlet;

import com.example.dao.ActivityDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "AddActivityServlet", value = "/AddActivityServlet")
public class AddActivityServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ActivityDAO activityDAO = new ActivityDAO();
            List<String> activityTypes = activityDAO.getActivityTypes();  // 获取活动类型列表
            request.setAttribute("activityTypes", activityTypes);  // 将列表传递给JSP
            request.getRequestDispatcher("addActivity.jsp").forward(request, response);  // 转发到JSP页面
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理添加活动的逻辑...
    }
}
