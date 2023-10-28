package com.ssu.commerce.order.dto.param;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@Builder
public class CreateCartItemParamDto {
    @NotEmpty
    private UUID bookId;
}
