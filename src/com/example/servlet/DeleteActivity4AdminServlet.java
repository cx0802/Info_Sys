package com.example.servlet;

import com.example.dao.ActivityDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 * DeleteActivity4AdminServlet.java is a Servlet for administrators to delete one activity. The ONLY difference between this
 * class and DeleteActivityAdminServlet.java is this class will redirect the user to viewAllActivities.jsp page
 * @author Bohan Zhang
 */

@WebServlet(name = "DeleteActivity4AdminServlet", value = "/DeleteActivity4AdminServlet")
public class DeleteActivity4AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activityIdStr = request.getParameter("activityId");

        try {
            int activityId = Integer.parseInt(activityIdStr);
            ActivityDAO activityDAO = new ActivityDAO();
            activityDAO.deleteActivity(activityId);

            response.sendRedirect("ViewAllActivitiesServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}