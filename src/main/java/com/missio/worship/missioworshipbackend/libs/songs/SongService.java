package com.missio.worship.missioworshipbackend.libs.songs;

import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.songs.errors.CouldNotCreateSongException;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.api.songs.SongInput;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Song;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.SongRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SongService {

    private final AuthorizationChecker authorizationChecker;

    private final SongRepository songRepository;

    public Song createSong(SongInput song, String bearerToken) throws InvalidProvidedToken, CouldNotCreateSongException {
        val decoded = authorizationChecker.doTokenVerification(bearerToken);
        val errors = validateSongInput(song);
        if (!errors.isEmpty()) {
            throw new CouldNotCreateSongException(errors);
        }
        if(inputContainsPrivateParams(song)) {
            authorizationChecker.verifyTokenAndAdmin(decoded);
        }
        Song songCreate = new Song(song);
        songCreate = songRepository.save(songCreate);
        log.info("Created song: {}", songCreate);
        return songCreate;
    }

    private boolean inputContainsPrivateParams(final SongInput input) {
        return input.active() || input.lastSunday() != null || input.notes() != null;
    }

    private List<String> validateSongInput(final SongInput input) {
        List<String> returnErrors = new LinkedList<>();
        if(!validLinks(input.linkToTrack())) {
            returnErrors.add("El link al track no es un enlace válido");
        }
        if(!validLinks(input.linkToYoutube())) {
            returnErrors.add("El link al video no es un enlace válido");
        }
        if(songRepository.existsByLinkToTrack(input.linkToTrack())) {
            returnErrors.add("Ya existe una canción con el track: " + input.linkToTrack());
        }
        if(songRepository.existsByLinkToYoutube(input.linkToYoutube())) {
            returnErrors.add("Ya existe una canción con el link: " + input.linkToYoutube());
        }
        return returnErrors;
    }

    private boolean validLinks(String input) {
        try {
            new URL(input).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException e) {
            log.info("El link {} no es válido", input);
            return false;
        }
    }
}
