package com.ssu.commerce.order.dto.param;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class RegisterBookToCartParamDto {
    @NotEmpty
    private String bookId;
}
