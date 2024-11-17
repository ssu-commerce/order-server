package com.ssu.commerce.order.grpc;

import com.google.protobuf.Empty;
import com.ssu.commerce.grpc.UpdateBookStateGrpc;
import com.ssu.commerce.grpc.UpdateBookStateRequest;
import com.ssu.commerce.order.dto.param.UpdateBookStateParamDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UpdateBookStateGrpcService {
    @GrpcClient("updateBookState")
    private UpdateBookStateGrpc.UpdateBookStateBlockingStub updateBookStateBlockingStub;

    public Empty sendMessageToUpdateBookState(UpdateBookStateParamDto paramDto) {

        updateBookStateBlockingStub.updateBookState(
                UpdateBookStateRequest.newBuilder()
                        .setToken(paramDto.getAccessToken())
                        .setBookState(paramDto.getBookState())
                        .addAllId(
                                paramDto.getCreateOrderInfoDto().stream().map(
                                        req -> req.getBookId().toString()
                                ).collect(Collectors.toList()))
                        .build()
        );
        return null;
    }
}
