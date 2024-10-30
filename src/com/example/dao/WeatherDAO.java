package com.example.dao;

import com.example.model.Weather;
import com.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class WeatherDAO {
    // Method to set up one date's weather information.
    public void setWeather(Weather weather) throws Exception {
        try (Connection con = DBUtil.getConnection()) {
            String query = "INSERT INTO Weather (date, weekday, temperature, weather_condition) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE weekday=?, temperature=?, weather_condition=?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, weather.getDate());
                pst.setString(2, weather.getWeekday());
                pst.setFloat(3, weather.getTemperature());
                pst.setString(4, weather.getWeatherCondition());
                pst.setString(5, weather.getWeekday());
                pst.setFloat(6, weather.getTemperature());
                pst.setString(7, weather.getWeatherCondition());
                pst.executeUpdate();
            }
        }
    }
}
