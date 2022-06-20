package com.ssu.commerce.order.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeliveryCode {

    DELIVERY("DELVY"),
    PICKUP("PICUP")
    ;

    private String code;
}
