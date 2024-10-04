package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.DemoApplication;
import com.example.demoimportsystem.daos.UserDAO;
import com.example.demoimportsystem.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    private final UserDAO userDAO;

    public LoginController() {
        userDAO = new UserDAO();
    }

    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Vui lòng nhập tất cả thông tin");
            return;
        }

        User user = userDAO.authenticate(username, password);
        if (user != null) {
            messageLabel.setText("Đăng nhập thành công!"); // Thông báo thành công

            changeToHomepage();
        } else {
            messageLabel.setText("Tên đăng nhập hoặc mật khẩu không chính xác");
        }
    }

    //Chuyển giao diện tới trang chủ
    public void changeToHomepage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApplication.class.getResource("home.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 600);

        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
