package com.ssu.commerce.order.dto.param;

import com.ssu.commerce.order.model.OrderCart;
import com.ssu.commerce.order.model.OrderCartItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SelectOrderCartDto {
    private OrderCart orderCart;
    private OrderCartItem orderCartItemList;
}
