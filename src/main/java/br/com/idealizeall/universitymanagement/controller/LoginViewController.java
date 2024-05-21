package br.com.idealizeall.universitymanagement.controller;

import br.com.idealizeall.universitymanagement.exception.UserException;
import br.com.idealizeall.universitymanagement.model.User;
import br.com.idealizeall.universitymanagement.model.UserRoles;
import br.com.idealizeall.universitymanagement.model.UserValidation;
import br.com.idealizeall.universitymanagement.service.UserService;
import javafx.animation.Animation;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private PasswordField adminConfirmPassword;

    @FXML
    private AnchorPane adminForm;

    @FXML
    private PasswordField adminPassword;

    @FXML
    private Hyperlink adminSignInBtn;

    @FXML
    private Button adminSignUpBtn;

    @FXML
    private TextField adminUsername;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private ComboBox<UserRoles> loginRole;

    @FXML
    private TextField loginUsername;

    @FXML
    private Label adminUsernameErrorMsg;

    @FXML
    private PasswordField studentConfirmPassword;

    @FXML
    private TextField studentEmail;

    @FXML
    private AnchorPane studentForm;

    @FXML
    private TextField studentPassword;

    @FXML
    private Hyperlink studentSignInBtn;

    @FXML
    private Button studentSignUpBtn;

    @FXML
    private TextField studentUsername;

    @FXML
    private PasswordField teacherConfirmPassword;

    @FXML
    private TextField teacherEmail;

    @FXML
    private AnchorPane teacherForm;

    @FXML
    private TextField teacherPassword;

    @FXML
    private Hyperlink teacherSignInBtn;

    @FXML
    private Button teacherSignUpBtn;

    @FXML
    private TextField teacherUsername;

    @FXML
    private Label admPassHasNum;

    @FXML
    private Label admPassHasLow;

    @FXML
    private Label admPassHasCap;

    @FXML
    private Label admPass8Char;

    private UserService userService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginForm();

    }

    public void loginForm(){
        loginForm.setVisible(true);
        adminForm.setVisible(false);
        studentForm.setVisible(false);
        teacherForm.setVisible(false);
        loadRoles();
    }

    public void adminForm(){
        adminForm.setVisible(true);
        loginForm.setVisible(false);
        studentForm.setVisible(false);
        teacherForm.setVisible(false);
        checkPassword(adminPassword);
        checkUsername(adminUsername);
    }

    public void studentForm(){
        studentForm.setVisible(true);
        adminForm.setVisible(false);
        loginForm.setVisible(false);
        teacherForm.setVisible(false);
    }

    public void teacherForm(){
        teacherForm.setVisible(true);
        studentForm.setVisible(false);
        adminForm.setVisible(false);
        loginForm.setVisible(false);

    }

    public void switchForm(ActionEvent actionEvent){
         UserRoles selectedItem = loginRole.getSelectionModel().getSelectedItem();
         switch(selectedItem){
             case ADMIN:
                 adminForm();
                 break;
             case STUDENT:
                 studentForm();
                 break;
             case TEACHER:
                 teacherForm();
                 break;
             default:
                 break;
         }

    }

    public void loadRoles(){
        List<UserRoles> rolesList = new ArrayList<UserRoles>(Arrays.asList(UserRoles.values()));
        ObservableList dataList = FXCollections.observableArrayList(rolesList);
        loginRole.setItems(dataList);
    }

    void checkPassword(PasswordField passwordField){
        passwordField.textProperty().addListener(new ChangeListener<String>() {

            boolean hasNum = false;
            boolean hasCap = false;
            boolean hasLow = false;
            boolean hasLenght = false;

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                hasCap = newValue.matches(".*[A-Z].*");
                hasLow  = newValue.matches(".*[a-z].*");
                hasNum = newValue.matches(".*\\d.*");
                hasLenght = newValue.length() > 7;

                if (hasLenght){
                    admPass8Char.setStyle("-fx-text-fill: #3dcc00;");
                } else {
                    admPass8Char.setStyle("-fx-text-fill: #FFFFFF;");
                }
                if(hasCap){
                    System.out.println("Contém maiúscula");
                    admPassHasCap.setStyle("-fx-text-fill: #3dcc00;");

                } else {
                    System.out.println("Não contém maiúscula");
                    admPassHasCap.setStyle("-fx-text-fill: #FFFFFF;");
                }
                if(hasNum){
                    admPassHasNum.setStyle("-fx-text-fill: #3dcc00;");
                    System.out.println("Contém Número");
                } else {
                    admPassHasNum.setStyle("-fx-text-fill: #FFFFFF;");
                    System.out.println("Não contém número");
                }
                if(hasLow){
                    System.out.println("Contém letra minúscula");
                    admPassHasLow.setStyle("-fx-text-fill: #3dcc00;");
                } else {
                    admPassHasLow.setStyle("-fx-text-fill: #FFFFFF;");
                    System.out.println("Não contém letra minúscula");
                }
                if (hasLow && hasLenght && hasCap && hasNum){
                    adminPassword.setStyle("-fx-border-color: #3dcc00;");
                } else {
                    adminPassword.setStyle("-fx-border-color: #d1d1d1;");
                }
            }
        });
    }

    //Task , validar se os campos password e confirmPassword são iguais.

    void checkUsername(TextField username){
        username.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("Alterado.");
                username.setStyle("-fx-border-color: #ffff");
                adminUsernameErrorMsg.setVisible(false);
            }
        });
    }

    public void saveUser(ActionEvent actionEvent){
        if(fieldIsNotBlank(adminPassword,adminUsername,adminConfirmPassword)){


        } else {
            userService = new UserService();
            User newUser = User.builder()
                    .username(adminUsername.getText())
                    .password(adminPassword.getText())
                    .dataCreate(LocalDateTime.now())
                    .role(UserRoles.ADMIN)
                    .build();
            try {

                userService.registerUser(newUser);
            } catch (UserException e) {
                handleUserException(e);
            }
            userService = null;
        }
    }

    public void showErrorUsername(TextField username){
        username.setStyle("-fx-border-color: #fc2f2f;");
        checkUsername(username);
    }

    public void showErrorPassword(PasswordField passwordField){
        passwordField.setStyle("-fx-border-color: #fc2f2f;");

    }

    void handleUserException(UserException exception){
        String exceptionMessage = exception.getMessage();
        switch (exceptionMessage){
            case "Invalid user password":
                showErrorPassword(adminPassword);
                break;
            case "User already exists":
                showErrorUsername(adminUsername);
            case "Username is blank":

            default:
                break;
        }

    }

    boolean fieldIsNotBlank(PasswordField password, TextField username, PasswordField confirmPassword){
        boolean isValid = false;

        boolean hasPass = !password.getText().isBlank();
        boolean hasUser = !username.getText().isBlank();
        boolean hasCPass = !confirmPassword.getText().isBlank();

        if(hasCPass && hasUser && hasPass){
            isValid = true;
        }

        return isValid;
    }
    public void handleBtnClick(ActionEvent actionEvent) {


    }
}
