package com.ssu.commerce.order.grpc;

import com.ssu.commerce.order.dto.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GrpcClientController {

    private final GrpcClientService grpcClientService;

    @GetMapping("getBookListTest-grpc")
    public List<TestDto> getBookList() {
        BookListResponse response = grpcClientService.sendMessageToGetBookList(0, 10);

        return response.getBookListList()
                .stream().map(
                        bookDto -> TestDto.builder()
                                .id(bookDto.getId())
                                .title(bookDto.getTitle())
                                .content(bookDto.getContent())
                                .writer(bookDto.getWriter())
                                .price(bookDto.getPrice())
                                .isbn(bookDto.getIsbn())
                                .maxBorrowDay(bookDto.getMaxBorrowDay())
                                .categoryId(bookDto.getCategoryId())
                                .build()
                ).collect(Collectors.toList());
    }
}

