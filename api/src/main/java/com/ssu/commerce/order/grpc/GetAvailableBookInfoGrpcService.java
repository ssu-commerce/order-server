package com.ssu.commerce.order.grpc;

import com.ssu.commerce.order.dto.request.RentalBookRequestDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAvailableBookInfoGrpcService {

    @GrpcClient("rentalBook")
    private RentalBookGrpc.RentalBookBlockingStub rentalBookBlockingStub;

    @GrpcClient("completeRentalBook")
    private CompleteRentalBookGrpc.CompleteRentalBookBlockingStub completeRentalBookBlockingStub;

    @GrpcClient("rollBackRental")
    private RollBackRentalGrpc.RollBackRentalBlockingStub rollBackRentalBlockingStub;

    public RentalBookResponse sendMessageToGetRentalBook(final List<RentalBookRequestDto> requestDto, String accessToken) {

        return rentalBookBlockingStub.rentalBook(
                RentalBookRequest.newBuilder()
                        .setToken(accessToken)
                        .addAllId(
                                requestDto.stream().map(
                                        req -> req.getBookId().toString()
                                ).collect(Collectors.toList())
                        ).build()
        );
    }

    public CompleteRentalBookResponse sendMessageToCompleteRentalBook(final List<RentalBookRequestDto> requestDto, String accessToken) {
        return completeRentalBookBlockingStub.completeRentalBook(
                CompleteRentalBookRequest.newBuilder()
                        .setToken(accessToken)
                        .addAllId(
                                requestDto.stream().map(
                                        req -> req.getBookId().toString()
                                ).collect(Collectors.toList())
                        ).build()
        );
    }

    public RollBackBookResponse sendMessageToRollBackRental(final List<RentalBookRequestDto> requestDto, String accessToken) {
        return rollBackRentalBlockingStub.rollBackRental(
                RollBackBookRequest.newBuilder()
                        .setToken(accessToken)
                        .addAllId(
                                requestDto.stream().map(
                                        req -> req.getBookId().toString()
                                ).collect(Collectors.toList())
                        ).build()
        );
    }
}
