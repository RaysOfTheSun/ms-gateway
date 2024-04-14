package com.learning.msgateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "helloWorld")
public interface HelloWorldClient {

    @GetMapping("greet")
    ResponseEntity<String> sayHello(@RequestHeader("x-call-target") String callTarget);

    @GetMapping("hey")
    ResponseEntity<String> sayHey(@RequestHeader("x-call-target") String callTarget);
}
