package br.com.idealizeall.universitymanagement.repository;
import br.com.idealizeall.universitymanagement.config.DBConfig;
import br.com.idealizeall.universitymanagement.model.User;

import java.sql.*;
import java.util.logging.Logger;

public class UserRepository {

    private final Logger log = Logger.getLogger(UserRepository.class.getName());

    private Connection connection;

    protected void connect(){
        try {
            if(connection == null || connection.isClosed()){
                connection = DBConfig.getPSQLConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void disconnect(){
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(User user) {
        String sql = "INSERT INTO users (username,email,password,data_create,role) VALUES (?,?,?,?,?)";
        connect();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2,user.getEmail());
            pstmt.setString(3,user.getPassword());
            pstmt.setTimestamp(4, Timestamp.valueOf(user.getDataCreate()));
            pstmt.setString(5, user.getRole().name());
            boolean rowAffected = pstmt.executeUpdate() > 0;
            disconnect();

            if(rowAffected){
                log.info("----------------------------User inserted successfully----------------------------");
            } else {
                log.severe("----------------------------Couldn't insert user----------------------------");
            }

        } catch (SQLException ex) {
            log.severe("Error at insert user " + ex.getLocalizedMessage() + "SQL State: " + ex.getSQLState());
        } finally {
            disconnect();
        }
    }

    public boolean existsByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        connect();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException ex){
            ex.getLocalizedMessage();
            ex.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    public void update(User user){
        String sql = "INSERT INTO users (username,email,password,data_update,role) VALUES (?,?,?,?,?)";
        connect();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setTimestamp(4,Timestamp.valueOf(user.getDataUpdate()));
            pstmt.setString(5,user.getRole().name());
            boolean rowAffected = pstmt.executeUpdate() > 0;
            disconnect();

            if(rowAffected){
                log.info("User updated successfully");
            } else {
                log.severe("Error at update User");
            }

        } catch (SQLException ex){
            log.severe("Couldn't update user");
            ex.printStackTrace();
            disconnect();
        }
    }
}
