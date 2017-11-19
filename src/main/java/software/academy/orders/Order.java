package software.academy.orders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {


    private Integer id;

    private Integer clientId;

    private Date insertDate;

    private Date updateDate;

    private OrderStatus status;

    public Order(Integer clientId, Date insertDate, Date updateDate, OrderStatus status, BigDecimal totalCost) {
        this.clientId = clientId;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
        this.status = status;
        this.totalCost = totalCost;
    }

    private BigDecimal totalCost;

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    private List<OrderItem> items = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void addItem(Integer productId, Integer quantity) {
        OrderItem orderItem = new OrderItem(this.id,productId,quantity);
        items.add(orderItem);
    }
}
