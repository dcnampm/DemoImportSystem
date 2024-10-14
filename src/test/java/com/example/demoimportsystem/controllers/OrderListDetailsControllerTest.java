package com.example.demoimportsystem.controllers;

import com.example.demoimportsystem.daos.MerchandiseDAO;
import com.example.demoimportsystem.daos.OrderDAO;
import com.example.demoimportsystem.models.Order;
import com.example.demoimportsystem.models.OrderList;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderListDetailsControllerTest {

    @Mock
    private MerchandiseDAO merchandiseDAO;
    @Mock
    private OrderDAO orderDAO;
    @InjectMocks
    private OrderListDetailsController controller;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize JavaFX toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            latch.countDown();
        });
        latch.await();

        MockitoAnnotations.openMocks(this);

//        // Inject DAO mocks into the controller using reflection
        Field merchandiseDAOField = controller.getClass().getDeclaredField("merchandiseDAO");
        merchandiseDAOField.setAccessible(true);
        merchandiseDAOField.set(controller, merchandiseDAO);

        Field orderDAOField = controller.getClass().getDeclaredField("orderDAO");
        orderDAOField.setAccessible(true);
        orderDAOField.set(controller, orderDAO);

        // Sử dụng Reflection để khởi tạo titleOrder
        Field titleOrderField = controller.getClass().getDeclaredField("titleOrder");
        titleOrderField.setAccessible(true);
        titleOrderField.set(controller, new TitledPane());

        // Sử dụng Reflection để khởi tạo messageLabel
        Field messageLabelField = controller.getClass().getDeclaredField("messageLabel");
        messageLabelField.setAccessible(true);
        messageLabelField.set(controller, new Label());
    }

    // Hộp đen: Kiểm tra nếu orderList là null thì hiển thị cảnh báo
    @Test
    public void testInitializeWithNullOrderList() throws Exception {
        controller.setOrderList(null);
        controller.initialize();

        Field orderListField = controller.getClass().getDeclaredField("orderList");
        orderListField.setAccessible(true);
        // Lấy giá trị của trường orderList
        OrderList orderListValue = (OrderList) orderListField.get(controller);

        assertNull(orderListValue);
    }

//    // Hộp trắng: Kiểm thử tất cả các trạng thái trong handleOrderListStatus()
//    @Test
//    public void testHandleOrderListStatus_Sent() throws Exception {
//        // Set orderList with status SENT
//        OrderList orderList = new OrderList();
//        orderList.setStatus(OrderList.Status.SENT);
//        injectPrivateField("orderList", orderList);
//
//        controller.handleOrderListStatus();
//
//        assertFalse(controller.titleOrder.isVisible());
//        assertTrue(controller.messageLabel.isVisible());
//        assertEquals("Đơn hàng chưa được tạo.", controller.messageLabel.getText());
//    }
//
//    @Test
//    public void testHandleOrderListStatus_Canceled() throws Exception {
//        // Set orderList with status CANCELED
//        OrderList orderList = new OrderList();
//        orderList.setStatus(OrderList.Status.CANCELED);
//        injectPrivateField("orderList", orderList);
//
//        controller.handleOrderListStatus();
//
//        assertFalse(controller.titleOrder.isVisible());
//        assertTrue(controller.messageLabel.isVisible());
//        assertEquals("Danh sách mặt hàng cần đặt đã bị hủy.", controller.messageLabel.getText());
//    }
//
//    @Test
//    public void testHandleOrderListStatus_Default() throws Exception {
//        // Set orderList with status DEFAULT (other than SENT and CANCELED)
//        OrderList orderList = new OrderList();
//        orderList.setStatus(OrderStatus.CREATED);  // Assuming CREATED is a valid status
//        injectPrivateField("orderList", orderList);
//
//        controller.handleOrderListStatus();
//
//        assertTrue(controller.titleOrder.isVisible());
//        assertFalse(controller.messageLabel.isVisible());
//    }
//
//    // Hộp trắng: Kiểm thử việc load dữ liệu từ DB
//    @Test
//    public void testLoadMerchandiseDetails() throws Exception {
//        // Set orderListId
//        injectPrivateField("orderListId", 1L);
//
//        // Mock the data from merchandiseDAO
//        List<OrderListDetails> mockMerchandiseList = Arrays.asList(
//                new OrderListDetails("M001", "Product A", 10, "pcs", "2024-10-15"),
//                new OrderListDetails("M002", "Product B", 5, "kg", "2024-10-16")
//        );
//        Mockito.when(merchandiseDAO.getMerchandiseDetailsForOrderList(1L)).thenReturn(mockMerchandiseList);
//
//        controller.loadMerchandiseDetails();
//
//        assertNotNull(controller.tableMerchandise.getItems());
//        assertEquals(2, controller.tableMerchandise.getItems().size());
//    }
//
//    @Test
//    public void testLoadOrderDetails() throws Exception {
//        // Set orderListId
//        injectPrivateField("orderListId", 1L);
//
//        // Mock the data from orderDAO
//        List<Order> mockOrderList = Arrays.asList(
//                new Order("O001", "Details A", "Site A", "2024-10-20", OrderStatus.CREATED),
//                new Order("O002", "Details B", "Site B", "2024-10-21", OrderStatus.SENT)
//        );
//        Mockito.when(orderDAO.getOrdersForOrderList(1L)).thenReturn(mockOrderList);
//
//        controller.loadOrderDetails();
//
//        assertNotNull(controller.tableOrders.getItems());
//        assertEquals(2, controller.tableOrders.getItems().size());
//    }
//
//    // Helper method to inject private fields using reflection
//    private void injectPrivateField(String fieldName, Object value) throws Exception {
//        Field field = controller.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(controller, value);
//    }
}
