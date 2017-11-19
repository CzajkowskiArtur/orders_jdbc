package dao;

import org.junit.Test;
import software.academy.orders.Order;
import software.academy.orders.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

public class OrderDaoImplTest {
    @Test
    public void shouldInsertOrder() throws Exception {
        OrderDao orderDao = new OrderDaoImpl();
        Date currentTime= new Date();
        Order order = new Order(5, currentTime, null, OrderStatus.NEW, new BigDecimal("2.20"));

        order.addItem(1,2);
        order.addItem(2,3);




        orderDao.insert(order);
    }

}