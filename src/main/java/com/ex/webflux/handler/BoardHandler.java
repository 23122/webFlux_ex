package com.ex.webflux.handler;

import com.ex.webflux.config.HandlerFunction;
import com.ex.webflux.entity.Board;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

public class BoardHandler {
    List<Board> boards;
    int bNo;

    {
        boards = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            boards.add(new Board(i,"제목"+i,"내용"+i));
        }
    }

    public HandlerFunction list = r ->{
        Flux result = Flux.fromIterable(boards);
        Mono res = ok().body(result, Board.class);
        return res;
    };

    public HandlerFunction create = r ->{
        URI uri = null;
        try {
            uri= new URI("/boards");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return r.body(toMono(Board.class))
                .doOnNext(board -> {
                    board.setBNo(bNo++);
                    boards.add(board);
                })
                .then(created(uri).build());
    };

    public HandlerFunction read = r -> {
        int bNo = Integer.valueOf(r.pathVariable("bNo"));

        Board foundBoard = null;

        for (Board board : boards) {
            if (board.getBNo() == bNo) {
                foundBoard = board;
                break;
            }
        }
        Mono res;
        if (foundBoard==null) {
            res = notFound().build();
        }
        else {
            res = ok().body(Mono.just(foundBoard), Board.class);
        }
        return res;
    };

    public HandlerFunction update = r ->{
        int bNo = Integer.valueOf(r.pathVariable("bNo"));

        return r.bodyToMono(Board.class)
                .doOnNext(updatedBoard -> {
                    for(Board board : boards){
                        if(board.getBNo()==bNo){
                            if(updatedBoard.getTitle() != null) {
                                board.setTitle(updatedBoard.getTitle());
                            }
                            if(updatedBoard.getContent() != null) {
                                board.setContent(updatedBoard.getContent());
                            }
                            break;
                        }
                    }
                }).then(noContent().build());
    };

    public HandlerFunction delete = r -> {
        int bNo = Integer.valueOf(r.pathVariable("bNo"));

        for(Board board : boards) {
            if(board.getBNo() == bNo){
                boards.remove(board);
                break;
            }
        }

        Mono res =noContent().build();
        return res;

    };
}
