package kitchenpos.menu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(optional = false)
    private Menu menu;

    @Column(nullable = false)
    private Long productId;

    private long quantity;

    protected MenuProduct() {
    }

    public MenuProduct(Long productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void includeToMenu(Menu menu) {
        this.menu = menu;
    }

    public Long getSeq() {
        return seq;
    }

    public Menu getMenu() {
        return menu;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

}
