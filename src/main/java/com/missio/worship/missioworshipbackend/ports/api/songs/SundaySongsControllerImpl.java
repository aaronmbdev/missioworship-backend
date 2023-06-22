package com.missio.worship.missioworshipbackend.ports.api.songs;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.libs.songs.SundaySongService;
import com.missio.worship.missioworshipbackend.libs.songs.errors.NoSongsForSundayException;
import com.missio.worship.missioworshipbackend.libs.songs.errors.SongDoesNotExistsException;
import com.missio.worship.missioworshipbackend.libs.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Date;

@RequestMapping("v1/sunday_songs/")
@RestController
@AllArgsConstructor
@Slf4j
public class SundaySongsControllerImpl implements SundaySongsController {

    private final SundaySongService service;

    @Override
    public Mono<ResponseEntity<Object>> getSongsForADate(String date, String bearerToken)  {
        try {
            var formattedDate = DateUtils.parseFrom(date);
            var songs = service.getSongsForDate(formattedDate, bearerToken);
            return Mono.just(ResponseEntity.ok(songs));
        } catch (NoSongsForSundayException e) {
            return Mono.just(new NotFoundResponse(e.getMessage()).toObjectEntity());
        } catch (InvalidProvidedToken e) {
            return Mono.just(new UnauthorizedResponse(e.getMessage()).toObjectEntity());
        } catch (ParseException e) {
            return Mono.just(new BadRequestResponse(e.getMessage()).toObjectEntity());
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> setSongsForADate(SongsInput songsInput, String bearerToken) {
        try {
            service.putSongsForDate(songsInput, bearerToken);
            return Mono.just(ResponseEntity.ok().build());
        } catch (SongDoesNotExistsException e) {
            return Mono.just(new BadRequestResponse(e.getMessage()).toObjectEntity());
        } catch (InvalidProvidedToken e) {
            return Mono.just(new UnauthorizedResponse(e.getMessage()).toObjectEntity());
        } catch (ParseException e) {
            return Mono.just(new BadRequestResponse(e.getMessage()).toObjectEntity());
        }
    }
}
