package br.com.idealizeall.universitymanagement.repository;
import br.com.idealizeall.universitymanagement.model.Role;
import br.com.idealizeall.universitymanagement.model.User;

import java.sql.*;
import java.util.logging.Logger;

public class UserRepository {

    private final Logger log = Logger.getLogger(UserRepository.class.getName());

    private Connection connection;

    public UserRepository(Connection connection){
        this.connection = connection;
    }

    public User save(User user) {
        String sql = "INSERT INTO users (username,email,password,data_create,role_id) VALUES (?,?,?,?,?)";

        try(PreparedStatement pstmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2,user.getEmail());
            pstmt.setString(3,user.getPassword());
            pstmt.setTimestamp(4, Timestamp.valueOf(user.getDataCreate()));
            pstmt.setInt(5, user.getRole().getRoleID());
            boolean rowAffected = pstmt.executeUpdate() == 1;

            if(rowAffected){
                log.info("----------------------------User inserted successfully----------------------------");
            } else {
                throw new SQLException("ERROR: Creating user failed, no row affected. ");
            }

            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return User.builder()
                            .id(generatedKeys.getInt("id"))
                            .username(generatedKeys.getString("username"))
                            .email(generatedKeys.getString("email"))
                            .role(Role.getRoleById(generatedKeys.getInt("role_id")))
                            .dataCreate(generatedKeys.getTimestamp("data_create").toLocalDateTime())
                            .build();
                } else {
                    throw new SQLException("ERROR: Creating user failed, no ID obtained. ");
                }
            }

        } catch (SQLException ex) {
            log.severe("Error at insert user: " + ex.getMessage() + "SQL State: " + ex.getSQLState());
        }
        return null;
    }

    public boolean existsByUsername(String username){
        if (username.isBlank() || username.equals("")){
            return false;
        }
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException ex){
            ex.getLocalizedMessage();
            ex.printStackTrace();
        }
        return false;
    }
}
