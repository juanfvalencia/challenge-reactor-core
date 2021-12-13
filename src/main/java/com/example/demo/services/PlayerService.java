package com.example.demo.services;

import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;



@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;


    private Flux<Player> getAll() {
        return playerRepository.findAll()
                .buffer(100)
                .flatMap(players -> Flux.fromStream(players.parallelStream()))
                ;
    }

    private Flux<Player> mayores34() {

        return getAll()
                .buffer(100)
                .flatMap(juga -> Flux.fromStream(juga.parallelStream()))
                .filter(jugador -> {
                    try {
                        return jugador.getAge() > 24;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }

    private Flux<Player> clubEspecifico() {
        return mayores34()
                .buffer(100)
                .flatMap(juga -> Flux.fromStream(juga.parallelStream()))
                .filter(jugador -> {
                    try {
                        return jugador.getClub().equals("FC Barcelona");
                    } catch (Exception e) {
                        return false;
                    }
                })
                .onErrorContinue((e, i) ->
                        System.out.println("error por filtro 1 "+i)
                );
    }


    public Flux<Player> getFilterPlayer() {
        return clubEspecifico()
                .buffer(100)
                .flatMap(juga -> Flux.fromStream(juga.parallelStream()))

                .onErrorContinue((e, i) ->
                        System.out.println("error filtrandoListas "+i));
    }


    public Flux<List<Player>> getRankingPlayer() {

        return getAll()
                .buffer(100)
                .flatMap(juga -> Flux.fromStream(juga.parallelStream()))
                .distinct()
                .groupBy(Player::getNational)
                .flatMap(Flux::collectList)
                .map(lista -> {
                    Collections.sort(lista);
                    return lista;
                })
                .onErrorContinue((e, i) ->
                        System.out.println("error filtrandoListas "+i));



    }



    public Mono<Player> prueba(){
        return playerRepository.findByCode("158023");

    }

}