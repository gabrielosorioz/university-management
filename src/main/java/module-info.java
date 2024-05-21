module br.com.idealizeall.universitymanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jdk.compiler;

    opens br.com.idealizeall.universitymanagement to javafx.fxml;
    exports br.com.idealizeall.universitymanagement;
    exports br.com.idealizeall.universitymanagement.controller;
    opens br.com.idealizeall.universitymanagement.controller to javafx.fxml;
}