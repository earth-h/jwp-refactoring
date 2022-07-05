package kitchenpos.domain.order;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    COOKING, MEAL, COMPLETION;

    public static List<String> cnaNotUngroupStatus() {
        return Arrays.asList(COOKING.name(), MEAL.name());
    }
}
