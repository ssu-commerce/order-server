package com.ssu.commerce.order.dto.param;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Builder
public class RegisterBookToCartParamDto {
    @NotEmpty
    private UUID bookId;
}
