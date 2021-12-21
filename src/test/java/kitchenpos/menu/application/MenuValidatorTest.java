package kitchenpos.menu.application;

import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menu_group.domain.MenuGroup;
import kitchenpos.menu_group.domain.MenuGroupRepository;
import kitchenpos.menu_group.fixture.MenuGroupFixture;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import kitchenpos.product.fixture.ProductFixture;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("메뉴 검증 테스트")
@ExtendWith(MockitoExtension.class)
class MenuValidatorTest {
    @Mock
    MenuGroupRepository menuGroupRepository;
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    MenuValidator menuValidator;

    private Product 강정치킨;
    private MenuGroup 추천메뉴;

    @BeforeEach
    void setup() {
        강정치킨 = ProductFixture.create(1L, "강정치킨", BigDecimal.valueOf(17_000));
        추천메뉴 = MenuGroupFixture.create(1L, "추천메뉴");
    }

    @DisplayName("메뉴 생성 검증")
    @Nested
    class TestValidateCreateMenu {
        @DisplayName("검증 확인")
        @Test
        void 검증_확인() {
            // given
            MenuProductRequest 메뉴_상품 = MenuProductRequest.of(강정치킨.getId(), 2L);
            MenuRequest 메뉴_등록_요청_데이터 = MenuRequest.of("더블강정", BigDecimal.valueOf(32_000), 추천메뉴.getId(), Collections.singletonList(메뉴_상품));

            given(menuGroupRepository.existsById(any())).willReturn(true);
            given(productRepository.findById(any(Long.TYPE))).willReturn(Optional.of(강정치킨));

            // when
            ThrowableAssert.ThrowingCallable 검증_요청 = () -> menuValidator.validateCreateMenu(메뉴_등록_요청_데이터);

            // then
            assertThatNoException().isThrownBy(검증_요청);
        }

        @DisplayName("그룹이 존재하지 않음")
        @Test
        void 그룹이_존재하지_않음() {
            // given
            MenuProductRequest 메뉴_상품 = MenuProductRequest.of(강정치킨.getId(), 2L);
            MenuRequest 메뉴_등록_요청_데이터 = MenuRequest.of("더블강정", BigDecimal.valueOf(32_000), null, Collections.singletonList(메뉴_상품));

            given(menuGroupRepository.existsById(any())).willReturn(true);

            // when
            ThrowableAssert.ThrowingCallable 검증_요청 = () -> menuValidator.validateCreateMenu(메뉴_등록_요청_데이터);

            // then
            assertThatThrownBy(검증_요청).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("상품이 존재하지 않음")
        @Test
        void 상품이_존재하지_않음() {
            // given
            MenuProductRequest 메뉴_상품 = MenuProductRequest.of(강정치킨.getId(), 2L);
            MenuRequest 메뉴_등록_요청_데이터 = MenuRequest.of("더블강정", BigDecimal.valueOf(32_000), 추천메뉴.getId(), Collections.singletonList(메뉴_상품));

            given(menuGroupRepository.existsById(any())).willReturn(true);
            given(productRepository.findById(any(Long.TYPE))).willReturn(Optional.empty());

            // when
            ThrowableAssert.ThrowingCallable 검증_요청 = () -> menuValidator.validateCreateMenu(메뉴_등록_요청_데이터);

            // then
            assertThatThrownBy(검증_요청).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("메뉴 가격이 메뉴 상품 가격의 총합보다 큼")
        @Test
        void 가격이_메뉴_상품_가격의_총합보다_큼() {
            // given
            MenuProductRequest 메뉴_상품 = MenuProductRequest.of(강정치킨.getId(), 2L);
            MenuRequest 메뉴_등록_요청_데이터 = MenuRequest.of("더블강정", BigDecimal.valueOf(35_000), 추천메뉴.getId(), Collections.singletonList(메뉴_상품));

            given(menuGroupRepository.existsById(any())).willReturn(true);
            given(productRepository.findById(any(Long.TYPE))).willReturn(Optional.of(강정치킨));

            // when
            ThrowableAssert.ThrowingCallable 검증_요청 = () -> menuValidator.validateCreateMenu(메뉴_등록_요청_데이터);

            // then
            assertThatThrownBy(검증_요청).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
