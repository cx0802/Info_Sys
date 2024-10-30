package com.example.servlet;

import com.example.dao.ActivityDAO;
import com.example.model.Activity;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SearchActivityServlet", value = "/SearchActivityServlet")
public class SearchActivityServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String activityType = request.getParameter("activityType");
        String date = request.getParameter("date");

        try {
            ActivityDAO activityDAO = new ActivityDAO();
            List<Activity> activities = activityDAO.searchActivities(username, activityType, date);

            request.setAttribute("activities", activities);
            request.getRequestDispatcher("searchActivity.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
