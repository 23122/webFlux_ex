package com.ex.webflux.config;

import com.ex.webflux.handler.BoardHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Slf4j
@Component
public class WebFluxConfig {

    @Bean
    RouterFunction route() {

        BoardHandler boardHandler= new BoardHandler();

        return RouterFunctions.route(GET("/boards"), boardHandler.list)
                .andRoute(POST("/boards"), boardHandler.create)
                .andRoute(GET("/boards/{num}"), boardHandler.read)
                .andRoute(PUT("/boards/{num}"), boardHandler.update)
                .andRoute(DELETE("/boards/{num}"), boardHandler.delete);
    }

}
