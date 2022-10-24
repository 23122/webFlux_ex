package com.ex.webflux.config;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface HandlerFunction extends org.springframework.web.reactive.function.server.HandlerFunction<ServerResponse> {
    Mono handle(ServerRequest request);
}
