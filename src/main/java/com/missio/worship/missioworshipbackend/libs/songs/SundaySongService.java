package com.missio.worship.missioworshipbackend.libs.songs;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.songs.errors.SongDoesNotExistsException;
import com.missio.worship.missioworshipbackend.libs.utils.DateUtils;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.libs.songs.errors.NoSongsForSundayException;
import com.missio.worship.missioworshipbackend.ports.api.songs.SongsInput;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Song;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.SundaySongs;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.SongRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.SundaySongsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class SundaySongService {

    private final AuthorizationChecker authorizationChecker;

    private final SundaySongsRepository songsRepository;

    private final SongRepository songRepository;

    public SundaySongs getSongsForDate(final Date date, final String token)
            throws InvalidProvidedToken, NoSongsForSundayException, NotAdminException {
        log.info("Consultando canciones para el día {}", date);
        var decoded = authorizationChecker.doTokenVerification(token);
        if (!authorizationChecker.verifyTokenAndAdmin(decoded)) {
            throw new NotAdminException();
        }
        var errMSg = String.format("No hay canciones definidas para la fecha %s", date);
        return songsRepository.findBySunday(date).orElseThrow(() -> new NoSongsForSundayException(errMSg));
    }

    public void putSongsForDate(final SongsInput input, final String token)
            throws InvalidProvidedToken, SongDoesNotExistsException, ParseException, NotAdminException {
        log.info("Insertando canciones para el domingo: '{}'", input);
        var decoded = authorizationChecker.doTokenVerification(token);
        if(!authorizationChecker.verifyTokenAndAdmin(decoded)) {
            throw new NotAdminException();
        }
        var formattedDate = DateUtils.parseFrom(input.date());
        var sundayExists = songsRepository.findBySunday(formattedDate);
        if(sundayExists.isPresent()) {
            updateSongsForDate(input, sundayExists.get());
        } else {
            createSongsForDate(input, formattedDate);
        }
    }

    public void deleteAllSongsForDate(final String date, final String token)
            throws InvalidProvidedToken, NotAdminException, ParseException {
        log.info("Eliminando todas las canciones para el domingo: '{}'", date);
        var decoded = authorizationChecker.doTokenVerification(token);
        if(!authorizationChecker.verifyTokenAndAdmin(decoded)) {
            throw new NotAdminException();
        }
        var formattedDate = DateUtils.parseFrom(date);
        var sundayExists = songsRepository.findBySunday(formattedDate);
        if(sundayExists.isPresent()) {
            songsRepository.deleteByDate(formattedDate);
        }
    }

    private void createSongsForDate(final SongsInput input, final Date date) throws SongDoesNotExistsException {
        var sundaySong = new SundaySongs();
        sundaySong.setSunday(date);
        updateSongsForDate(input, sundaySong);
    }

    private void updateSongsForDate(final SongsInput input, SundaySongs entity) throws SongDoesNotExistsException {
        entity.setFirst_song(getSongFromRepoOrDie(input.song_one()));
        entity.setSecond_song(getSongFromRepoOrDie(input.song_two()));
        entity.setThrid_song(getSongFromRepoOrDie(input.song_three()));
        entity.setForth_song(getSongFromRepoOrDie(input.song_four()));
        songsRepository.save(entity);
    }

    private Song getSongFromRepoOrDie(final Integer id) throws SongDoesNotExistsException {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongDoesNotExistsException(id));
    }
}
