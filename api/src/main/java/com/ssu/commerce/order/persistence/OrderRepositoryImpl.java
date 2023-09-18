package com.ssu.commerce.order.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssu.commerce.order.dto.param.query.SelectOrderListParamDto;
import com.ssu.commerce.order.dto.response.OrderWithItemsDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.ssu.commerce.order.model.QOrder.order;
import static com.ssu.commerce.order.model.QOrderItem.orderItem;

@Validated
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<OrderWithItemsDto> selectOrderPage(
            @NonNull final SelectOrderListParamDto paramDto
    ) {
        final JPAQuery<OrderWithItemsDto> jpaQuery = jpaQueryFactory.select(Projections.fields(OrderWithItemsDto.class, order,orderItem))
                .from(order)
                .innerJoin(orderItem).on(order.id.eq(orderItem.orderId))
                .fetchJoin()
                .where(eqUserId(paramDto.getUserId()));

        final JPAQuery<Long> countQuery = jpaQueryFactory.select(order.count())
                .from(order)
                .innerJoin(orderItem).on(order.id.eq(orderItem.orderId))
                .where(
                        eqUserId(paramDto.getUserId())
                );

        List<OrderWithItemsDto> result = jpaQuery.fetch();
        return PageableExecutionUtils.getPage(result, paramDto.getPageable(), countQuery::fetchOne);
    }

    private BooleanExpression eqUserId(
            final UUID userId
    ) {
        return Objects.nonNull(userId)
                ? order.userId.eq(userId)
                : null;
    }
}
