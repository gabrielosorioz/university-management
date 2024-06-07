package br.com.idealizeall.universitymanagement.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfigTest {

    public static Connection getH2Connection()  {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        } catch (SQLException e) {
            System.out.println("Erro");
            throw new RuntimeException(e);
        }

    }
}
