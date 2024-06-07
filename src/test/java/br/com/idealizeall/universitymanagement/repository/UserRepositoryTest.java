package br.com.idealizeall.universitymanagement.repository;
import br.com.idealizeall.universitymanagement.config.DBConfigTest;
import br.com.idealizeall.universitymanagement.model.User;
import br.com.idealizeall.universitymanagement.model.UserRoles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private UserRepository underTest;
    private Connection connection;
    private final Logger log = Logger.getLogger(UserRepositoryTest.class.getName());

    @BeforeEach
    public void setUp() {
        connection = DBConfigTest.getH2Connection();
        underTest = new UserRepository(connection);
        String sql = "CREATE TABLE users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255), " +
                "password VARCHAR(255) NOT NULL, " +
                "data_create TIMESTAMP, " +
                "data_update TIMESTAMP, " +
                "role VARCHAR(50) NOT NULL" +
                ")";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE users");
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void itShouldCheckIfUserExistsByUsername() throws SQLException {
        // Given
        User underUser = User.builder()
                .username("testUser")
                .email("testUser@example.com")
                .password("password")
                .dataCreate(LocalDateTime.now())
                .role(UserRoles.ADMIN)
                .build();
        underTest.save(underUser);

        // when
        boolean expected = underTest.existsByUsername("testuser");

        // then
        assertTrue(expected);

    }

    @Test
    public void itShouldCheckWhenUserDoesNotExistsUsername() throws SQLException {
        // Given
        User underUser = User.builder()
                .username("testUser")
                .email("testUser@example.com")
                .password("password")
                .dataCreate(LocalDateTime.now())
                .role(UserRoles.ADMIN)
                .build();
        underTest.save(underUser);

        // when
        boolean expected = underTest.existsByUsername("testUser");

        // then
        assertFalse(expected);

    }
}
