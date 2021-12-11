package kitchenpos.dto;

import java.util.Objects;

import kitchenpos.domain.order.OrderTable;
import kitchenpos.domain.table.TableGroup;

public class OrderTableDto {
    private Long id;
    private Long tableGroupId;
    private int numberOfGuests;
    private boolean empty;

    protected OrderTableDto() {
    }

    private OrderTableDto(Long id, Long tableGroupId, int numberOfGuests, boolean empty) {
        this.id = id;
        this.tableGroupId = tableGroupId; 
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public static OrderTableDto of(Long id, Long tableGroupId, int numberOfGuests, boolean empty) {
        return new OrderTableDto(id, tableGroupId, numberOfGuests, empty);
    }

    public static OrderTableDto of(Long id, TableGroup tableGroup, int numberOfGuests, boolean empty) {
        if (tableGroup == null ) {
            return new OrderTableDto(id, null, numberOfGuests, empty);    
        }

        return new OrderTableDto(id, tableGroup.getId(), numberOfGuests, empty);
    }

    public static OrderTableDto of(OrderTable orderTable) {
        if (orderTable.getTableGroup() == null) {
            return new OrderTableDto(orderTable.getId(), null, orderTable.getNumberOfGuests(), orderTable.getEmpty());    
        }

        return new OrderTableDto(orderTable.getId(), orderTable.getTableGroup().getId(), orderTable.getNumberOfGuests(), orderTable.getEmpty());
    }

    public Long getId() {
        return this.id;
    }

    public Long getTableGroupId() {
        return this.tableGroupId;
    }

    public int getNumberOfGuests() {
        return this.numberOfGuests;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean getEmpty() {
        return this.empty;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderTableDto)) {
            return false;
        }
        OrderTableDto orderTableDto = (OrderTableDto) o;
        return Objects.equals(id, orderTableDto.id) && Objects.equals(tableGroupId, orderTableDto.tableGroupId) && numberOfGuests == orderTableDto.numberOfGuests && empty == orderTableDto.empty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableGroupId, numberOfGuests, empty);
    }

}
