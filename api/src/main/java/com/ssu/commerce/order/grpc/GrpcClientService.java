package com.ssu.commerce.order.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    @GrpcClient("getBookList")
    private GetBookListGrpc.GetBookListBlockingStub grpcStub;

    public BookListResponse sendMessageToGetBookList(final int id, final int size) {
        BookListResponse response =  this.grpcStub.getBookList(BookListRequest.newBuilder().setPageNumber(id).setPageSize(size).build());
        return response;
    }
}
