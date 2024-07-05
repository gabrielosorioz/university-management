package br.com.idealizeall.universitymanagement.repository;
import br.com.idealizeall.universitymanagement.model.Teacher;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.logging.Logger;

public class TeacherRepository {

    private Connection connection;
    private final Logger log = Logger.getLogger(TeacherRepository.class.getName());

    public TeacherRepository(Connection connection){
        this.connection = connection;
    }

    public void save(Teacher teacher){
        String SQL = "INSERT INTO teacher (teacher_id,data_insert,status,user_id) VALUES (?,?,?,?)";

        if(teacher.equals(null)) { throw new RuntimeException();}

        Integer teacherID;
        Integer newTeacherID = teacher.getTeacherId();

        if(teacher.equals(null)) { throw new RuntimeException();}

        if(newTeacherID == null || newTeacherID <= 0){
            teacherID = teacherIDGenerator();
        } else {
            teacherID = Integer.parseInt(LocalDate.now().getYear() + "0000") + newTeacherID;
        }

        try(PreparedStatement pstmt = connection.prepareStatement(SQL)){
            pstmt.setInt(1,teacherID);
            pstmt.setTimestamp(2, Timestamp.from(Instant.now()));
            pstmt.setString(3,teacher.getStatus());
            pstmt.setInt(4,teacher.getUser().getId());
            boolean rowAffected = pstmt.executeUpdate() == 1;

            if (rowAffected){
                log.info("----------------------------Teacher inserted successfully----------------------------");
            }

        }catch (SQLException ex){
            log.severe("Error at insert teacher: " + ex.getMessage());
        }
    }

    public Teacher findById(int id){
        Teacher teacher;
        String sql = "SELECT id,teacher_id,data_insert,status FROM teacher WHERE teacher_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                teacher = Teacher.builder()
                        .id(rs.getInt("id"))
                        .teacherId(rs.getInt("teacher_id"))
                        .dateInsert(rs.getTimestamp("data_insert"))
                        .status(rs.getString("status"))
                        .build();
                return teacher;
            }

        } catch (SQLException ex){
            log.severe("Error at find teacher by ID " + ex.getMessage());
        }

        return null;
    }

    private int teacherIDGenerator(){
        int teacherID = 0;
        String queryMaxId = "SELECT MAX(id) AS max_id FROM teacher";
        int year = LocalDate.now().getYear();

        try (PreparedStatement pstmt = connection.prepareStatement(queryMaxId)){
            ResultSet result = pstmt.executeQuery();

            if(result.next()){
                int maxID = result.getInt("max_id");
                int teacherNewId = maxID == 0 ? 1 : maxID + 1;
                String newID = year + "0000";
                teacherID = Integer.parseInt(newID) + teacherNewId;

            }
        } catch (SQLException ex){
            log.severe("Error at query MAX teacher ID " + ex.getMessage());
        }
        return teacherID;
    }
}
