package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.daos.MerchandiseDAO;
import com.example.demoimportsystem.daos.OrderDAO;
import com.example.demoimportsystem.daos.OrderListDAO;
import com.example.demoimportsystem.models.Order;
import com.example.demoimportsystem.models.OrderList;
import com.example.demoimportsystem.models.OrderListDetails;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.List;

import static javafx.scene.paint.Color.RED;

@AllArgsConstructor
public class OrderListDetailsController {

    @FXML
    private TableView<OrderListDetails> tableMerchandise;

    @FXML
    private TableColumn<OrderListDetails, String> columnMerchandiseCode;

    @FXML
    private TableColumn<OrderListDetails, String> columnMerchandiseName;

    @FXML
    private TableColumn<OrderListDetails, Integer> columnQuantity;

    @FXML
    private TableColumn<OrderListDetails, String> columnUnit;

    @FXML
    private TableColumn<OrderListDetails, String> columnDate;

    @FXML
    private TableView<Order> tableOrders;

    @FXML
    private TableColumn<Order, String> columnOrderCode;

    @FXML
    private TableColumn<Order, String> columnDetails;

    @FXML
    private TableColumn<Order, String> columnSite;

    @FXML
    private TableColumn<Order, String> columnDeliveryDate;

    @FXML
    private TableColumn<Order, String> columnStatus;

    @FXML
    private TitledPane titleOrder;

    @FXML
    private Label messageLabel;

    private MerchandiseDAO merchandiseDAO;
    private OrderDAO orderDAO;
    @Setter
    private OrderList orderList;
    private long orderListId;

    public OrderListDetailsController() {
        orderDAO = new OrderDAO();
        merchandiseDAO = new MerchandiseDAO();
    }

    // Observable lists for table data
    private ObservableList<OrderListDetails> merchandiseDetails;
    private ObservableList<Order> orderDetails;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            if (orderList != null) {
                // Configure tableMerchandise columns
                columnMerchandiseCode.setCellValueFactory(new PropertyValueFactory<>("merchandiseCode"));
                columnMerchandiseName.setCellValueFactory(new PropertyValueFactory<>("merchandiseName"));
                columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                columnUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
                columnDate.setCellValueFactory(new PropertyValueFactory<>("desiredDate"));

                // Configure tableOrders columns
                columnOrderCode.setCellValueFactory(new PropertyValueFactory<>("orderCode"));
                columnSite.setCellValueFactory(new PropertyValueFactory<>("site"));
                columnDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
                columnStatus.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getStatus().getVietnamese()));
                columnDetails.setCellValueFactory(new PropertyValueFactory<>("orderDetails"));

                orderListId = orderList.getId();
                loadMerchandiseDetails();
                handleOrderListStatus();
            } else {
                // Tạo một Alert dialog để báo lỗi
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi tải dữ liệu");
                alert.setHeaderText("Không thể tải thông tin chi tiết dsmhcđ");
                alert.setContentText("Lỗi khi lấy dữ liệu. Vui lòng thử lại sau");

                // Hiển thị popup và chờ người dùng đóng nó
                alert.showAndWait();
            }
        });
    }

    private void handleOrderListStatus() {
        switch (orderList.getStatus()) {
            case SENT:
                titleOrder.setVisible(false);
                messageLabel.setVisible(true);
                messageLabel.setText("Đơn hàng chưa được tạo.");
                break;
            case CANCELED:
                titleOrder.setVisible(false);
                messageLabel.setVisible(true);
                messageLabel.setText("Danh sách mặt hàng cần đặt đã bị hủy.");
                break;
            default:
                titleOrder.setVisible(true);
                loadOrderDetails();
                break;
        }
    }

    private void loadMerchandiseDetails() {
        List<OrderListDetails> merchandiseListFromDB = merchandiseDAO.getMerchandiseDetailsForOrderList(orderListId);
        merchandiseDetails = FXCollections.observableArrayList(merchandiseListFromDB);
        tableMerchandise.setItems(merchandiseDetails);
    }

    private void loadOrderDetails() {
        List<Order> orderListFromDB = orderDAO.getOrdersForOrderList(orderListId);
        orderDetails = FXCollections.observableArrayList(orderListFromDB);
        tableOrders.setItems(orderDetails);
    }
}

