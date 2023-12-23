package com.ssu.commerce.order.feign;

import com.ssu.commerce.order.dto.request.PaymentRequest;
import com.ssu.commerce.order.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentFeignClient", url = "${feign.client.url}")
public interface PaymentFeignClient {

    @PostMapping("/payment")
    PaymentResponse requestPayment(@RequestBody PaymentRequest paymentRequest);

    @PostMapping("/payment/{transactionId}")
    void cancelPayment(@PathVariable(name = "transactionId") Long transactionId);
}
