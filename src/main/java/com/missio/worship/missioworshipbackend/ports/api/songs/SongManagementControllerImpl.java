package com.missio.worship.missioworshipbackend.ports.api.songs;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.common.PaginationInput;
import com.missio.worship.missioworshipbackend.libs.common.SongPaginationInput;
import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import com.missio.worship.missioworshipbackend.libs.errors.ForbiddenResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.libs.songs.SongService;
import com.missio.worship.missioworshipbackend.libs.songs.errors.CouldNotCreateSongException;
import com.missio.worship.missioworshipbackend.libs.songs.errors.CouldNotUpdateSongException;
import com.missio.worship.missioworshipbackend.libs.songs.errors.SongDoesNotExistsException;
import com.missio.worship.missioworshipbackend.libs.users.errors.LessThanZeroException;
import com.missio.worship.missioworshipbackend.libs.users.errors.WrongOffsetValueException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("v1/songs/")
@RestController
@AllArgsConstructor
@Slf4j
public class SongManagementControllerImpl implements SongManagementController {

    private final SongService service;

    @Override
    public Mono<ResponseEntity<Object>> getRithms(String bearerToken) {
        return null;
        //Retornar listado de ritmos disponibles sin paginar.
    }

    @Override
    public Mono<ResponseEntity<Object>> getAllSongs(Integer limit,
                                                    Integer offset,
                                                    String dateFilter,
                                                    String activeFilter,
                                                    String bearerToken) {
        try {
            val input = new SongPaginationInput(limit, offset);
            val response = service.getAllSongsPaginated(input, bearerToken);
            return Mono.just(ResponseEntity.ok(response));
        } catch (LessThanZeroException | WrongOffsetValueException e) {
            return Mono.just(new BadRequestResponse(e.getMessage()).toObjectEntity());
        } catch (InvalidProvidedToken e) {
            return Mono.just(new UnauthorizedResponse(e.getMessage()).toObjectEntity());
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> getSong(Integer id, String bearerToken) {
        try {
            val song = service.getSongInformation(id, bearerToken);
            return Mono.just(ResponseEntity.ok(song));
        } catch (SongDoesNotExistsException e) {
            return Mono.just(new NotFoundResponse(e.getMessage()).toObjectEntity());
        } catch (InvalidProvidedToken e) {
            return Mono.just(new UnauthorizedResponse(e.getMessage()).toObjectEntity());
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> deleteSong(Integer id, String bearerToken) {
        try {
            service.deleteSongFromLib(id, bearerToken);
            return Mono.empty();
        } catch (InvalidProvidedToken e) {
            return Mono.just(new UnauthorizedResponse(e.getMessage()).toObjectEntity());
        } catch (NotAdminException e) {
            return Mono.just(new ForbiddenResponse(e.getMessage()).toObjectEntity());
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> updateSong(Integer id, SongInput song, String bearerToken) {
        try {
            val response = service.updateSong(id, song, bearerToken);
            return Mono.just(ResponseEntity.ok(response));
        } catch (InvalidProvidedToken e) {
            return Mono.just(new UnauthorizedResponse(e.getMessage()).toObjectEntity());
        } catch (CouldNotUpdateSongException e) {
            return Mono.just(new BadRequestResponse(e.getErrors()).toObjectEntity());
        } catch (SongDoesNotExistsException e) {
            return Mono.just(new NotFoundResponse(e.getMessage()).toObjectEntity());
        } catch (NotAdminException e) {
            return Mono.just(new ForbiddenResponse(e.getMessage()).toObjectEntity());
        }
    }

    @Override
    public Mono<ResponseEntity<Object>> createSong(SongInput song, String bearerToken) {
        try  {
            log.info("Se intenta crear una canción con los datos {}", song);
            var response = service.createSong(song, bearerToken);
            return Mono.just(ResponseEntity.ok(response));
        } catch (InvalidProvidedToken e) {
            return Mono.just(new UnauthorizedResponse(e.getMessage()).toObjectEntity());
        } catch (CouldNotCreateSongException e) {
            return Mono.just(new BadRequestResponse(e.getErrors()).toObjectEntity());
        } catch (NotAdminException e) {
            return Mono.just(new ForbiddenResponse(e.getMessage()).toObjectEntity());
        }
    }
}
