package com.ssu.commerce.order.grpc;

import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpdateBookStateGrpcService {
    @GrpcClient("updateBookState")
    private UpdateBookStateGrpc.UpdateBookStateBlockingStub updateBookStateBlockingStub;

    public UpdateBookStateResponse sendMessageToUpdateBookState(final List<CreateOrderRequestDto> requestDto, String accessToken, BookState bookState) {

        return updateBookStateBlockingStub.updateBookState(
                UpdateBookStateRequest.newBuilder()
                        .setToken(accessToken)
                        .setBookState(bookState)
                        .addAllId(
                                requestDto.stream().map(
                                        req -> req.getBookId().toString()
                                ).collect(Collectors.toList()))
                        .build()
        );
    }
}
