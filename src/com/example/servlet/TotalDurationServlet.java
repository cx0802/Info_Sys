package com.example.servlet;

import com.example.dao.ActivityDAO;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "TotalDurationServlet", value = "/TotalDurationServlet")
public class TotalDurationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        try {
            ActivityDAO activityDAO = new ActivityDAO();
            int totalDuration = activityDAO.getTotalDuration(username, startDate, endDate);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head>");
            out.println("<style>");
            out.println("body {font-family: Arial, sans-serif; background-color: #f0f0f0; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0;}");
            out.println(".container {background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); text-align: center;}");
            out.println("h2 {color: #333333;}");
            out.println("a {color: #4CAF50; text-decoration: none;}");
            out.println("a:hover {text-decoration: underline;}");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<div class='container'>");
            out.println("<h2>TOTAL TIME</h2>");
            if (totalDuration > 0) {
                out.println("<p>Total sport time from " + startDate + " to " + endDate + " is: " + totalDuration + " minutes</p>");
            } else {
                out.println("<p>NO RECORD FOUND</p>");
            }
            out.println("<p><a href='home.jsp'>BACK TO HOME</a></p>");
            out.println("</div>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}



