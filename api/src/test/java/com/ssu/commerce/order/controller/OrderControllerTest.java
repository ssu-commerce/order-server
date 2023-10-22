package com.ssu.commerce.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.constant.OrderConstant;
import com.ssu.commerce.order.dto.request.RentalBookRequestDto;
import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.service.OrderService;
import com.ssu.commerce.order.supplier.OrderControllerTestDataSupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Slf4j
class OrderControllerTest implements OrderControllerTestDataSupplier {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setAuthUser() {
        SsuCommerceAuthenticatedPrincipal principal = OrderControllerTestDataSupplier.getSsuCommerceAuthenticatedPrincipal();
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getAccessToken(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void rentalBook() {
      /*  UUID userId = TEST_VAL_USER_ID;
        UUID orderId = TEST_VAL_ORDER_ID;
        Order order = OrderControllerTestDataSupplier.getOrder();
        RentalBookRequestDto rentalBookRequestDto = OrderControllerTestDataSupplier.getRentalBookRequestDto();

        when(orderService.rentalBook(rentalBookRequestDto, userId)).thenReturn(order);

        assertDoesNotThrow(() -> {
            mockMvc.perform(post(OrderConstant.ORDER_API_BASE + "/book/rental")
                            .content(objectMapper.writeValueAsString(rentalBookRequestDto))
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(csrf())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(orderId.toString())));
        });

        verify(orderService, times(1)).rentalBook(rentalBookRequestDto, userId);*/
    }
}