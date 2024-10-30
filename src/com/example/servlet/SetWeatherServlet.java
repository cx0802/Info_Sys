package com.example.servlet;

import com.example.dao.WeatherDAO;
import com.example.model.Weather;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SetWeatherServlet", value = "/SetWeatherServlet")
public class SetWeatherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date");
        String weekday = request.getParameter("weekday");
        float temperature = Float.parseFloat(request.getParameter("temperature"));
        String weatherCondition = request.getParameter("weather_condition");

        try {
            Weather weather = new Weather();
            weather.setDate(date);
            weather.setWeekday(weekday);
            weather.setTemperature(temperature);
            weather.setWeatherCondition(weatherCondition);

            WeatherDAO weatherDAO = new WeatherDAO();
            weatherDAO.setWeather(weather);

            response.sendRedirect("adminHome.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}

