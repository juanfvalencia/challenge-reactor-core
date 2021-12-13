package com.example.demo.repository;

import com.example.demo.model.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {

    Mono<Player> findByCode(String code);
    Flux<Player> findByAge(int code);


}