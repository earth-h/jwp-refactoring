package kitchenpos.dto.menu;

public class MenuProductRequest {

    private Long productId;
    private long quantity;

    public MenuProductRequest() {

    }

    public MenuProductRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
