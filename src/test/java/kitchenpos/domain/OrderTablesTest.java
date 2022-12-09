package kitchenpos.domain;

import static kitchenpos.domain.OrderTableTestFixture.generateOrderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.Collections;
import kitchenpos.common.constant.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("주문 테이블 집합 관련 도메인 테스트")
public class OrderTablesTest {

    private OrderTable 주문테이블A;
    private OrderTable 주문테이블B;

    @BeforeEach
    void setUp() {
        주문테이블A = generateOrderTable(1L, 1L, 5, true);
        주문테이블B = generateOrderTable(2L, 1L, 4, true);
    }

    @DisplayName("주문 테이블 집합을 생성한다.")
    @Test
    void createOrderTables() {
        // when
        OrderTables orderTables = OrderTables.from(Arrays.asList(주문테이블A, 주문테이블B));

        // then
        assertAll(
                () -> assertThat(orderTables.unmodifiableOrderTables()).hasSize(2),
                () -> assertThat(orderTables.unmodifiableOrderTables()).containsExactly(주문테이블A, 주문테이블B)
        );
    }

    @DisplayName("주문 테이블 집합 내 주문 테이블들의 그룹 상태를 해제하면, 각 주문 테이블의 그룹은 null이 된다.")
    @Test
    void ungroupOrderTables() {
        // given
        OrderTables orderTables = OrderTables.from(Arrays.asList(주문테이블A, 주문테이블B));

        // when
        orderTables.ungroupOrderTables();

        // then
        assertThat(orderTables.unmodifiableOrderTables().stream().map(OrderTable::getTableGroupId))
                .containsExactly(null, null);
    }

    @DisplayName("주문 테이블 집합 내 주문 테이블이 2개 미만이면 주문 테이블 집합 생성 시 에러가 발생한다.")
    @Test
    void createOrderTablesThrowErrorWhenOrderTableCountIsSmallerThanTwo() {
        // when & then
        assertThatThrownBy(() -> OrderTables.from(Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorCode.주문_테이블은_2개_이상여야함.getErrorMessage());
    }
}
