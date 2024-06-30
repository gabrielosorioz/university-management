package br.com.idealizeall.universitymanagement;

import br.com.idealizeall.universitymanagement.config.DBConfig;
import br.com.idealizeall.universitymanagement.controller.LoginViewController;
import br.com.idealizeall.universitymanagement.model.User;
import br.com.idealizeall.universitymanagement.repository.StudentRepository;
import br.com.idealizeall.universitymanagement.repository.UserRepository;
import br.com.idealizeall.universitymanagement.service.StudentService;
import br.com.idealizeall.universitymanagement.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class UniversityManagementSystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Connection conn = DBConfig.getPSQLConnection();
        UserRepository userRepository = new UserRepository(conn);
        UserService userService = new UserService(userRepository);
        StudentRepository studentRepository = new StudentRepository(conn);
        StudentService studentService = new StudentService(studentRepository);
        FXMLLoader loader = new FXMLLoader(UniversityManagementSystem.class.getResource("login-view.fxml"));
        loader.setControllerFactory(param -> new LoginViewController(userService,studentService));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
    public static void main (String[]args){
        launch();
    }
}