package kitchenpos.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_table_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_orders_order_table"))
    private OrderTable orderTable;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderedTime = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public Order() {
    }

    public Order(OrderTable orderTable, OrderStatus orderStatus, List<OrderLineItem> orderLineItems) {
        this.orderTable = orderTable;
        this.orderStatus = orderStatus;
        this.orderLineItems.addAll(toOrderLineItems(orderLineItems));
    }

    private List<OrderLineItem> toOrderLineItems(List<OrderLineItem> orderLineItems) {
        return orderLineItems.stream()
                             .map(orderLineItem -> new OrderLineItem(this, orderLineItem.getMenu(), orderLineItem.getQuantity()))
                             .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderTable getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(OrderTable orderTable) {
        this.orderTable = orderTable;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(orderTable, order.orderTable) && orderStatus == order.orderStatus && Objects.equals(orderedTime, order.orderedTime) && Objects.equals(orderLineItems, order.orderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderTable, orderStatus, orderedTime, orderLineItems);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderTable=" + orderTable.getId() +
                ", orderStatus=" + orderStatus +
                ", orderedTime=" + orderedTime +
                ", orderLineItems=" + orderLineItems +
                '}';
    }
}
