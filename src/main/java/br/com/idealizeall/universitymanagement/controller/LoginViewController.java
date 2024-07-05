    package br.com.idealizeall.universitymanagement.controller;

    import br.com.idealizeall.universitymanagement.exception.UserException;
    import br.com.idealizeall.universitymanagement.model.*;
    import br.com.idealizeall.universitymanagement.service.StudentService;
    import br.com.idealizeall.universitymanagement.service.TeacherService;
    import br.com.idealizeall.universitymanagement.service.UserService;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.event.EventHandler;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.control.*;
    import javafx.scene.input.KeyCode;
    import javafx.scene.input.KeyEvent;
    import javafx.scene.layout.AnchorPane;
    import org.w3c.dom.Text;

    import java.net.URL;
    import java.sql.Timestamp;
    import java.time.Instant;
    import java.time.LocalDateTime;
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
    private ComboBox<FormType> loginRole;
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
    private List<FormType> formTypeList;
    private Role currentRole;
    private ObservableList observableList;
    private UserService userService;
    private StudentService studentService;
    private TeacherService teacherService;

    enum FormType {
        LOGIN, ADMIN, STUDENT, TEACHER
    }

    public LoginViewController(UserService userService, StudentService studentService,TeacherService teacherService){
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }


    private void loadCurrentFields(FormType formType){
        if (formType == FormType.ADMIN){
            loadAdminFields();
        }
        if(formType == FormType.STUDENT){
            loadStudentFields();
        }
        if(formType == FormType.TEACHER){
            loadTeacherFields();
        }
    }

    private void addTabTraversal(TextField... textFields){
        for(int i =0; i < textFields.length; i++){
            int nextIndex = (i +1) % textFields.length;

            final int currentIndex = i;
            final int nextFieldIndex = nextIndex;

            textFields[currentIndex].addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(keyEvent.getCode() == KeyCode.TAB){
                    keyEvent.consume();

                    // focus on next field
                    textFields[nextFieldIndex].requestFocus();
                }
            });

        }
    }


    private void loadTeacherFields() {
        currentUsername = teacherUsername;
        currentPassword = teacherPassword;
        currentConfirmPassword = teacherConfirmPassword;
        currentEmail = teacherEmail;
        currentPassLabels = Arrays.asList(teacherPassHasNum, teacherPass8Char, teacherPassHasCap, teacherPassHasLow);
        currentRole = Role.TEACHER;
        teacherEmail.requestFocus();
        eventFilterEnterKey(teacherUsername,teacherPassword,teacherEmail,teacherConfirmPassword);
        addTabTraversal(teacherEmail,teacherUsername,teacherPassword,teacherConfirmPassword);

    }

    private void loadStudentFields () {
        currentUsername = studentUsername;
        currentPassword = studentPassword;
        currentConfirmPassword = studentConfirmPassword;
        currentEmail = studentEmail;
        currentPassLabels = Arrays.asList(studentPassHasNum, studentPass8Char, studentPassHasCap, studentPassHasLow);
        currentRole = Role.STUDENT;
        studentEmail.requestFocus();
        eventFilterEnterKey(studentUsername,studentPassword,studentConfirmPassword,studentEmail);
        addTabTraversal(studentEmail,studentUsername,studentPassword,studentConfirmPassword);
    }

    private void loadAdminFields (){
        currentUsername = adminUsername;
        currentPassword = adminPassword;
        currentConfirmPassword = adminConfirmPassword;
        currentEmail = null;
        currentPassLabels = Arrays.asList(admPassHasNum, admPass8Char, admPassHasCap, admPassHasLow);
        currentRole = Role.ADMIN;
        adminUsername.requestFocus();
        eventFilterEnterKey(adminUsername,adminPassword,adminConfirmPassword);
    }

    private void loadFormList(){
            formTypeList  = Arrays.asList(FormType.values());
            observableList = FXCollections.observableArrayList(formTypeList);
            loginRole.setPromptText("Choose role: ");
            loginRole.setItems(observableList);
    }


    private void showForm(FormType formType){
        switch (formType){
            case LOGIN -> {
                loginRole.getSelectionModel().select(FormType.LOGIN);
                setFormVisibility(true, false,false,false);
                currentRole = null;

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

    private void setFormVisibility(boolean login, boolean admin, boolean student, boolean teacher){
        loginForm.setVisible(login);
        adminForm.setVisible(admin);
        studentForm.setVisible(student);
        teacherForm.setVisible(teacher);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signIn();
    }

    public void switchForm(ActionEvent actionEvent){
        resetFields();
        FormType selectedForm = loginRole.getSelectionModel().getSelectedItem();
        if (selectedForm != null){
            switch(selectedForm){
                case ADMIN -> {showForm(FormType.ADMIN);}
                case STUDENT -> {showForm(FormType.STUDENT);}
                case TEACHER -> {showForm(FormType.TEACHER);}
            }
        }
    }

    private void eventFilterEnterKey(TextField... textFields){
            Arrays.stream(textFields).forEach(textField -> {
                textField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                    if(keyEvent.getCode() == KeyCode.ENTER){
                        signUp();
                        keyEvent.consume();
                    }
                });
            });
        }

    public void signIn(){
        loadFormList();
        showForm(FormType.LOGIN);
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void signUp(){

        if(currentUsername == null || currentPassword == null || currentConfirmPassword == null){
            showAlert("Error", "Form not loaded", "Please select a valid form to sign up", Alert.AlertType.ERROR);
            return;
        }

        String usernameTxt = currentUsername.getText();
        String passwordTxt = currentPassword.getText();
        String confirmPasswordTxt = currentConfirmPassword.getText();
        String emailTxt = (currentEmail != null) ? currentEmail.getText() : null;

        boolean notBlank = fieldsAreNotBlank(usernameTxt, passwordTxt, confirmPasswordTxt,emailTxt);
        boolean equalPasswords = passwordTxt.equals(confirmPasswordTxt);

        if(!notBlank){
            highLightEmptyFields();
            showAlert("Fields are empty", "", "Please fill all fields", Alert.AlertType.ERROR);
            return;
        }

        if(!equalPasswords){
            showErrorFieldUI(currentConfirmPassword);
            showAlert("Password error","","Passwords do not match", Alert.AlertType.ERROR);
            return;
        }

        try {
            User user = createUser(currentUsername, currentPassword,currentEmail,currentRole);
            if(user != null){
                User savedUser = userService.registerUser(user);
                registerUserByRole(savedUser);
                showAlert("Congratulations","Successfully registered", "Now you can log in with your user and password",Alert.AlertType.INFORMATION);
                resetFields();
                showForm(FormType.LOGIN);
            }
        } catch (UserException e){
            handleUserException(e);
        }
    }

    private void resetFields() {
        if (currentUsername != null) currentUsername.clear();
        if (currentEmail != null) currentEmail.clear();
        if (currentPassword != null) currentPassword.clear();
        if (currentConfirmPassword != null) currentConfirmPassword.clear();
        if (currentEmail != null) currentEmail.clear();
        if (currentRole != null) currentRole = null;

    }

    private void showErrorFieldUI(TextField field){
        field.setStyle("-fx-border-color: #b20000");
        field.textProperty().addListener((observable) -> {
            field.setStyle("-fx-border-color: #FFFFFF");
        });
    }

    private void highLightEmptyFields(){
        String email = (currentEmail != null) ? currentEmail.getText() : null;
        if(currentUsername.getText().isBlank()){
            showErrorFieldUI(currentUsername);
        }
        if(currentPassword.getText().isBlank()){
            showErrorFieldUI(currentPassword);
        }
        if(currentConfirmPassword.getText().isBlank()){
            showErrorFieldUI(currentConfirmPassword);
        }
        if(email != null && email.isBlank()){
            showErrorFieldUI(currentEmail);
        }

    }

    private void addPasswordListener(TextField passwordField){
        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            boolean hasCap = newValue.matches(".*[A-Z].*");
            boolean hasLow  = newValue.matches(".*[a-z].*");
            boolean hasNum = newValue.matches(".*\\d.*");
            boolean hasLength = newValue.length() > 7;
            addPasswordValidationUI(hasCap,hasLow,hasNum,hasLength);
        });
    }

    private void addPasswordValidationUI(boolean hasCap, boolean hasLow , boolean hasNum, boolean hasLength){

        if(hasLength && hasCap && hasLow && hasNum){
            currentPassword.setStyle("-fx-border-color: #3dcc00;");
        } else {
            currentPassword.setStyle("-fx-border-color: #FFFFFF;");
        }

        currentPassLabels.get(0).setStyle("-fx-text-fill:" + (hasNum ? "#3dcc00;" : "#FFFFFF"));
        currentPassLabels.get(1).setStyle("-fx-text-fill:" + (hasLength ? "#3dcc00;" : "#FFFFFF"));
        currentPassLabels.get(2).setStyle("-fx-text-fill:" + (hasCap ? "#3dcc00;" : "#FFFFFF"));
        currentPassLabels.get(3).setStyle("-fx-text-fill:" + (hasLow ? "#3dcc00;" : "#FFFFFF"));
    }

    private void handleUserException(UserException exception){
        switch (exception.getMessage()){
            case "Invalid username" -> {
                if(currentUsername.getText().isBlank()){
                    showAlert(exception.getMessage(), "Username cannot be null", "please fill the username", Alert.AlertType.ERROR);
                }
                showErrorFieldUI(currentUsername);
            }
            case "Username already exists" -> {
                showAlert(exception.getMessage(),"Username ", "Please choose another username, if it's you, sign in",Alert.AlertType.ERROR);
                showErrorFieldUI(currentUsername);
            }

            case "Invalid password" -> {
                showAlert(exception.getMessage(),"Insecure password", "Required: \n*at least one capital letter" +
                                "\n*at least one lower letter" +
                                "\n*at least one number"+
                                "\n*at least 8 characters"
                        ,Alert.AlertType.ERROR);
                showErrorFieldUI(currentPassword);
            }

            case "Invalid email" -> {
                showErrorFieldUI(currentEmail);
                showAlert("Invalid Email","Fill in the email field correctly","Example: maria@example.com", Alert.AlertType.ERROR);
            }
            default -> throw new IllegalStateException("Unexpected value: " + exception.getMessage());
        }

    }


    private boolean fieldsAreNotBlank(String... fields) {
        return !Arrays.stream(fields)
                .filter(Objects::nonNull)
                .allMatch(field -> field.isBlank());
    }

    private User createUser(TextField username, TextField password, TextField email, Role role){
        String usrname = username.getText();
        String pass = password.getText();
        String em = (email != null) ? email.getText() : null;

        if (role != null) {
            return userService.createUserByRole(role, usrname, pass, em);
        } else {
            throw new IllegalArgumentException("ERROR: User role is null" + role.name());
        }

    }

    private void registerUserByRole(User user){
        System.out.println(user.getId());
        Role role = user.getRole();
        Role admin = Role.ADMIN;
        Role student = Role.STUDENT;
        Role teacher = Role.TEACHER;

        if(role == student){
            registerStudent(user);
        }

        if(role == teacher){
            registerTeacher(user);
        }

    }

    private void registerStudent(User user){
        Student student = Student.builder()
                .status(Status.APPROVAL.name())
                .dataInsert(LocalDateTime.now())
                .user(user)
                .build();
        studentService.registerStudent(student);

    }

    private void registerTeacher(User user){
        Teacher teacher = Teacher.builder()
                .status(Status.APPROVAL.name())
                .dateInsert(Timestamp.from(Instant.now()))
                .user(user)
                .build();
        teacherService.registerTeacher(teacher);

    }

    }
