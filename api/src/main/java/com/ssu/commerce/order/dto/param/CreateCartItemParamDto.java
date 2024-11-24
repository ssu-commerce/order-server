package com.ssu.commerce.order.dto.param;

import com.ssu.commerce.order.dto.request.CreateCartItemRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class CreateCartItemParamDto {
    @NotEmpty
    private List<UUID> bookIds;

    public CreateCartItemParamDto(CreateCartItemRequestDto createCartItemRequestDto) {
        bookIds = createCartItemRequestDto.getBookIds();
    }
}
