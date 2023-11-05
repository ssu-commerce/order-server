package com.ssu.commerce.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.order.dto.param.GetOrderListParamDto;
import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import com.ssu.commerce.order.dto.response.OrderListParamDto;
import org.hamcrest.Matchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.service.OrderService;
import com.ssu.commerce.order.supplier.OrderTestDataSupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Slf4j
class OrderControllerTest implements OrderTestDataSupplier {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setAuthUser() {
        SsuCommerceAuthenticatedPrincipal principal = OrderTestDataSupplier.getSsuCommerceAuthenticatedPrincipal();
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getAccessToken(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void createOrder_success() throws Exception {
        SsuCommerceAuthenticatedPrincipal principal = OrderTestDataSupplier.getSsuCommerceAuthenticatedPrincipal();
        List<CreateOrderRequestDto> requestDto = OrderTestDataSupplier.getCreateOrderRequestDto();

        when(orderService.createOrder(eq(requestDto), eq(principal.getAccessToken()), eq(principal.getUserId()))).thenReturn(OrderTestDataSupplier.getOrder());

        mockMvc.perform(post("/api/v1/order")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(String.valueOf(TEST_VAL_ORDER_ID))));

        verify(orderService).createOrder(eq(requestDto), eq(principal.getAccessToken()), eq(principal.getUserId()));
    }

    @Test
    void createOrder_badRequest_requestBody_is_null() throws Exception {
        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrderItem_success() throws Exception {
        UUID orderItemId = TEST_VAL_ORDER_ITEM_ID;

        when(orderService.updateOrderItem(orderItemId)).thenReturn(orderItemId);

        mockMvc.perform(put("/api/v1/order/" + orderItemId)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService).updateOrderItem(orderItemId);
    }

    @Test
    void updateOrderItem_fail_NotFoundException() throws Exception {
        UUID orderItemId = TEST_VAL_ORDER_ITEM_ID;
        String errorMessage = String.format("orderItem not found; orderItemId=%s", orderItemId);
        NotFoundException notFoundException = new NotFoundException(errorMessage, "ORDER_ITEM_001");

        when(orderService.updateOrderItem(orderItemId)).thenThrow(notFoundException);

        mockMvc.perform(put("/api/v1/order/" + orderItemId)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo(errorMessage)));

        verify(orderService).updateOrderItem(orderItemId);
    }

    @Test
    void testGetOrder_success() throws Exception {
        GetOrderListParamDto getOrderListParamDto = OrderTestDataSupplier.getGetOrderListParamDto();
        Page<OrderListParamDto> orderListParamDto = new PageImpl<>(Arrays.asList(OrderTestDataSupplier.getOrderListParamDto()));

        when(orderService.getOrderList(
                argThat(dto -> dto instanceof GetOrderListParamDto && dto.getUserId().equals(getOrderListParamDto.getUserId()
                )))).thenReturn(orderListParamDto);

        mockMvc.perform(get("/api/v1/order")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService)
                .getOrderList(argThat(dto -> dto instanceof GetOrderListParamDto &&
                        dto.getUserId().equals(getOrderListParamDto.getUserId())));
    }
}
