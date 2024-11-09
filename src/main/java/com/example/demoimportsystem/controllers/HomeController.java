package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.DemoApplication;
import com.example.demoimportsystem.daos.OrderDAO;
import com.example.demoimportsystem.daos.OrderListDAO;
import com.example.demoimportsystem.daos.impl.OrderDAOImpl;
import com.example.demoimportsystem.daos.impl.OrderListDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button homeButton;
    @FXML
    private Label createdCountLabel;
    @FXML
    private Label deliveringCountLabel;
    @FXML
    private Label deliveredCountLabel;
    @FXML
    private Label orderCanceledCountLabel;

    @FXML
    private Label sentCountLabel;
    @FXML
    private Label canceledCountLabel;
    @FXML
    private Label processingCountLabel;
    @FXML
    private Label completedCountLabel;

    @FXML
    private Button orderListButton;

    private final OrderDAO orderDAO;
    private final OrderListDAO orderListDAO;

    public HomeController () {
        this.orderDAO = new OrderDAOImpl();
        this.orderListDAO = new OrderListDAOImpl();
    }

    public HomeController (OrderDAO orderDAO, OrderListDAO orderListDAO) {
        this.orderDAO = orderDAO;
        this.orderListDAO = orderListDAO;
    }

    @FXML
    public void initialize() {
        loadData();
    }
    public void loadData() {
        createdCountLabel.setText(String.valueOf(orderDAO.countOrdersByStatus("Đã tạo")));
        deliveringCountLabel.setText(String.valueOf(orderDAO.countOrdersByStatus("Đang vận chuyển")));
        deliveredCountLabel.setText(String.valueOf(orderDAO.countOrdersByStatus("Đã giao")));
        orderCanceledCountLabel.setText(String.valueOf(orderDAO.countOrdersByStatus("Đã hủy")));

        sentCountLabel.setText(String.valueOf(orderListDAO.countOrderListsByStatus("Đã gửi")));
        canceledCountLabel.setText(String.valueOf(orderListDAO.countOrderListsByStatus("Đã hủy")));
        processingCountLabel.setText(String.valueOf(orderListDAO.countOrderListsByStatus("Đang xử lý")));
        completedCountLabel.setText(String.valueOf(orderListDAO.countOrderListsByStatus("Đã hoàn thành")));
    }


    // Phương thức xử lý sự kiện khi bấm vào nút "📋 Bảng DSMH cần đặt"
    public void handleOrderList(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApplication.class.getResource("order-list.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 600);

        Stage stage = (Stage) orderListButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
