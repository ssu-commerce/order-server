package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.dto.param.query.SelectOrderCartListParamDto;
import com.ssu.commerce.order.model.OrderCart;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

public interface SelectOrderCartPage {

    Page<OrderCart> selectOrderCartPage(
            @NonNull @Valid final SelectOrderCartListParamDto paramDto,
            @NonNull final Pageable pageable
    );
}
