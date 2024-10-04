module com.example.demoimportsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires static lombok;
    requires org.jetbrains.annotations;
    requires java.desktop;

    opens com.example.demoimportsystem to javafx.fxml;
    exports com.example.demoimportsystem;
    exports com.example.demoimportsystem.models;
    opens com.example.demoimportsystem.models to javafx.fxml;
    exports com.example.demoimportsystem.utils;
    opens com.example.demoimportsystem.utils to javafx.fxml;
    opens com.example.demoimportsystem.controllers to javafx.fxml;
}