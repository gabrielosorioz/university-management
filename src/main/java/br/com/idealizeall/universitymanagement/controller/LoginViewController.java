package br.com.idealizeall.universitymanagement.controller;

import br.com.idealizeall.universitymanagement.exception.UserException;
import br.com.idealizeall.universitymanagement.model.User;
import br.com.idealizeall.universitymanagement.model.UserRoles;
import br.com.idealizeall.universitymanagement.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.*;

public class LoginViewController implements Initializable {

    @FXML
    private AnchorPane adminForm,loginForm,studentForm,teacherForm;
    @FXML
    private PasswordField adminConfirmPassword,adminPassword;
    @FXML
    private Hyperlink adminSignInBtn,studentSignInBtn,teacherSignInBtn;
    @FXML
    private TextField adminUsername;
    @FXML
    private PasswordField loginPassword;

    @FXML
    private ComboBox<UserRoles> loginRole;
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField studentConfirmPassword,studentPassword;
    @FXML
    private Button studentSignUpBtn,teacherSignUpBtn,adminSignUpBtn,loginBtn;
    @FXML
    private TextField studentUsername,studentEmail;
    @FXML
    private PasswordField teacherPassword,teacherConfirmPassword;
    @FXML
    private TextField teacherUsername,teacherEmail;
    @FXML
    private Label admPassHasNum,admPass8Char,admPassHasCap,admPassHasLow, adminUsernameErrorMsg;
    @FXML
    private Label teacherPassHasNum,teacherPass8Char,teacherPassHasCap,teacherPassHasLow;
    @FXML
    private Label studentPassHasNum,studentPass8Char,studentPassHasCap ,studentPassHasLow;

    private TextField currentUsername, currentPassword, currentConfirmPassword, currentEmail;

    private List<Label> currentPassLabels;
    private List<UserRoles> rolesList;
    private ObservableList observableList;
    private UserService userService;

    enum FormType {
        LOGIN, ADMIN, STUDENT, TEACHER,
    }

    void loadCurrentFields(FormType formType){
        switch (formType) {
            case ADMIN -> {
                currentUsername = adminUsername;
                currentPassword = adminPassword;
                currentConfirmPassword = adminConfirmPassword;
                currentEmail = null;
                currentPassLabels = Arrays.asList(admPassHasNum, admPass8Char, admPassHasCap, admPassHasLow);
            }
            case STUDENT -> {
                currentUsername = studentUsername;
                currentPassword = studentPassword;
                currentConfirmPassword = studentConfirmPassword;
                currentEmail = studentEmail;
                currentPassLabels = Arrays.asList(studentPassHasNum, studentPass8Char, studentPassHasCap, studentPassHasLow);
            }
            case TEACHER -> {
                currentUsername = teacherUsername;
                currentPassword = teacherPassword;
                currentConfirmPassword = teacherConfirmPassword;
                currentEmail = teacherEmail;
                currentPassLabels = Arrays.asList(teacherPassHasNum, teacherPass8Char, teacherPassHasCap, teacherPassHasLow);
            }
        }
    }

    void loadRoles(){
        if(getSelectedRole() == null){
            setFormVisibility(false,false,false,false);
            rolesList = Arrays.asList(UserRoles.values());
            observableList = FXCollections.observableArrayList(rolesList);
            loginRole.setPromptText("Choose role: ");
            loginRole.setItems(observableList);
        }
    }
    void showForm(FormType formType){
        loadRoles();
        switch (formType){
            case LOGIN -> {
                setFormVisibility(true, false,false,false);
            }
            case ADMIN -> {
                setFormVisibility(false,true,false,false);
                loadCurrentFields(FormType.ADMIN);
                addPasswordListener(currentPassword);
            }
            case STUDENT -> {
                setFormVisibility(false, false, true, false);
                loadCurrentFields(FormType.STUDENT);
                addPasswordListener(currentPassword);

            }
            case TEACHER -> {
                setFormVisibility(false,false,false,true);
                loadCurrentFields(FormType.TEACHER);
                addPasswordListener(currentPassword);
            }
        }
    }

