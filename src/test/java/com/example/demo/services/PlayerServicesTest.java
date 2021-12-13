package com.example.demo.services;

import com.example.demo.CsvUtilFile;
import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class PlayerServicesTest {
    @Mock
    PlayerService playerService;

    @Mock
    PlayerRepository playerRepository;

    @Test
    void getFilterPlayer() {

        Flux<Player> jugadores = Flux.fromIterable(CsvUtilFile.getPlayers());
        Mockito.when(playerService.getFilterPlayer()).thenReturn(jugadores);
        StepVerifier.create(playerService.getFilterPlayer())
                .expectNextCount(18207)
                .verifyComplete();
    }


    @Test
    void testFilterPlayer() {
        Flux<Player> jugadores = Flux.fromIterable(CsvUtilFile.getPlayers()) .filter(jugador -> {
                    try {
                        return jugador.getAge() > 24;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .filter(jugador -> {
                    try {
                        return jugador.getClub().equals("FC Barcelona");
                    } catch (Exception e) {
                        return false;
                    }
                })
                .onErrorContinue((e, i) ->
                        System.out.println("error por filtro 1 " + i)
                );


        Mockito.when(playerService.getFilterPlayer()).thenReturn(jugadores);

        jugadores.subscribe(System.out::println);

        StepVerifier.create(playerService.getFilterPlayer())
                .expectNextCount(13)
                .verifyComplete();


    }


}
