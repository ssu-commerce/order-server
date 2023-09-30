package com.ssu.commerce.order.exception;

import com.ssu.commerce.core.exception.SsuCommerceException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

public class RollBackException extends SsuCommerceException {
    public RollBackException(@Nullable String errorCode, @NotNull String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, message);
    }
}
