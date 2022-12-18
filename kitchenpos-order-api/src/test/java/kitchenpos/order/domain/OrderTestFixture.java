package kitchenpos.order.domain;

import java.util.List;
import kitchenpos.common.constant.ErrorCode;
import kitchenpos.order.dto.OrderLineItemRequest;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.ordertable.domain.OrderTable;

public class OrderTestFixture {

    public static Order generateOrder(Long id, Long orderTableId, List<OrderLineItem> orderLineItems) {
        return new Order(id, orderTableId, OrderLineItems.from(orderLineItems));
    }

    public static Order generateOrder(Long orderTableId, OrderLineItems orderLineItems) {
        return new Order(null, orderTableId, orderLineItems);
    }

    public static Order generateOrder(Long orderTableId, List<OrderLineItem> orderLineItems) {
        return Order.of(orderTableId, OrderLineItems.from(orderLineItems));
    }

    public static OrderRequest generateOrderRequest(Long orderTableId, OrderStatus orderStatus, List<OrderLineItemRequest> orderLineItems) {
        return new OrderRequest(orderTableId, orderStatus, orderLineItems);
    }

    private static void validateOrderTable(OrderTable orderTable) {
        if(orderTable.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.주문_테이블은_비어있으면_안됨.getErrorMessage());
        }
    }
}
