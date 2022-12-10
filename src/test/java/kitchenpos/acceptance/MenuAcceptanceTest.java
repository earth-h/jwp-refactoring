package kitchenpos.acceptance;

import static kitchenpos.acceptance.MenuGroupRestAssured.메뉴_그룹_등록되어_있음;
import static kitchenpos.acceptance.MenuRestAssured.메뉴_등록되어_있음;
import static kitchenpos.acceptance.MenuRestAssured.메뉴_목록_조회_요청;
import static kitchenpos.acceptance.MenuRestAssured.메뉴_생성_요청;
import static kitchenpos.acceptance.ProductRestAssured.상품_등록되어_있음;
import static kitchenpos.domain.MenuGroupTestFixture.generateMenuGroup;
import static kitchenpos.domain.MenuProductTestFixture.generateMenuProduct;
import static kitchenpos.domain.MenuTestFixture.generateMenu;
import static kitchenpos.domain.ProductTestFixture.generateProductRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.dto.ProductRequest;
import kitchenpos.dto.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("메뉴 관련 인수 테스트")
public class MenuAcceptanceTest extends AcceptanceTest {

    private ProductResponse 감자튀김;
    private ProductResponse 불고기버거;
    private ProductResponse 치킨버거;
    private ProductResponse 콜라;
    private MenuGroup 햄버거세트;
    private MenuProduct 감자튀김상품;
    private MenuProduct 불고기버거상품;
    private MenuProduct 치킨버거상품;
    private MenuProduct 콜라상품;
    private Menu 불고기버거세트;
    private Menu 치킨버거세트;

    @BeforeEach
    public void setUp() {
        super.setUp();
        햄버거세트 = 메뉴_그룹_등록되어_있음(generateMenuGroup("햄버거세트")).as(MenuGroup.class);
        감자튀김 = 상품_등록되어_있음(generateProductRequest("감자튀김", BigDecimal.valueOf(3000L))).as(ProductResponse.class);
        콜라 = 상품_등록되어_있음(generateProductRequest("콜라", BigDecimal.valueOf(1500L))).as(ProductResponse.class);
        불고기버거 = 상품_등록되어_있음(generateProductRequest("불고기버거", BigDecimal.valueOf(4000L))).as(ProductResponse.class);
        치킨버거 = 상품_등록되어_있음(generateProductRequest("치킨버거", BigDecimal.valueOf(4500L))).as(ProductResponse.class);
        감자튀김상품 = generateMenuProduct(1L, null, 감자튀김.getId(), 1L);
        콜라상품 = generateMenuProduct(2L, null, 콜라.getId(), 1L);
        불고기버거상품 = generateMenuProduct(3L, null, 불고기버거.getId(), 1L);
        치킨버거상품 = generateMenuProduct(3L, null, 치킨버거.getId(), 1L);
        불고기버거세트 = generateMenu("불고기버거세트", BigDecimal.valueOf(8500L), 햄버거세트.getId(), Arrays.asList(감자튀김상품, 콜라상품, 불고기버거상품));
        치킨버거세트 = generateMenu("치킨버거세트", BigDecimal.valueOf(9000L), 햄버거세트.getId(), Arrays.asList(감자튀김상품, 콜라상품, 치킨버거상품));
    }

    @DisplayName("메뉴를 생성한다.")
    @Test
    void createMenu() {
        // when
        ExtractableResponse<Response> response = 메뉴_생성_요청(불고기버거세트);

        // then
        메뉴_생성됨(response);
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findAllProducts() {
        // given
        ExtractableResponse<Response> 불고기버거_생성_응답 = 메뉴_등록되어_있음(불고기버거세트);
        ExtractableResponse<Response> 치킨버거_생성_응답 = 메뉴_등록되어_있음(치킨버거세트);

        // when
        ExtractableResponse<Response> response = 메뉴_목록_조회_요청();

        // then
        메뉴_목록_응답됨(response);
        메뉴_목록_포함됨(response, Arrays.asList(불고기버거_생성_응답, 치킨버거_생성_응답));
    }

    private static void 메뉴_생성됨(ExtractableResponse<Response> response) {
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("Location")).isNotBlank()
        );
    }

    private static void 메뉴_목록_응답됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private static void 메뉴_목록_포함됨(ExtractableResponse<Response> response, List<ExtractableResponse<Response>> createdResponses) {
        List<Long> expectedMenuIds = createdResponses.stream()
                .map(it -> Long.parseLong(it.header("Location").split("/")[3]))
                .collect(Collectors.toList());

        List<Long> resultMenuIds = response.jsonPath().getList(".", Menu.class).stream()
                .map(Menu::getId)
                .collect(Collectors.toList());

        assertThat(resultMenuIds).containsAll(expectedMenuIds);
    }
}
