package com.ssu.commerce.order.grpc;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrpcClientService {

    @GrpcClient("getBookList")
    private GetBookListGrpc.GetBookListBlockingStub grpcStub;

    public BookListResponse sendMessageToGetBookList(final int id, final int size) {
        log.info("grpc client service start");
        BookListResponse response =  this.grpcStub.getBookList(BookListRequest.newBuilder().setPageNumber(id).setPageSize(size).build());
        log.info("grpc client service end");
        return response;
    }
}
