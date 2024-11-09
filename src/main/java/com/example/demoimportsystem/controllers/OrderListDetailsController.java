package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.daos.MerchandiseDAO;
import com.example.demoimportsystem.daos.OrderDAO;
import com.example.demoimportsystem.daos.impl.MerchandiseDAOImpl;
import com.example.demoimportsystem.daos.impl.OrderDAOImpl;
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
import lombok.Setter;

import java.util.List;

public class OrderListDetailsController {

    @FXML
    TableView<OrderListDetails> tableMerchandise;

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
    TableView<Order> tableOrders;

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
    TitledPane titleOrder;

    @FXML
    Label messageLabel;

    private final MerchandiseDAO merchandiseDAO;
    private final OrderDAO orderDAO;

    @Setter
    private OrderList orderList;
    private long orderListId;

    public OrderListDetailsController() {
        this.merchandiseDAO = new MerchandiseDAOImpl();
        this.orderDAO = new OrderDAOImpl();
    }
    public OrderListDetailsController(MerchandiseDAO merchandiseDAO, OrderDAO orderDAO) {
        this.merchandiseDAO = merchandiseDAO;
        this.orderDAO = orderDAO;
    }

    // Observable lists for table data
    private ObservableList<OrderListDetails> merchandiseDetails;
    private ObservableList<Order> orderDetails;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
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
            //load ds mặt hàng
            loadMerchandiseDetails();

            handleOrderListStatus();
        });
    }

    void handleOrderListStatus() {
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
                messageLabel.setVisible(false);

                // Thêm cờ để chỉ nạp dữ liệu một lần khi mở rộng titleOrder
                final boolean[] isDataLoaded = {false};

                titleOrder.expandedProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue && !isDataLoaded[0]) {
                        loadOrderDetails();
                        isDataLoaded[0] = true;
                    }
                });
                break;
        }
    }

    void loadMerchandiseDetails() {
        List<OrderListDetails> merchandiseListFromDB = merchandiseDAO.getMerchandiseDetailsForOrderList(orderListId);
        merchandiseDetails = FXCollections.observableArrayList(merchandiseListFromDB);
        tableMerchandise.setItems(merchandiseDetails);
    }

    void loadOrderDetails() {
        List<Order> orderListFromDB = orderDAO.getOrdersForOrderList(orderListId);
        orderDetails = FXCollections.observableArrayList(orderListFromDB);
        tableOrders.setItems(orderDetails);
    }
}

