package br.com.idealizeall.universitymanagement.repository;
import br.com.idealizeall.universitymanagement.config.DBConfigTest;
import br.com.idealizeall.universitymanagement.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.*;

public class StudentRepositoryTest {
    private StudentRepository underTest;
    private Connection connection;
    private final Logger log = Logger.getLogger(StudentRepositoryTest.class.getName());


    @BeforeEach
    public void setUp(){
        connection = DBConfigTest.getH2Connection();
        underTest = new StudentRepository(connection);

        String sql = "CREATE TABLE student (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY , " +
                "student_id BIGINT NOT NULL UNIQUE, " +
                "full_name VARCHAR(255), " +
                "gender VARCHAR(40), " +
                "birth_data DATE, " +
                "year_study VARCHAR(255), " +
                "course VARCHAR(255), " +
                "section VARCHAR(55), " +
                "payment DOUBLE PRECISION, " +
                "image VARCHAR(500), " +
                "data_insert TIMESTAMP, " +
                "data_delete TIMESTAMP, " +
                "data_update TIMESTAMP, " +
                "status VARCHAR(11), " +
                "status_payment VARCHAR(250)" +
                ")";

        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()){
            stmt.execute("DROP TABLE student");
        }
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }

    private Student createTestStudent(String status, LocalDateTime dateInsert){
        return Student.builder()
                .status(status)
                .dataInsert(dateInsert)
                .build();
    }

    private Student createTestStudent(String status, LocalDateTime dateInsert,Integer studentID){
        return Student.builder()
                .studentId(studentID)
                .status(status)
                .dataInsert(dateInsert)
                .build();
    }

    @Test
    public void testInsertAndRetrieveStudent(){
        Student newStudent = createTestStudent("active", LocalDateTime.now());
        underTest.save(newStudent);
        int expectedID = Integer.parseInt(LocalDate.now().getYear() + "0000") + 1;
        Student savedStudent = underTest.findById(expectedID);


        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getStudentId());
        assertTrue(savedStudent.getStudentId() == expectedID);

        log.info("\nID: " + savedStudent.getId() +
                "\nStudent ID: " + savedStudent.getStudentId() +
                "\nDate inserted: " + savedStudent.getDataInsert());
    }

    @Test
    public void testInsertAndRetrieveByStudentID(){
        Student newStudent = createTestStudent("active", LocalDateTime.now(),54);
        underTest.save(newStudent);
        int expectedID = Integer.parseInt(LocalDate.now().getYear() + "0000") + 54;
        Student savedStudent = underTest.findById(expectedID);

        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getStudentId());
        assertTrue(savedStudent.getStudentId() == expectedID);

        log.info("\nID: " + savedStudent.getId() +
                "\nStudent ID: " + savedStudent.getStudentId() +
                "\nDate inserted: " + savedStudent.getDataInsert());
    }

    @Test
    public void testDeleteStudentById(){

        Student newStudent = createTestStudent("active",LocalDateTime.now());
        underTest.save(newStudent);
        int expectedID = Integer.parseInt(LocalDate.now().getYear() + "0000") + 1;
        underTest.deleteStudentById(expectedID);
        Student savedStudent = underTest.findById(expectedID);

        assertNull(savedStudent);

    }

    @Test
    public void testDeleteStudentByStudent_ID(){
        Student newStudent = createTestStudent("active",LocalDateTime.now());
        underTest.save(newStudent);
        int expectedID = Integer.parseInt(LocalDate.now().getYear() + "0000") + 1;

        Student savedStudent = underTest.findById(expectedID);
        underTest.deleteStudentById(savedStudent.getStudentId());
        savedStudent = underTest.findById(expectedID);

        assertNull(savedStudent);

    }

    @Test
    public void testStudentOrderByStudent_ID(){
        int expectedID = Integer.parseInt(LocalDate.now().getYear() + "0000") + 20;

        for (int i = 1; i <= 20; i++){
            Student newStudent = createTestStudent("active " + i, LocalDateTime.now());
            underTest.save(newStudent);
        }
        underTest.deleteStudentById(expectedID);
        Student student = createTestStudent("active 21", LocalDateTime.now());
        underTest.save(student);
        Student savedStudent = underTest.findById(expectedID);
        underTest.findAll();

        assertEquals("active 21", savedStudent.getStatus());


    }

}