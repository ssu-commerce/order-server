package com.ssu.commerce.order.service;

import com.ssu.commerce.order.dto.request.RentalBookRequestDto;
import com.ssu.commerce.order.dto.request.ReturnBookRequestDto;
import com.ssu.commerce.order.dto.response.RentalBookResponseDto;
import com.ssu.commerce.order.dto.response.ReturnBookResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    public RentalBookResponseDto rentalBook(
            RentalBookRequestDto requestDto
    ) {
        /*
         * TODO 구현이 필요합니다.
         *  1. 도서 조회 API call
         *  2. 도서 결제
         *  3. 도서 배송 API call
         */
        return RentalBookResponseDto.builder().build();
    }

    public ReturnBookResponseDto returnBook(
            ReturnBookRequestDto responseDto
    ) {
        /*
         * TODO 구현이 필요합니다.
         *  1. 도서 조회 API call
         *  2. 도서 반납
         *  3. 도서 배송 API call
         */
        return ReturnBookResponseDto.builder().build();
    }

}
