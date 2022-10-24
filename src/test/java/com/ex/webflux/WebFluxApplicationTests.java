package com.ex.webflux;

import com.ex.webflux.config.WebFluxConfig;
import com.ex.webflux.entity.Board;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
public class WebFluxApplicationTests {
    WebTestClient webClient;
    @Before
    public void setUp() {
        webClient= WebTestClient
                .bindToRouterFunction(new WebFluxConfig().route())
                .build();
    }
    @Test
    public void list() {
        webClient.get()
                .uri("/boards")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Board.class);
    }
    @Test
    public void create() {
        Board board = new Board() {
            {
                this.setTitle("제목");
                this.setContent("내용");
            }
        };
        webClient.post()
                .uri("/boards")
                .body(Mono.just(board), Board.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }
    @Test
    public void read() {
        webClient.get()
                .uri("/boards/3")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"title\":\"제목 3\", \"content\":\"내용 3\"}");
    }
    @Test
    public void put() {
        Board board = new Board() {
            {
                this.setTitle("제목");
                this.setContent("내용");
            }
        };

        webClient.put()
                .uri("/boards/5")
                .body(Mono.just(board), Board.class)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }
    @Test
    public void delete() {
        webClient.delete()
                .uri("/boards/7")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

}
