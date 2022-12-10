package kitchenpos.common.constant;

public enum ErrorCode {

    이름은_비어_있을_수_없음("[ERROR] 이름은 비어있을 수 없습니다."),
    방문한_손님_수는_0보다_작을_수_없음("[ERROR] 방문한 손님 수는 0보다 작을 수 없습니다."),
    가격은_비어있을_수_없음("[ERROR] 가격은 비어있을 수 없습니다."),
    가격은_0보다_작을_수_없음("[ERROR] 가격은 0보다 작을 수 없습니다."),
    수량은_0보다_작을_수_없음("[ERROR] 수량은 0보다 작을 수 없습니다."),
    주문_테이블은_2개_이상여야함("[ERROR] 주문 테이블은 2개 이상 존재해야 합니다."),
    주문_항목은_비어있을_수_없음("[ERROR] 주문 항목은 비어있을 수 없습니다."),
    메뉴_상품은_비어있을_수_없음("[ERROR] 메뉴 항목은 비어있을 수 없습니다."),
    메뉴_그룹은_비어있을_수_없음("[ERROR] 메뉴 그룹은 비어있을 수 없습니다."),
    존재하지_않는_상품("[ERROR] 존재하지 않는 상품입니다."),
    메뉴의_가격은_메뉴상품들의_가격의_합보다_클_수_없음("[ERROR] 메뉴의 가격은 메뉴 상품들의 가격의 합보다 클 수 없습니다."),
    존재하지_않는_메뉴_그룹("[ERROR] 존재하지 않는 메뉴 그룹입니다."),
    ;

    private final String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
