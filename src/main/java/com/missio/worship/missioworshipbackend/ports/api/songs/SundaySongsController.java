package com.missio.worship.missioworshipbackend.ports.api.songs;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.songs.errors.NoSongsForSundayException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Controlador de gestión de canciones para el domingo",
        description = "Endpoints para interactuar con canciones de los domingos")
public interface SundaySongsController {

    @GetMapping("")
    Mono<ResponseEntity<Object>> getSongsForADate(
            @Parameter String date,
            @RequestHeader(value = "Authorization") String bearerToken) throws NoSongsForSundayException, InvalidProvidedToken;

    @PostMapping("")
    Mono<ResponseEntity<Object>> setSongsForADate(
            @RequestBody SongsInput songsInput,
            @RequestHeader(value = "Authorization") String bearerToken);

    @DeleteMapping("")
    Mono<ResponseEntity<Object>> deleteAllSongsForDate(
            @RequestParam String date,
            @RequestHeader(value = "Authorization") String bearerToken);
}
