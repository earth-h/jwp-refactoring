package kitchenpos.order.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import kitchenpos.common.constant.ErrorCode;
import org.springframework.util.CollectionUtils;

@Embeddable
public class OrderLineItems {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    protected OrderLineItems() {}

    private OrderLineItems(List<OrderLineItem> orderLineItems) {
        validateOrderLineItemsIsEmpty(orderLineItems);
        this.orderLineItems = new ArrayList<>(orderLineItems);
    }

    public static OrderLineItems from(List<OrderLineItem> orderLineItems) {
        return new OrderLineItems(orderLineItems);
    }

    private void validateOrderLineItemsIsEmpty(List<OrderLineItem> orderLineItems) {
        if(CollectionUtils.isEmpty(orderLineItems)) {
            throw new IllegalArgumentException(ErrorCode.주문_항목은_비어있을_수_없음.getErrorMessage());
        }
    }

    public void setUpOrder(final Order order) {
        orderLineItems.forEach(orderLineItem -> orderLineItem.setUpOrder(order));
    }

    public List<OrderLineItem> unmodifiableOrderLineItems() {
        return Collections.unmodifiableList(orderLineItems);
    }
}
