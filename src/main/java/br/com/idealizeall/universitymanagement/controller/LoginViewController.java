    package br.com.idealizeall.universitymanagement.controller;

    import br.com.idealizeall.universitymanagement.exception.UserException;
    import br.com.idealizeall.universitymanagement.model.*;
    import br.com.idealizeall.universitymanagement.service.StudentService;
    import br.com.idealizeall.universitymanagement.service.TeacherService;
    import br.com.idealizeall.universitymanagement.service.UserService;
    import com.dlsc.formsfx.model.structure.Form;
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
    import java.sql.Array;
    import java.sql.Timestamp;
    import java.text.Normalizer;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showLogin();
    }

    private void loadFormList(){
            formTypeList  = Arrays.asList(FormType.values());
            observableList = FXCollections.observableArrayList(formTypeList);
            loginRole.setPromptText("Choose role: ");
            loginRole.setItems(observableList);
        }

    public void switchForm(ActionEvent actionEvent){
            resetFields();
            FormType selectedForm = loginRole.getSelectionModel().getSelectedItem();
            if (selectedForm != null){
                switch(selectedForm){
                    case LOGIN -> {setForm(FormType.LOGIN);}
                    case ADMIN -> {setForm(FormType.ADMIN);}
                    case STUDENT -> {setForm(FormType.STUDENT);}
                    case TEACHER -> {setForm(FormType.TEACHER);}
                }
            }
        }

    private void loadCurrentFields(TextField email, TextField username, TextField password, TextField confirmPassword,Role role,FormType form){
        currentUsername = username;
        currentPassword = password;
        currentConfirmPassword = confirmPassword;
        currentEmail = email;
        currentPassLabels = getPassLabels(form);
        currentRole = role;


        eventFilterEnterKey(email,username,password,confirmPassword);
        addTabTraversal(email,username,password,confirmPassword);
        addPasswordListener(password);
    }

    private void addTabTraversal(TextField... textFields){
        List<TextField> nonNullTxtFields = Arrays.stream(textFields)
                .filter(Objects::nonNull)
                .toList();

        for(int i =0; i < nonNullTxtFields.size(); i++){
            int nextIndex = (i +1) % nonNullTxtFields.size();

            final int currentIndex = i;
            final int nextFieldIndex = nextIndex;

            nonNullTxtFields.get(currentIndex).addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(keyEvent.getCode() == KeyCode.TAB){
                    keyEvent.consume();

                    // focus on next field
                    nonNullTxtFields.get(nextIndex).requestFocus();
                }
            });

        }
    }

    private void eventFilterEnterKey(TextField... textFields){
        Arrays.stream(textFields)
                .filter(Objects::nonNull)
                .forEach(textField -> {
                            textField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
                                if(keyEvent.getCode().equals(KeyCode.ENTER)){
                                    signUp();
                                    keyEvent.consume();
                                }

                            });
                        }
                );
    }

    private void setFormVisibility(boolean login, boolean admin, boolean student, boolean teacher){
            loginForm.setVisible(login);
            adminForm.setVisible(admin);
            studentForm.setVisible(student);
            teacherForm.setVisible(teacher);
        }

    private void showForm(FormType form){

        if(form.equals(FormType.LOGIN)){
            setFormVisibility(true, false,false,false);
        }
        if(form.equals(FormType.STUDENT)){
            setFormVisibility(false, false,true,false);
            currentEmail.requestFocus();
        }
        if(form.equals(FormType.TEACHER)){
            setFormVisibility(false, false,false,true);
            currentEmail.requestFocus();
        }
        if(form.equals(FormType.ADMIN)){
            setFormVisibility(false, true,false,false);
            currentUsername.requestFocus();
        }
    }

    private void setForm(FormType form){

        if(form.equals(FormType.LOGIN)){
            loginRole.getSelectionModel().select(form);
        }
        if(form.equals(FormType.STUDENT)){
            loadCurrentFields(studentEmail,studentUsername,studentPassword,studentConfirmPassword,Role.STUDENT,form);
        }
        if(form.equals(FormType.TEACHER)){
            loadCurrentFields(teacherEmail,teacherUsername,teacherPassword,teacherConfirmPassword,Role.TEACHER,form);
        }
        if(form.equals(FormType.ADMIN)){
            loadCurrentFields(null,adminUsername,adminPassword,adminConfirmPassword,Role.ADMIN,form);
        }
        showForm(form);
    }

    private List<Label> getPassLabels(FormType form){
        if(form.equals(FormType.STUDENT)){
            return Arrays.asList(studentPassHasNum, studentPass8Char, studentPassHasCap, studentPassHasLow);
        }
        if(form.equals(FormType.TEACHER)){
            return Arrays.asList(teacherPassHasNum, teacherPass8Char, teacherPassHasCap, teacherPassHasLow);
        }
        if(form.equals(FormType.ADMIN)){
            return  Arrays.asList(admPassHasNum, admPass8Char, admPassHasCap, admPassHasLow);
        }
        throw new IllegalArgumentException("Invalid form type: " + form.name());
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }

    public void showLogin(){
        loadFormList();
        setForm(FormType.LOGIN);
    }

    public void signUp(){

        if(currentUsername == null || currentPassword == null || currentConfirmPassword == null){
            showAlert("Error", "Form not loaded", "Please select a valid form to sign up", Alert.AlertType.ERROR);
            return;
        }

        String usernameStr = currentUsername.getText();
        String passwordStr = currentPassword.getText();
        String confirmPasswordStr = currentConfirmPassword.getText();
        String emailStr = (currentEmail != null) ? currentEmail.getText() : null;

        boolean notBlank = fieldsAreNotBlank(usernameStr, passwordStr, confirmPasswordStr,emailStr);
        boolean equalPasswords = passwordStr.equals(confirmPasswordStr);

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
                showLogin();
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
