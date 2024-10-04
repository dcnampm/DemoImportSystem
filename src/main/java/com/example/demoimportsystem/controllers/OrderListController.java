package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.DemoApplication;
import com.example.demoimportsystem.daos.OrderListDAO;
import com.example.demoimportsystem.models.OrderList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.crypto.spec.PSource;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderListController {
    @FXML
    private Button homeButton;

    @FXML
    private TableView<OrderList> orderListTable;

    @FXML
    private TableColumn<OrderList, Long> columnOrdinalNumber;

    @FXML
    private TableColumn<OrderList, String> columnListCode;

    @FXML
    private TableColumn<OrderList, String> columnCreatedBy;

    @FXML
    private TableColumn<OrderList, String> columnCreatedDate;

    @FXML
    private TableColumn<OrderList, String> columnStatus;

    @FXML
    private TableColumn<OrderList, String> columnAction;

    @FXML
    private TextField textFieldListCode;

    @FXML
    private TextField textFieldCreatedBy;

    @FXML
    private ComboBox<String> comboBoxStatus;

    @FXML
    private Button buttonSearch;

    // Dữ liệu hiển thị trong bảng (ObservableList cho TableView)
    private ObservableList<OrderList> orderLists;

    // DAO để tương tác với cơ sở dữ liệu
    private OrderListDAO orderListDAO = new OrderListDAO();

    private Long selectedOrderListId;

    @FXML
    public void initialize() {
        // Cấu hình các cột trong bảng (map dữ liệu vào cột)
        columnOrdinalNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnListCode.setCellValueFactory(new PropertyValueFactory<>("listCode"));

        // Cột này hiển thị username từ đối tượng User
        columnCreatedBy.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedBy().getUsername()));

        // Format ngày tháng hiển thị trong bảng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        columnCreatedDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedDate().format(formatter)));

        // Hiển thị trạng thái đơn hàng
        columnStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().getVietnamese()));

        // Thêm nút vào cột "Tác vụ"
        columnAction.setCellFactory(param -> {
            TableCell<OrderList, String> cell = new TableCell<>() {
                private final Button editButton = new Button("✏️"); // Nút sửa
                private final Button viewButton = new Button("👁️"); // Nút xem
                final HBox hbox = new HBox(editButton, viewButton);

                {
                    viewButton.setOnAction(event -> {
                        OrderList orderList = getTableView().getItems().get(getIndex());
                        selectedOrderListId = orderList.getId();
//                        showOrderListDetail(selectedOrderListId);
                        showOrderListDetail(orderList);
                    });

                    editButton.setOnAction(event -> {
                        // Code xử lý khi nhấn "Sửa" -> bỏ qua
                    });

                    hbox.setSpacing(5);  // Khoảng cách giữa 2 nút
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(hbox);
                    }
                }
            };
            return cell;
        });

        // Tải dữ liệu từ cơ sở dữ liệu và hiển thị trong bảng
        loadOrderLists();

        buttonSearch.setOnAction(event -> filterOrderList());
    }

    // Phương thức tải dữ liệu từ cơ sở dữ liệu và hiển thị trong TableView
    private void loadOrderLists() {
        List<OrderList> orderListFromDB = orderListDAO.getAllOrderLists();
        orderLists = FXCollections.observableArrayList(orderListFromDB);
        orderListTable.setItems(orderLists);
    }

//    // Phương thức này có thể dùng để làm mới lại dữ liệu trong bảng (nếu cần thiết)
//    public void refreshOrderListTable() {
//        loadOrderLists();  // Tải lại dữ liệu
//    }

    //filter
    private void filterOrderList() {
        String listCodeFilter = textFieldListCode.getText().toLowerCase();
        String createdByFilter = textFieldCreatedBy.getText().toLowerCase();
        String statusFilter = comboBoxStatus.getValue();

        //tạo list chứa các order đc lọc
        ObservableList<OrderList> filteredList = FXCollections.observableArrayList();

        for (OrderList order : orderLists) {
            //ktra code trống || giá trị nhập vào có trong listCodeFilter => matches = true
            boolean matches = listCodeFilter.isEmpty() || order.getListCode().toLowerCase().contains(listCodeFilter);

            if (!createdByFilter.isEmpty() && !order.getCreatedBy().getUsername().toLowerCase().contains(createdByFilter)) {
                matches = false;
            }

            if (statusFilter != null && !statusFilter.equals("Trạng thái") &&
                    !order.getStatus().getVietnamese().equals(statusFilter)) {
                matches = false;
            }

            //matches = true -> add order vào listFilter
            if (matches) {
                filteredList.add(order);
            }
        }

        orderListTable.setItems(filteredList);
    }

    // Phương thức xử lý sự kiện khi bấm vào"Trang chủ"
    public void handleHomepage(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApplication.class.getResource("home.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 600);

        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.setScene(scene);
    }

    //hiển thị chi tiết ds khi nhấn nút "👁️"
    public void showOrderListDetail(OrderList orderList) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(DemoApplication.class.getResource("order-list-details.fxml"));
            Parent root = fxmlLoader.load();

            // Lấy controller của trang chi tiết và truyền đối tượng OrderList
            OrderListDetailsController detailsController = fxmlLoader.getController();
//            detailsController.setOrderListId(orderListId);
            detailsController.setOrderList(orderList);
            detailsController.initialize();

            // Hiển thị trang chi tiết
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
