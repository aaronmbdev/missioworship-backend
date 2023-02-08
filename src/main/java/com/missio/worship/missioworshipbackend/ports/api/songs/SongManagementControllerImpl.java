package com.missio.worship.missioworshipbackend.ports.api.songs;
import com.missio.worship.missioworshipbackend.libs.songs.SongService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<Object>> getAllSongs(Integer limit, Integer offset, String bearerToken) {
        return null;
        //Obtener listado de canciones registradas de forma paginada.
    }

    @Override
    public Mono<ResponseEntity<Object>> getSong(Integer id, String bearerToken) {
        return null;
        //Obtener información no privada de una cancion
    }

    @Override
    public Mono<ResponseEntity<Object>> deleteSong(Integer id, String bearerToken) {
        return null;
        //Solo se puede borrar si soy admin
    }

    @Override
    public Mono<ResponseEntity<Object>> updateSong(Integer id, SongInput song, String bearerToken) {
        return null;
        //Solo puedo actualizar si soy admin
    }

    @Override
    public Mono<ResponseEntity<Object>> createSong(SongInput song, String bearerToken) {
        return null;
        //Verificar solamente atributos obligatorios. Si los opcionales no son nulos
        //verificar que soy administrador.
    }
}
