package br.com.idealizeall.universitymanagement.controller;

import br.com.idealizeall.universitymanagement.model.UserRoles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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
    private TextField adminPassword;

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
    private ComboBox<?> loginRole;

    @FXML
    private TextField loginUsername;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoles();

    }

    public void loadRoles(){
        List<UserRoles> rolesList = new ArrayList<UserRoles>(Arrays.asList(UserRoles.values()));
        ObservableList dataList = FXCollections.observableArrayList(rolesList);
        loginRole.setItems(dataList);
    }


    public void handleBtnClick(ActionEvent actionEvent) {
        System.out.println("Hello world !");
    }
}
