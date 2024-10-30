package com.example.servlet;

import com.example.dao.ActivityDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "DeleteActivityServlet", value = "/DeleteActivityServlet")
public class DeleteActivityServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activityIdStr = request.getParameter("activityId");

        try {
            int activityId = Integer.parseInt(activityIdStr);
            ActivityDAO activityDAO = new ActivityDAO();
            activityDAO.deleteActivity(activityId);

            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}

