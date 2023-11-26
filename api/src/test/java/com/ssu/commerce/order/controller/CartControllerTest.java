package com.ssu.commerce.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.dto.mapper.CreateCartItemParamDtoMapper;
import com.ssu.commerce.order.dto.param.CartItemParamDto;
import com.ssu.commerce.order.dto.request.CreateCartItemRequestDto;
import com.ssu.commerce.order.service.CartService;
import com.ssu.commerce.order.supplier.CartTestDataSupplier;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CartController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CartControllerTest implements CartTestDataSupplier {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartService cartService;

    @BeforeEach
    void setAuthUser() {
        SsuCommerceAuthenticatedPrincipal principal = CartTestDataSupplier.getSsuCommerceAuthenticatedPrincipal();
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getAccessToken(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void getCartItem_success() throws Exception {

        when(cartService.getCartItem(
                argThat(dto -> dto instanceof CartItemParamDto && dto.getUserId().equals(TEST_VAL_USER_ID))
        )).thenReturn(CartTestDataSupplier.getSelectCartItemParamDto());

        mockMvc.perform(get("/api/v1/cart")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService).getCartItem(argThat(dto -> dto instanceof CartItemParamDto && dto.getUserId().equals(TEST_VAL_USER_ID)));
    }

    @Test
    void createCartItem_success() throws Exception {
        CreateCartItemRequestDto requestDto = CartTestDataSupplier.getCreateCartItemRequestDto();

        when(cartService.createCartItem(
                CreateCartItemParamDtoMapper.INSTANCE.map(requestDto), TEST_VAL_USER_ID)).thenReturn(TEST_VAL_CART_ITEM_ID);

        mockMvc.perform(post("/api/v1/cart")
                        .content(objectMapper.writeValueAsString(
                                CartTestDataSupplier.getCreateCartItemRequestDto()
                        )).contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(String.valueOf(TEST_VAL_CART_ITEM_ID))));
        verify(cartService).createCartItem(eq(CreateCartItemParamDtoMapper.INSTANCE.map(requestDto)), eq(TEST_VAL_USER_ID));

    }

    @Test
    void deleteCartItem_success() throws Exception {

        when(cartService.deleteCartItem(TEST_VAL_CART_ITEM_ID)).thenReturn(TEST_VAL_CART_ITEM_ID);

        mockMvc.perform(delete("/api/v1/cart/" + TEST_VAL_CART_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.id", Matchers.equalTo(String.valueOf(TEST_VAL_CART_ITEM_ID)))));

        verify(cartService).deleteCartItem(TEST_VAL_CART_ITEM_ID);
    }

    @Test
    void deleteCartItem_fail_NotFoundException() throws Exception {
        String errorMessage = String.format("cartItem not found; cartItemId=%s", TEST_VAL_BOOK_ID);
        NotFoundException notFoundException = new NotFoundException(errorMessage, "ORDER_CART_001");

        when(cartService.deleteCartItem(TEST_VAL_BOOK_ID)).thenThrow(notFoundException);

        mockMvc.perform(delete("/api/v1/cart/" + TEST_VAL_BOOK_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.equalTo(errorMessage)));

        verify(cartService).deleteCartItem(TEST_VAL_BOOK_ID);
    }
}
