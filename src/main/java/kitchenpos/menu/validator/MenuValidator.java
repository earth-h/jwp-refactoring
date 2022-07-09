package kitchenpos.menu.validator;

import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.dto.CreateMenuRequest;
import kitchenpos.menu.exception.MenuException;
import kitchenpos.menu.exception.MenuExceptionType;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import kitchenpos.product.exception.ProductException;
import kitchenpos.product.exception.ProductExceptionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuValidator {
    private final ProductRepository productRepository;
    private final MenuGroupRepository menuGroupRepository;

    public MenuValidator(final ProductRepository productRepository, final MenuGroupRepository menuGroupRepository) {
        this.productRepository = productRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    public void validate(final CreateMenuRequest createMenuRequest) {
        existsByMenuGroupId(createMenuRequest.getMenuGroupId());

        final List<MenuProduct> menuProducts = createMenuRequest.toMenuProducts();
        final List<Long> productIds = menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());

        final List<Product> products = productRepository.findAllById(productIds);
        if (productIds.size() != products.size()) {
            throw new ProductException(ProductExceptionType.PRODUCT_NOT_FOUND);
        }

        final BigDecimal totalPrice = getTotalPrice(menuProducts, products);
        if (createMenuRequest.getPrice().compareTo(totalPrice) > BigDecimal.ZERO.intValue()) {
            throw new MenuException(MenuExceptionType.EXCEED_PRICE);
        }

    }

    private void existsByMenuGroupId(final Long menuGroupId) {
        if (!menuGroupRepository.existsById(menuGroupId)) {
            throw new MenuException(MenuExceptionType.MENU_GROUP_NOT_FOUND);
        }
    }

    private BigDecimal getTotalPrice(List<MenuProduct> menuProducts, List<Product> products) {
        return BigDecimal.valueOf(products.stream()
                .map(it -> it.getPrice().multiply(getQuantity(menuProducts, it)))
                .mapToDouble(BigDecimal::doubleValue)
                .sum()
        );
    }

    private BigDecimal getQuantity(List<MenuProduct> menuProducts, Product product) {
        return BigDecimal.valueOf(menuProducts.stream()
                .filter(it -> it.equalsProductId(product.getId()))
                .findFirst()
                .map(MenuProduct::getQuantity)
                .orElseThrow(() -> new ProductException(ProductExceptionType.PRODUCT_NOT_FOUND))
        );
    }
}
