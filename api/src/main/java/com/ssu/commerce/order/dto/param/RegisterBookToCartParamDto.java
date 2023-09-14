package com.ssu.commerce.order.dto.param;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@Builder
public class RegisterBookToCartParamDto {
    @NotEmpty
    private UUID bookId;
}
