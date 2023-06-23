package com.missio.worship.missioworshipbackend.libs.songs;

import com.missio.worship.missioworshipbackend.libs.authentication.MissioValidationResponseSampler;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.InvalidProvidedToken;
import com.missio.worship.missioworshipbackend.libs.authentication.errors.NotAdminException;
import com.missio.worship.missioworshipbackend.libs.songs.errors.NoSongsForSundayException;
import com.missio.worship.missioworshipbackend.ports.api.common.AuthorizationChecker;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.SundaySongsSampler;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.SongRepository;
import com.missio.worship.missioworshipbackend.ports.datastore.repositories.SundaySongsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SundaySongServiceTest {

    @Mock
    private AuthorizationChecker authorizationChecker;

    @Mock
    private SundaySongsRepository songsRepository;

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    SundaySongService service;

    @BeforeEach
    void setAuthorizationChecker() throws InvalidProvidedToken {
        when(authorizationChecker.doTokenVerification(any())).thenReturn(MissioValidationResponseSampler.sample());
        when(authorizationChecker.verifyTokenAndAdmin(any())).thenReturn(true);
    }

    @Test
    void getSundaySongsForExistingSunday() throws NoSongsForSundayException, InvalidProvidedToken, NotAdminException {
        var expected = SundaySongsSampler.sample();
        var date = new Date();
        when(songsRepository.findBySunday(any())).thenReturn(Optional.of(expected));
        var actual = service.getSongsForDate(date, "myToken");

        assertThat(actual).isEqualTo(expected);
        verify(songsRepository, times(1)).findBySunday(date);
    }

    @Test
    void getSundaySongsForNotExistingSunday() {
        var date = new Date();
        when(songsRepository.findBySunday(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getSongsForDate(date, "a token")).isInstanceOf(NoSongsForSundayException.class);
        verify(songsRepository, times(1)).findBySunday(date);
    }

}