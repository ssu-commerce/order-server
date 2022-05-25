package com.ssu.commerce.order.controller;

import com.ssu.commerce.order.constants.OrderConstant;
import com.ssu.commerce.order.dto.request.RentalBookRequestDto;
import com.ssu.commerce.order.dto.request.ReturnBookRequestDto;
import com.ssu.commerce.order.dto.response.RentalBookResponseDto;
import com.ssu.commerce.order.dto.response.ReturnBookResponseDto;
import com.ssu.commerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(OrderConstant.ORDER_API_BASE)
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/book/rental")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RentalBookResponseDto> rentalBook(
            @RequestBody RentalBookRequestDto requestDto
    ) {

        RentalBookResponseDto responseDto = orderService.rentalBook(requestDto);

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/book/return")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReturnBookResponseDto> returnBook(
            @RequestBody ReturnBookRequestDto requestDto
    ) {

        ReturnBookResponseDto responseDto = orderService.returnBook(requestDto);

        return ResponseEntity.ok().body(responseDto);
    }
}
