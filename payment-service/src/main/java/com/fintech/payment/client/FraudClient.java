package com.fintech.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// 1. Make the records public so the Service can see them
// 2. You can keep them in this file, but they must be public
// 3. Since they are top-level, just remove 'static' (only nested classes are static)

@FeignClient(name = "fraud-service", url = "http://localhost:5000")
public interface FraudClient {
    @PostMapping("/score")
    FraudResponse evaluateFraud(@RequestBody FraudRequest request);
}

