package com.ssu.commerce.order.dto.param;

import com.ssu.commerce.order.model.OrderCart;
import com.ssu.commerce.order.model.OrderCartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SelectOrderCartDto {
    private OrderCart orderCart;
    private OrderCartItem orderCartItemList;
}
