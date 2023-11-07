package com.ssu.commerce.order.supplier;

import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.core.security.user.UserRole;
import com.ssu.commerce.order.dto.param.SelectCartItemParamDto;
import com.ssu.commerce.order.dto.request.CreateCartItemRequestDto;
import com.ssu.commerce.order.dto.response.DeleteCartItemResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public interface CartTestDataSupplier {

    UUID TEST_VAL_BOOK_ID = UUID.fromString("48a46ad5-2ecd-4a61-81ad-b25a6df896c5");
    UUID TEST_VAL_USER_ID = UUID.fromString("b94c78ae-542c-4f04-b392-4dd107252645");
    UUID TEST_VAL_CART_ITEM_ID = UUID.fromString("b8580a51-c043-4ea8-aff1-8f9fc595921a");

    String TEST_VAL_ACCESS_TOKEN = "TEST_TOKEN";

    static SsuCommerceAuthenticatedPrincipal getSsuCommerceAuthenticatedPrincipal() {
        return new SsuCommerceAuthenticatedPrincipal(
                TEST_VAL_USER_ID,
                TEST_VAL_ACCESS_TOKEN,
                Arrays.asList(
                        UserRole.ROLE_USER
                ),
                "USER_NAME"
        );
    }

    static CreateCartItemRequestDto getCreateCartItemRequestDto() {
        return CreateCartItemRequestDto.builder()
                .bookId(TEST_VAL_BOOK_ID)
                .build();
    }

    static Page<SelectCartItemParamDto> getSelectCartItemParamDto() {
        return new PageImpl<>(
                Arrays.asList(
                        SelectCartItemParamDto.builder()
                                .bookId(TEST_VAL_BOOK_ID)
                                .addedAt(LocalDateTime.now())
                                .build()
                )
        );
    }
}
