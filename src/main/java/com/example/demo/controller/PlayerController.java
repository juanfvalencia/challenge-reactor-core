package com.example.demo.controller;

import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerRepository playerRepository;

   /*
    @GetMapping
    public Flux<Player> getFilteredPlayers() {
        return playerRepository.findAll();
    }*/


    @GetMapping
    public Flux<Player> getFilteredPlayers() {
        playerService.getRankingPlayer();
        return playerService.getFilterPlayer()
                .buffer(100)
                .flatMap(juga ->Flux.fromStream(juga.parallelStream()));

    }


    @GetMapping("/listas")
    public Flux<List<Player>> getListasRankingPlayers() {
        return playerService.getRankingPlayer();
    }


    @GetMapping("/prueba")
    public Mono<Player> prueba() {
        System.out.println("asdad\n");
        playerService.prueba().subscribe(System.out::println);
        //System.out.println("asdad edad\n");
        // playerRepository.findByAge(34).subscribe(System.out::println);;
        return Mono.just(new Player());
    }

}