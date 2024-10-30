package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.model.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SetProfileServlet", value = "/SetProfileServlet")
public class SetProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String region = request.getParameter("region");
        String gender = request.getParameter("gender");
        int age = Integer.parseInt(request.getParameter("age"));
        double weight = Double.parseDouble(request.getParameter("weight"));
        double height = Double.parseDouble(request.getParameter("height"));

        try {
            User user = new User();
            user.setUsername(username);
            user.setRegion(region);
            user.setGender(gender);
            user.setAge(age);
            user.setWeight(weight);
            user.setHeight(height);

            UserDAO userDAO = new UserDAO();
            userDAO.setUserProfile(user);

            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
