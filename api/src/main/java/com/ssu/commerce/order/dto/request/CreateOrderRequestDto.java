package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CreateOrderRequestDto {
    private UUID receiverId;
    private List<CreateOrderInfoDto> orderInfo;

    public List<String> getBookIdInfo() {
        return orderInfo.stream().map(info -> info.getBookId().toString()).collect(Collectors.toList());
    }
}
