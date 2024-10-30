package com.example.servlet;

import com.example.dao.UserDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EditAccountServlet", value = "/EditAccountServlet")
public class EditAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String oldUsername = (String) session.getAttribute("username");

        String newUsername = request.getParameter("newUsername");
        String newPassword = request.getParameter("newPassword");

        try {
            UserDAO userDAO = new UserDAO();
            userDAO.updateUser(oldUsername, newUsername, newPassword);

            // Update username for session
            session.setAttribute("username", newUsername);

            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
