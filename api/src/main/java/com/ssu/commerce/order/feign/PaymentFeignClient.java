package com.ssu.commerce.order.feign;

import com.ssu.commerce.order.dto.request.PaymentRequest;
import com.ssu.commerce.order.dto.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentFeignClient", url = "http://localhost:8082/point")
public interface PaymentFeignClient {

    /*
     *      TODO configuration 설정 및 url 설정
     */

    @PostMapping("/payment")
    PaymentResponse requestPayment(@RequestBody PaymentRequest paymentRequest);
}
