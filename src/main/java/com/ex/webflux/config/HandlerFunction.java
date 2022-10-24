package com.ex.webflux.config;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface HandlerFunction {
    Mono handle(ServerRequest request);
}
