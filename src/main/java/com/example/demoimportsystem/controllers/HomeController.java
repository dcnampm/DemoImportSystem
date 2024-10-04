package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.DemoApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button homeButton;

    @FXML
    private Button orderListButton;

    // Phương thức xử lý sự kiện khi bấm vào nút "📋 Bảng DSMH cần đặt"
    public void handleOrderList(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApplication.class.getResource("order-list.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 600);

        Stage stage = (Stage) orderListButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
