package br.com.idealizeall.universitymanagement.repository;

import br.com.idealizeall.universitymanagement.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;


public class StudentRepository {
    private final Logger log = Logger.getLogger(StudentRepository.class.getName());
    private Connection connection;

    public StudentRepository(Connection connection){
        this.connection = connection;
    }

    private StudentRepository(){

    }

    public void save (Student newStudent){
        final String SQL = "INSERT INTO student (student_id,data_insert,status)" +
                " VALUES (?,?,?)";

        Integer studentID;
        Integer newStudentID = newStudent.getStudentId();

        if(newStudent.equals(null)) { throw new RuntimeException();}

        if(newStudentID == null || newStudentID <= 0){
            studentID = studentIDGenerator();
        } else {
            studentID = Integer.parseInt(LocalDate.now().getYear() + "0000") + newStudentID;
        }

        try (PreparedStatement pstmt = connection.prepareStatement(SQL)){

            pstmt.setInt(1,studentID);
            pstmt.setTimestamp(2, Timestamp.valueOf(newStudent.getDataInsert()));
            pstmt.setString(3,newStudent.getStatus());
            boolean rowAffected = pstmt.executeUpdate() == 1;

            if (rowAffected){
                log.info("----------------------------Student inserted successfully----------------------------");
            }

        } catch (SQLException ex){
            log.severe("Error at insert student " + ex.getMessage() + "SQL State: " + ex.getSQLState());
        }


    }


    private int studentIDGenerator(){
        int studentID = 0;
        String queryMaxId = "SELECT MAX(id) AS max_id FROM student";
        int year = LocalDate.now().getYear();

        try (PreparedStatement pstmt = connection.prepareStatement(queryMaxId)){
            ResultSet result = pstmt.executeQuery();

            if(result.next()){
                int maxID = result.getInt("max_id");
                int studentNewId = maxID == 0 ? 1 : maxID + 1;
                String newID = year + "0000";
                studentID = Integer.parseInt(newID) + studentNewId;

            }
        } catch (SQLException ex){
            log.severe("Error at query MAX student ID " + ex.getMessage());
        }
        return studentID;
    }

    public Student findById(int id){
        Student student;
        String sql = "SELECT id,student_id,data_insert,status FROM student WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
               student = Student.builder()
                        .id(rs.getInt("id"))
                        .studentId(rs.getInt("student_id"))
                        .dataInsert(rs.getTimestamp("data_insert").toLocalDateTime())
                        .status(rs.getString("status"))
                        .build();
                return student;
            }

        } catch (SQLException ex){
            log.severe("Error at find student by ID " + ex.getMessage());
        }

        return null;
    }

    public void deleteStudentById(int id){
        String SQL = "DELETE FROM student WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(SQL)){
            pstmt.setInt(1,id);
            boolean rowAffected = (pstmt.executeUpdate() > 0);
            if(rowAffected){
                log.info("Student successfully deleted.");
            }

        } catch (SQLException ex){
            log.severe("Error at delete Student: " + ex.getMessage());
        }
    }

    public void findAll(){
        String SQL = "SELECT * FROM student";
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()){
                int id = rs.getInt("id");
                int studentID = rs.getInt("student_id");
                String status = rs.getString("status");
                Timestamp dateInsert = rs.getTimestamp("data_insert");

                System.out.println("\nID: " + id + " Student ID: " + studentID + " Status: " + status + " Date inserted: " + dateInsert);


            }

        }catch (SQLException ex){
            log.severe("Erro" + ex.getMessage());

        }
    }

}

