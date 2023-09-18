package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.dto.param.query.SelectOrderListParamDto;
import com.ssu.commerce.order.dto.response.OrderWithItemsDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import javax.validation.Valid;

public interface OrderRepositoryCustom {

    Page<OrderWithItemsDto> selectOrderPage(
            @NonNull @Valid final SelectOrderListParamDto paramDto
    );
}
