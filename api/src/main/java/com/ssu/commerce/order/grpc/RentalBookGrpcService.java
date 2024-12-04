package com.ssu.commerce.order.grpc;

import com.google.protobuf.Empty;
import com.ssu.commerce.grpc.UpdateBookStateDisSharableGrpc;
import com.ssu.commerce.grpc.UpdateBookStateDisSharableRequest;
import com.ssu.commerce.grpc.UpdateBookStateSharingGrpc;
import com.ssu.commerce.grpc.UpdateBookStateSharingRequest;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalBookGrpcService {
    @GrpcClient("updateBookStateRequestDisSharable")
    private UpdateBookStateDisSharableGrpc.UpdateBookStateDisSharableBlockingStub updateBookStateDisSharableBlockingStub;

    @GrpcClient("updateBookStateSharing")
    private UpdateBookStateSharingGrpc.UpdateBookStateSharingBlockingStub updateBookStateSharingBlockingStub;


    public Empty updateBookStateBeforeShare(List<String> bookIdList, String token) {
        return updateBookStateSharingBlockingStub.updateBookStateSharing(
                UpdateBookStateSharingRequest.newBuilder()
                        .setToken(token)
                        .addAllId(bookIdList)
                        .build()
        );
    }

    public Empty updateBookStateAfterShare(List<String> bookIdList, String token) {
        return updateBookStateDisSharableBlockingStub.updateBookStateDisSharable(
                UpdateBookStateDisSharableRequest.newBuilder()
                        .setToken(token)
                        .addAllId(bookIdList)
                        .build()
        );
    }
}
