package com.missio.worship.missioworshipbackend.libs.songs;

import com.missio.worship.missioworshipbackend.libs.authentication.MissioValidationResponse;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.common.PaginationInput;
import com.missio.worship.missioworshipbackend.libs.common.RestPaginationResponse;
import com.missio.worship.missioworshipbackend.libs.songs.errors.CouldNotCreateSongException;
import com.missio.worship.missioworshipbackend.libs.songs.errors.CouldNotUpdateSongException;
import com.missio.worship.missioworshipbackend.libs.songs.errors.SongDoesNotExistsException;
import com.missio.worship.missioworshipbackend.libs.users.errors.LessThanZeroException;
import com.missio.worship.missioworshipbackend.libs.users.errors.WrongOffsetValueException;
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

    public RestPaginationResponse<SongSlim> getAllSongsPaginated(final PaginationInput input, String bearerToken)
            throws InvalidProvidedToken {
        authorizationChecker.doTokenVerification(bearerToken);
        
        return null;
    }

    public void deleteSongFromLib(final Integer id, final String bearer)
            throws NotAdminException, InvalidProvidedToken {
        val decoded = authorizationChecker.doTokenVerification(bearer);
        if(!authorizationChecker.verifyTokenAndAdmin(decoded)) {
            throw new NotAdminException();
        }
        songRepository.deleteById(id);
    }

    public Song getSongInformation(final Integer id, final String bearerToken)
            throws InvalidProvidedToken, SongDoesNotExistsException {
        authorizationChecker.doTokenVerification(bearerToken);
        return songRepository.findById(id).orElseThrow(() -> new SongDoesNotExistsException(id));
    }

    public Song updateSong(final Integer id, final SongInput song, final String bearerToken)
            throws InvalidProvidedToken, NotAdminException, SongDoesNotExistsException, CouldNotUpdateSongException {
        val decoded = authorizationChecker.doTokenVerification(bearerToken);
        if (!authorizationChecker.verifyTokenAndAdmin(decoded)) {
            throw new NotAdminException();
        }
        val errors = validateSongInput(song, decoded);
        if (!errors.isEmpty()) {
            throw new CouldNotUpdateSongException(errors);
        }
        Song toBeUpdated = songRepository.findById(id).orElseThrow(() -> new SongDoesNotExistsException(id));
        if (!toBeUpdated.getName().equals(song.name())) toBeUpdated.setName(song.name());
        if (!toBeUpdated.getArtist().equals(song.artist())) toBeUpdated.setArtist(song.artist());
        if (!toBeUpdated.getRithm().equals(song.rithm())) toBeUpdated.setRithm(song.rithm());
        if (!toBeUpdated.getLinkToTrack().equals(song.linkToTrack())) {
            toBeUpdated.setLinkToTrack(song.linkToTrack());
        }
        if (!toBeUpdated.getLinkToYoutube().equals(song.linkToYoutube())) {
            toBeUpdated.setLinkToYoutube(song.linkToYoutube());
        }
        if (toBeUpdated.getLastSunday() == null || !toBeUpdated.getLastSunday().equals(song.lastSunday())) {
            toBeUpdated.setLastSunday(song.lastSunday());
        }
        if (toBeUpdated.getNotes() == null || !toBeUpdated.getNotes().equals(song.notes())) {
            toBeUpdated.setNotes(song.notes());
        }
        if (toBeUpdated.isActive() != song.active()) toBeUpdated.setActive(song.active());
        return songRepository.save(toBeUpdated);
    }

    public Song createSong(SongInput song, String bearerToken)
            throws InvalidProvidedToken, CouldNotCreateSongException, NotAdminException {
        val decoded = authorizationChecker.doTokenVerification(bearerToken);
        val errors = validateSongInput(song, decoded);
        if (!errors.isEmpty()) {
            throw new CouldNotCreateSongException(errors);
        }
        Song songCreate = new Song(song);
        songCreate = songRepository.save(songCreate);
        log.info("Created song: {}", songCreate);
        return songCreate;
    }

    private boolean inputContainsPrivateParams(final SongInput input) {
        return input.active() || input.lastSunday() != null || input.notes() != null;
    }

    private List<String> validateSongInput(final SongInput input, final MissioValidationResponse decoded) {
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
        if(inputContainsPrivateParams(input) && !authorizationChecker.verifyTokenAndAdmin(decoded)) {
            returnErrors.add("La canción contiene información que sólo puede ser introducida por un administrador.");
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
