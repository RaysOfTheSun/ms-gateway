package com.learning.msgateway.controllers;

import com.learning.msgateway.configurations.IntegrationConfiguration;
import com.learning.msgateway.feign.DownstreamNextClient;
import com.learning.msgateway.feign.HelloWorldClient;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("hello")
public class HelloWorldController {
    @Autowired
    IntegrationConfiguration integrationConfiguration;

    @Lazy
    @Autowired
    EurekaClient eurekaClient;

    @Autowired
    private HelloWorldClient helloWorldClient;

    @Autowired
    private DownstreamNextClient downstreamNextClient;


    @GetMapping("greet")
    public ResponseEntity<String> sayHello(
            @RequestHeader(value = "x-call-target", required = false, defaultValue = "current") String callTarget
    ) {
        Logger.getAnonymousLogger().info(String.format("target downstream: %s", callTarget));

        if (callTarget.equals("current")) {
            return this.helloWorldClient.sayHello(callTarget);
        }

        Optional<Application> nextApplication = Optional
                .ofNullable(this.eurekaClient.getApplication("ms-blue"));

        if (nextApplication.isEmpty()) {
            return this.helloWorldClient.sayHello(callTarget);
        }

        Optional<InstanceInfo> nextApplicationInstance = Optional
                .ofNullable(nextApplication
                .get()
                .getByInstanceId("next"));


        if (nextApplicationInstance.isEmpty()) {
            Logger
                    .getLogger(this.getClass().getName())
                    .info(String.format("The target service %s appears to be down", callTarget));
            return this.helloWorldClient.sayHello(callTarget);
        }

        InstanceInfo.InstanceStatus nextApplicationStatus = nextApplicationInstance.get().getStatus();

        if (nextApplicationStatus.equals(InstanceInfo.InstanceStatus.UP)) {
            Logger
                    .getLogger(this.getClass().getName())
                    .info(String.format("Attempting to call the target service: %s", callTarget));
            return this.downstreamNextClient.sayHello(callTarget);
        }

        return this.helloWorldClient.sayHello(callTarget);
    }

    @GetMapping("hey")
    public ResponseEntity<String> sayHey(
            @RequestHeader(value = "x-call-target", required = false, defaultValue = "current") String callTarget
    ) {
        Logger.getAnonymousLogger().info(String.format("target downstream: %s", callTarget));

        if (callTarget.equals("current")) {
            return this.helloWorldClient.sayHey(callTarget);
        }

        Optional<Application> nextApplication = Optional
                .ofNullable(this.eurekaClient.getApplication("ms-blue"));

        if (nextApplication.isEmpty()) {
            return this.helloWorldClient.sayHey(callTarget);
        }

        Optional<InstanceInfo> nextApplicationInstance = Optional
                .ofNullable(nextApplication
                        .get()
                        .getByInstanceId("next"));


        if (nextApplicationInstance.isEmpty()) {
            Logger
                    .getLogger(this.getClass().getName())
                    .info(String.format("The target service %s appears to be down", callTarget));
            return this.helloWorldClient.sayHey(callTarget);
        }

        InstanceInfo.InstanceStatus nextApplicationStatus = nextApplicationInstance.get().getStatus();

        if (nextApplicationStatus.equals(InstanceInfo.InstanceStatus.UP)) {
            Logger
                    .getLogger(this.getClass().getName())
                    .info(String.format("Attempting to call the target service: %s", callTarget));
            return this.downstreamNextClient.sayHey(callTarget);
        }

        return this.helloWorldClient.sayHey(callTarget);
    }
}
