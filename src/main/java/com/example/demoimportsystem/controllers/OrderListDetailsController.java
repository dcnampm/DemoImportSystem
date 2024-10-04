package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.daos.MerchandiseDAO;
import com.example.demoimportsystem.daos.OrderDAO;
import com.example.demoimportsystem.daos.OrderListDAO;
import com.example.demoimportsystem.models.Order;
import com.example.demoimportsystem.models.OrderList;
import com.example.demoimportsystem.models.OrderListDetails;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;

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
    private OrderList orderList;
    private long orderListId;

    public OrderListDetailsController() {
        orderDAO = new OrderDAO();
        merchandiseDAO = new MerchandiseDAO();
    }

//    // Thiết lập OrderList và tải dữ liệu liên quan
//    public void setOrderListId(Long orderListId) {
//        this.orderListId = orderListId;
//    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    // Observable lists for table data
    private ObservableList<OrderListDetails> merchandiseDetails;
    private ObservableList<Order> orderDetails;

    @FXML
    public void initialize() {
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

//        if (orderList != null) {
//            orderListId = orderList.getId();
//            // Load data from the database and populate the tables
//            if (orderList.getStatus() == OrderList.Status.SENT) { // Nếu trạng thái là "Đã gửi"
//                tableOrders.setVisible(false); // Ẩn TableView
//                labelNoOrders.setVisible(true); // Hiển thị thông báo "Đơn hàng chưa được tạo"
//                loadMerchandiseDetails();
//            } else if (orderList.getStatus() == OrderList.Status.CANCELED) {
//                tableOrders.setVisible(false); // Ẩn TableView
//                labelNoOrders.setVisible(true); // Hiển thị thông báo "Đơn hàng chưa được tạo"
//                labelNoOrders.setText("Danh sách mặt hàng cần đặt đã bị hủy");
//            } else {
//                labelNoOrders.setVisible(false); // Ẩn thông báo
//                tableOrders.setVisible(true); // Hiển thị TableView
//                loadMerchandiseDetails();
//                loadOrderDetails();
//            }
//        }

//        if (orderList == null) {
//            titleOrder.setVisible(false);
//        } else {
//            orderListId = orderList.getId();
//            // Kiểm tra trạng thái của orderList
//            switch (orderList.getStatus()) {
//                case SENT:
//                    loadMerchandiseDetails();
//                    titleOrder.setVisible(false);
//                    createMessageLabel("Đơn hàng chưa được tạo.");
//                    break;
//
//                case CANCELED:
//                    loadMerchandiseDetails();
//                    titleOrder.setVisible(false);
//                    createMessageLabel("Danh sách mặt hàng cần đặt đã bị hủy.");
//                    break;
//
//                default:
//                    loadMerchandiseDetails();
//                    tableOrders.setVisible(true);
//                    loadOrderDetails();
//                    break;
//            }
//        }

        if (orderList != null) {
            orderListId = orderList.getId();
            loadMerchandiseDetails();

            // Kiểm tra trạng thái của orderList
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
        } else {
            titleOrder.setVisible(false);
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

