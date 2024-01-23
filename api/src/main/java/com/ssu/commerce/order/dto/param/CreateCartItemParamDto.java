package com.ssu.commerce.order.dto.param;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class CreateCartItemParamDto {
    @NotEmpty
    private List<UUID> bookIds;
}
