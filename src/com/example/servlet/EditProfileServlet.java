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

@WebServlet(name = "EditProfileServlet", value = "/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // 调试信息：检查获取的用户名
        System.out.println("Retrieved username from session: " + username);

        try {
            UserDAO userDAO = new UserDAO();
            User userProfile = userDAO.getUserProfileByUsername(username);  // 获取用户的当前个人信息

            if (userProfile != null) {

                // 设置请求属性
                request.setAttribute("userRegion", userProfile.getRegion());
                request.setAttribute("userGender", userProfile.getGender());
                request.setAttribute("userAge", String.valueOf(userProfile.getAge()));   // 转换为 String
                request.setAttribute("userWeight", String.valueOf(userProfile.getWeight())); // 转换为 String
                request.setAttribute("userHeight", String.valueOf(userProfile.getHeight())); // 转换为 String

                request.getRequestDispatcher("editProfile.jsp").forward(request, response);
            } else {
                System.out.println("User profile not found in database.");
                response.sendRedirect("error.html");  // 若未能获取用户信息，重定向至错误页面
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // 获取用户输入的个人信息
        String region = request.getParameter("region");
        String gender = request.getParameter("gender");
        int age = Integer.parseInt(request.getParameter("age"));
        double weight = Double.parseDouble(request.getParameter("weight"));
        double height = Double.parseDouble(request.getParameter("height"));

        try {
            UserDAO userDAO = new UserDAO();

            // 使用 username 调用 updateUserProfile
            userDAO.updateUserProfile(username, region, gender, age, weight, height);

            // 重定向回主页或成功页面
            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }

}
