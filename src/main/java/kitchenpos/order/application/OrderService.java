package kitchenpos.order.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItem;
import kitchenpos.order.domain.OrderLineItems;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.dto.OrderLineItemRequest;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.order.dto.OrderResponse;
import kitchenpos.order.dto.OrderStatusChangeRequest;
import kitchenpos.order.exception.OrderException;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.TableRepository;

@Service
public class OrderService {
	private final MenuRepository menuRepository;
	private final OrderRepository orderRepository;
	private final TableRepository tableRepository;

	public OrderService(
		final MenuRepository menuRepository,
		final OrderRepository orderRepository,
		final TableRepository tableRepository
	) {
		this.menuRepository = menuRepository;
		this.orderRepository = orderRepository;
		this.tableRepository = tableRepository;
	}

	@Transactional
	public OrderResponse create(final OrderRequest orderRequest) {
		final OrderTable orderTable = tableRepository.findById(orderRequest.getOrderTableId())
			.orElseThrow(() -> new OrderException("주문 테이블이 존재하지 않아 주문 할 수 없습니다."));

		List<OrderLineItem> orderLineItems = findOrderLineItems(orderRequest.getOrderLineItemRequests());
		Order order = orderRequest.toOrder(orderTable, new OrderLineItems(orderLineItems));

		final Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	public List<OrderResponse> list() {
		final List<Order> orders = orderRepository.findAll();
		return OrderResponse.of(orders);
	}

	@Transactional
	public OrderResponse changeOrderStatus(final Long orderId,
		final OrderStatusChangeRequest orderStatusChangeRequest) {
		final Order savedOrder = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);

		savedOrder.changeOrderStatus(OrderStatus.valueOf(orderStatusChangeRequest.getOrderStatus()));

		return OrderResponse.of(savedOrder);
	}

	private List<OrderLineItem> findOrderLineItems(List<OrderLineItemRequest> orderLineItemRequests) {
		return orderLineItemRequests.stream()
			.map(orderLineItemRequest -> {
				Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
					.orElseThrow(() -> new OrderException("메뉴가 존재하지 않아 주문 생성할 수 없습니다."));
				return orderLineItemRequest.toOrderLineItem(menu);
			}).collect(Collectors.toList());
	}
}
