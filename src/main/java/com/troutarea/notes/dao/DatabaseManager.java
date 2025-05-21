package com.troutarea.notes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/dmitrijgajdukov"; // Замените <host>, <port>, <database>
    private static final String USER = "dmitrijgajdukov"; // Укажите ваше имя пользователя
    private static final String PASS = "4717847"; // Укажите ваш пароль

    public static void main(String[] args) {
        try {
            initializeDatabase();

            try (Connection conn = getConnection()) {
                if (conn != null) {
                    System.out.println("Соединение установлено успешно.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS Note (" +
                "id SERIAL PRIMARY KEY," +
                "tour TEXT," +
                "lure TEXT," +
                "lureDesc TEXT," +
                "descriptionWiring TEXT," +
                "fishActivity TEXT," +
                "horizon TEXT," +
                "waterClarity TEXT," +
                "weather TEXT," +
                "castDistance TEXT," +
                "fishingGear TEXT," +
                "sector INTEGER," +
                "date TEXT" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}