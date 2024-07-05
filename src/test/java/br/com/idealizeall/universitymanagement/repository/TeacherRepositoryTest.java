package br.com.idealizeall.universitymanagement.repository;

import br.com.idealizeall.universitymanagement.config.DBConfigTest;
import br.com.idealizeall.universitymanagement.model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherRepositoryTest {

    private TeacherRepository underTest;
    private Connection connection;
    private Logger log = Logger.getLogger(TeacherRepositoryTest.class.getName());

    @BeforeEach
    public void setUp(){

        connection = DBConfigTest.getH2Connection();
        underTest = new TeacherRepository(connection);

        String sql = "CREATE TABLE teacher ("
                + "id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                + "teacher_id BIGINT NOT NULL, "
                + "full_name VARCHAR(255), "
                + "gender VARCHAR(255), "
                + "birth_date DATE, "
                + "year_experience VARCHAR(250), "
                + "experience VARCHAR(255), "
                + "department VARCHAR(255), "
                + "salary DOUBLE PRECISION, "
                + "salary_status VARCHAR(11), "
                + "image VARCHAR(500), "
                + "data_insert TIMESTAMP, "
                + "data_delete TIMESTAMP, "
                + "data_update TIMESTAMP,"
                + "status VARCHAR(50)"
                + ");";


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
            stmt.execute("DROP TABLE teacher");
        }
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }

    @Test
    void itShouldSaveAndRetrieveTeacher(){
        Teacher teacher = Teacher.builder()
                .status("active")
                .build();
        underTest.save(teacher);

        int expectedID = Integer.parseInt(LocalDate.now().getYear() + "0000") + 1;
        Teacher expectedTeacher = underTest.findById(expectedID);

        log.info("ID: " + expectedTeacher.getId() +
                "\nStudent ID: " + expectedTeacher.getTeacherId() +
                "\nDate inserted: " + expectedTeacher.getDateInsert());

        assertNotNull(teacher);
        assertNotNull(expectedTeacher.getTeacherId());
        assertTrue(expectedTeacher.getTeacherId() == expectedID);


    }



}
