package com.ex.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebfluxController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello WenFlux!!!";
    }

    @GetMapping("/hello/mono")
    public Mono helloMono() {
        return Mono.just("Hello Mono!!!");
    }

    @GetMapping("/hello/flux")
    public Flux helloFlux() {
        return Flux.just(
                "Hello WenFlux!!!",
                "Hello Reactor 3!!!",
                "Hello Reactive Streams!!!");
    }
}
