package com.ssu.commerce.order.exception;

import com.ssu.commerce.core.error.SsuCommerceException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

public class OrderFailException extends SsuCommerceException {

    public OrderFailException(@Nullable String errorCode, @NotNull String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorCode, message);
    }
}