    void setFormVisibility(boolean login, boolean admin, boolean student, boolean teacher){
        loginForm.setVisible(login);
        adminForm.setVisible(admin);
        studentForm.setVisible(student);
        teacherForm.setVisible(teacher);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          signIn();
          loadRoles();
          loadPassLabels();
    }

    public void switchForm(ActionEvent actionEvent){
        resetFields();
        loadRoles();
        UserRoles selectedItem = getSelectedRole();
         switch(selectedItem){
             case ADMIN -> {showForm(FormType.ADMIN);}
             case STUDENT -> {showForm(FormType.STUDENT);}
             case TEACHER -> {showForm(FormType.TEACHER);}
             default -> {
                 showForm(FormType.LOGIN);
             }
         }
    }

    public void signIn(){
        showForm(FormType.LOGIN);
    }

    public void loadRoles(){
        List<UserRoles> rolesList = new ArrayList<>(Arrays.asList(UserRoles.values()));
        ObservableList dataList = FXCollections.observableArrayList(rolesList);
        loginRole.setPromptText("Choose role: ");
        loginRole.setItems(dataList);

    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void signUp(ActionEvent actionEvent){

        UserRoles selectedRole = getSelectedRole();
        String username = getUsername(selectedRole).getText();
        String password = getPassword(selectedRole).getText();
        String confirmPassword = getConfirmPassword(selectedRole).getText();


        boolean notBlank = fieldsAreNotBlank(getPassword(selectedRole),getUsername(selectedRole),getConfirmPassword(selectedRole));
        boolean equalPasswords = Objects.equals(password, confirmPassword);

        userService = new UserService();

        String email = null;

        if(notBlank && equalPasswords){
            User user = userService.createUserByRole(selectedRole,username,password, null);
            try {
                userService.registerUser(user);
                showAlert("Congratulations","Successfully register. ", "Now you can log in with your password and user",Alert.AlertType.INFORMATION);
                showForm(FormType.LOGIN);
                resetFields(selectedRole);
            } catch (UserException e) {
                handleUserException(e,selectedRole);
            }
            userService = null;
        }
        else if (!notBlank && equalPasswords){
            highLightEmptyFields(selectedRole);
            showAlert("Fields are empty","", "Please fill all fields",Alert.AlertType.ERROR);
        }
        else if (username.isBlank()){
            highLightEmptyFields(selectedRole);
            showAlert("Invalid Username","Username is blank","Please fill username correctly",Alert.AlertType.ERROR);
        }
        else if(!equalPasswords && notBlank) {

            showErrorFieldUI(getPassword(selectedRole));

            showAlert("Password error","", "password are different",Alert.AlertType.ERROR);
        }

    }

    private void resetFields(UserRoles role) {
        getUsername(role).clear();
        getPassword(role).clear();
        getConfirmPassword(role).clear();

        if (role == UserRoles.STUDENT) {
            studentEmail.clear();
        } else if (role == UserRoles.TEACHER) {
            teacherEmail.clear();
        }
    }

    private void showErrorFieldUI(TextField field){
        field.setStyle("-fx-border-color: #b20000");
        field.textProperty().addListener((observable) -> {
            field.setStyle("-fx-border-color: #FFFFFF");
        });
    }

    private void highLightEmptyFields(UserRoles role){
        boolean isBlank = getUsername(role).getText().isBlank();
        if(isBlank) this.showErrorFieldUI(getUsername(role));
        if(isBlank) this.showErrorFieldUI(getPassword(role));
        if(isBlank) this.showErrorFieldUI(getConfirmPassword(role));
    }

    private void addPasswordListener(UserRoles role,TextField passwordField){
        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            boolean hasCap = newValue.matches(".*[A-Z].*");
            boolean hasLow  = newValue.matches(".*[a-z].*");
            boolean hasNum = newValue.matches(".*\\d.*");
            boolean hasLength = newValue.length() > 7;
            addPasswordValidationUI(role,hasCap,hasLow,hasNum,hasLength);
        });
    }

    private void addPasswordValidationUI(UserRoles role,boolean hasCap, boolean hasLow , boolean hasNum, boolean hasLength){
        List<Label> labels;
        switch (role) {
            case ADMIN -> labels = adminPassLabels;
            case TEACHER -> labels = teacherPassLabels;
            case STUDENT -> labels = studentPassLabels;
            default -> throw new IllegalArgumentException("Invalid user role");
        }
        if(hasLength && hasCap && hasLow && hasNum){
            getPassword(role).setStyle("-fx-border-color: #3dcc00;");
        } else {
            getPassword(role).setStyle("-fx-border-color: #FFFFFF;");
        }

        /** Update each label's color based on the corresponding validation rule **/
        labels.get(0).setStyle("-fx-text-fill:" + (hasNum ? "#3dcc00;" : "#FFFFFF"));
        labels.get(1).setStyle("-fx-text-fill:" + (hasLength ? "#3dcc00;" : "#FFFFFF"));
        labels.get(2).setStyle("-fx-text-fill:" + (hasCap ? "#3dcc00;" : "#FFFFFF"));
        labels.get(3).setStyle("-fx-text-fill:" + (hasLow ? "#3dcc00;" : "#FFFFFF"));
    }

    void handleUserException(UserException exception, UserRoles role){
        switch (exception.getMessage()){
            case "Username is blank" -> {
                showAlert(exception.getMessage(), "Username cannot be null", "please fill the username", Alert.AlertType.ERROR);
            }
            case "Username already exists" -> {
                showAlert(exception.getMessage(),"Username ", "Please choose another username, if it's you, sign in",Alert.AlertType.ERROR);
            }

            case "Invalid user password" -> {
                showAlert(exception.getMessage(),"Insecure password", "Required: \n*at least one capital letter" +
                                "\n*at least one lower letter" +
                                "\n*at least one number"+
                                "\n*at least 8 characters"
                        ,Alert.AlertType.ERROR);
                showErrorFieldUI(getPassword(role));
            }
            default -> throw new IllegalStateException("Unexpected value: " + exception.getMessage());
        }

    }

    private TextField getUsername(UserRoles role){
        switch (role){
            case ADMIN -> { return this.adminUsername; }
            case STUDENT -> {return this.studentUsername;}
            case TEACHER -> {return this.teacherUsername;}
            default -> {throw new IllegalArgumentException("Invalid user role");}
        }
    }

    private TextField getPassword(UserRoles role){
        switch (role){
            case ADMIN -> { return this.adminPassword; }
            case STUDENT -> {return this.studentPassword;}
            case TEACHER -> {return this.teacherPassword;}
            default -> {throw new IllegalArgumentException("Invalid user role");}
        }
    }

    private TextField getConfirmPassword(UserRoles role){
        switch (role){
            case ADMIN -> { return this.adminConfirmPassword; }
            case STUDENT -> {return this.studentConfirmPassword;}
            case TEACHER -> {return this.teacherConfirmPassword;}
            default -> {throw new IllegalArgumentException("Invalid user role");}
        }
    }

    private TextField getEmail(UserRoles role){
        switch (role){
            case STUDENT -> {return this.studentEmail;}
            case TEACHER -> {return this.teacherEmail;}
            default -> {throw new IllegalArgumentException("Invalid user role");}
        }
    }

    private UserRoles getSelectedRole(){
        return loginRole.getSelectionModel().getSelectedItem();
    }

    private boolean fieldsAreNotBlank(TextField... fields) {
        return !Arrays.stream(fields).allMatch(field -> field.getText().isBlank());
    }
}
