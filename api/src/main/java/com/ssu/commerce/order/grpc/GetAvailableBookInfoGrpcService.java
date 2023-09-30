package com.ssu.commerce.order.grpc;

import com.ssu.commerce.order.grpc.RentalBookGrpc.RentalBookBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableBookInfoGrpcService {

    @GrpcClient("rentalBook")
    private RentalBookBlockingStub rentalBookBlockingStub;

    @GrpcClient("completeRentalBook")
    private CompleteRentalBookGrpc.CompleteRentalBookBlockingStub completeRentalBookBlockingStub;

    @GrpcClient("rollBackRental")
    private RollBackRentalGrpc.RollBackRentalBlockingStub rollBackRentalBlockingStub;

    public RentalBookResponse sendMessageToGetRentalBook(final String bookId, final String token) {
        return this.rentalBookBlockingStub.rentalBook(
                RentalBookRequest.newBuilder()
                        .setToken(token)
                        .setId(bookId)
                        .build()
        );
    }

    public CompleteRentalBookResponse sendMessageToCompleteRentalBook(final String bookId, final String token) {
        return this.completeRentalBookBlockingStub.completeRentalBook(
                CompleteRentalBookRequest.newBuilder()
                        .setToken(token)
                        .setId(bookId)
                        .build()
        );
    }

    public RollBackBookResponse sendMessageToRollBackRental(final String bookId, final String token) {
        return this.rollBackRentalBlockingStub.rollBackRental(
                RollBackBookRequest.newBuilder()
                        .setToken(token)
                        .setId(bookId)
                        .build()
        );
    }
}
