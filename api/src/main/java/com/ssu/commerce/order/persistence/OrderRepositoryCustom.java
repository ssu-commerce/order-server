package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.dto.param.query.SelectOrderListParamDto;
import com.ssu.commerce.order.dto.response.OrderWithItemsDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface OrderRepositoryCustom {

    Page<OrderWithItemsDto> selectOrderPage(
            @NonNull @Valid final SelectOrderListParamDto paramDto,
            @NonNull final Pageable pageable
    );
}
