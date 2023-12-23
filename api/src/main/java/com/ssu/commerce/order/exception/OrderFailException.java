package com.ssu.commerce.order.exception;

import com.ssu.commerce.core.error.SsuCommerceException;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper=false)
public class OrderFailException extends SsuCommerceException {

    public OrderFailException(@Nullable OrderErrorCode errorCode, @NotNull String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.format("ORDER_%s", errorCode), message);
    }
}
