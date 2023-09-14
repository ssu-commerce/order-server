package com.ssu.commerce.order.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssu.commerce.order.dto.param.query.SelectOrderCartListParamDto;
import com.ssu.commerce.order.dto.param.SelectOrderCartDto;
import com.ssu.commerce.order.model.OrderCart;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.ssu.commerce.order.model.QOrderCart.orderCart;
import static com.ssu.commerce.order.model.QOrderCartItem.orderCartItem;

@Validated
@Repository
@RequiredArgsConstructor
public class OrderCartRepositoryImpl implements OrderCartRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<SelectOrderCartDto> selectOrderCartPage(
            @NonNull final SelectOrderCartListParamDto paramDto,
            @NonNull Pageable pageable
    ) {

        final List<SelectOrderCartDto> selectOrderCartDtoList = jpaQueryFactory
                .select(Projections.constructor(SelectOrderCartDto.class, orderCart, orderCartItem))
                .from(orderCart)
                .leftJoin(orderCartItem).on(orderCart.id.eq(orderCartItem.orderCartId))
                .fetch();

        final JPAQuery<Long> countQuery = jpaQueryFactory.select(orderCart.count())
                .from(orderCart)
                .leftJoin(orderCartItem).on(orderCart.id.eq(orderCartItem.orderCartId));

        return PageableExecutionUtils.getPage(selectOrderCartDtoList, pageable, countQuery::fetchOne);

    }
}
