package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.daos.MerchandiseDAO;
import com.example.demoimportsystem.daos.OrderDAO;
import com.example.demoimportsystem.models.Order;
import com.example.demoimportsystem.models.OrderList;
import com.example.demoimportsystem.models.OrderListDetails;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.example.demoimportsystem.models.Order.OrderStatus.CREATED;
import static com.example.demoimportsystem.models.Order.OrderStatus.DELIVERING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderListDetailsControllerTest {
    @Mock
    private MerchandiseDAO merchandiseDAOMock;
    @Mock
    private OrderDAO orderDAOMock;
    @Mock
    private OrderList orderListMock;
    @InjectMocks
    private OrderListDetailsController controller;

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        // Initialize JavaFX toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await();
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new OrderListDetailsController(merchandiseDAOMock, orderDAOMock);
        controller.setOrderList(orderListMock);
        controller.titleOrder = new TitledPane();
        controller.messageLabel = new Label();
        controller.tableMerchandise = new TableView<>();
        controller.tableOrders = new TableView<>();
    }

    @Test
    public void testHandleOrderListStatus_Sent() {
        // Giả lập trạng thái SENT cho orderList
        when(orderListMock.getStatus()).thenReturn(OrderList.Status.SENT);

        controller.handleOrderListStatus();

        assertFalse(controller.titleOrder.isVisible());
        assertTrue(controller.messageLabel.isVisible());
        assertEquals("Đơn hàng chưa được tạo.", controller.messageLabel.getText());
    }

    @Test
    public void testHandleOrderListStatus_Canceled() throws Exception {
        // Giả lập trạng thái SENT cho orderList
        when(orderListMock.getStatus()).thenReturn(OrderList.Status.CANCELED);

        controller.handleOrderListStatus();

        assertFalse(controller.titleOrder.isVisible());
        assertTrue(controller.messageLabel.isVisible());
        assertEquals("Danh sách mặt hàng cần đặt đã bị hủy.", controller.messageLabel.getText());
    }

    @Test
    public void testHandleOrderListStatus_Default() {
        controller = Mockito.spy(new OrderListDetailsController(merchandiseDAOMock, orderDAOMock));

        controller.setOrderList(orderListMock);
        controller.titleOrder = new TitledPane();
        controller.messageLabel = new Label();
        controller.tableOrders = new TableView<>();


        // Giả lập trạng thái PROCESSING/COMPLETED cho orderList
        when(orderListMock.getStatus()).thenReturn(OrderList.Status.PROCESSING);

        controller.handleOrderListStatus();

        assertTrue(controller.titleOrder.isVisible());
        assertFalse(controller.messageLabel.isVisible());

        // Thiết lập một listener để phát hiện khi titleOrder được mở rộng
        BooleanProperty expandedProperty = new SimpleBooleanProperty(false);
        controller.titleOrder.expandedProperty().bind(expandedProperty);

        expandedProperty.set(true);

        verify(controller, times(1)).loadOrderDetails();
    }

//    // Hộp trắng: Kiểm thử việc load dữ liệu từ DB
    @Test
    public void testLoadMerchandiseDetails() {
        // Giả lập dữ liệu từ merchandiseDAO
        List<OrderListDetails> mockMerchandiseList = Arrays.asList(
                new OrderListDetails(1L, 1L, 1L, 10, LocalDate.parse("2024-10-15")),
                new OrderListDetails(2L, 1L,2L,5, LocalDate.parse("2024-10-15"))
        );

        when(merchandiseDAOMock.getMerchandiseDetailsForOrderList(anyLong())).thenReturn(mockMerchandiseList);


        // Gọi phương thức loadMerchandiseDetails
        controller.loadMerchandiseDetails();

        // Kiểm tra rằng dữ liệu đã được nạp vào TableView
        ObservableList<OrderListDetails> merchandiseDetails = controller.tableMerchandise.getItems();
        assertEquals(2, merchandiseDetails.size());
        assertEquals(1L, merchandiseDetails.get(0).getMerchandiseId());
        assertEquals(2L, merchandiseDetails.get(1).getMerchandiseId());
    }

    @Test
    public void testLoadOrderDetails() {
        List<Order> mockOrders = Arrays.asList(
                new Order(1L, 1L, "1a", "Site A", LocalDate.parse("2024-10-02"), CREATED, "5x sp1"),
                new Order(2L, 1L, "1b", "Site B", LocalDate.parse("2024-10-02"), DELIVERING, "10x sp2")
        );

        when(orderDAOMock.getOrdersForOrderList(anyLong())).thenReturn(mockOrders);

        // Gọi phương thức loadOrderDetails
        controller.loadOrderDetails();

        // Kiểm tra rằng dữ liệu đã được nạp vào TableView
        ObservableList<Order> orderDetails = controller.tableOrders.getItems();
        assertEquals(2, orderDetails.size());
        assertEquals("5x sp1", orderDetails.get(0).getOrderDetails());
        assertEquals("10x sp2", orderDetails.get(1).getOrderDetails());
    }
}
