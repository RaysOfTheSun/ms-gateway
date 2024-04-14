package com.learning.msgateway.feign;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "downstream-next")
public interface DownstreamNextClient {
    @GetMapping("greet")
    ResponseEntity<String> sayHello(@RequestHeader("x-call-target") String callTarget);

    @GetMapping("hey")
    ResponseEntity<String> sayHey(@RequestHeader("x-call-target") String callTarget);
}
