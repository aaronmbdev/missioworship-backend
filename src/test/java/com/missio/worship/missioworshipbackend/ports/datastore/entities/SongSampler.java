package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import com.missio.worship.missioworshipbackend.libs.enums.SongRithm;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SongSampler {
    public static Song sample() {
        var sample = new Song();
        sample.setName("A song");
        sample.setArtist("An artist");
        sample.setActive(true);
        sample.setNotes("Notes!!!");
        sample.setRithm(SongRithm.RAPIDA);
        sample.setLastSunday(new Date());
        sample.setCreationDate(new Date());
        sample.setLinkToYoutube("https://youtube.com");
        return sample;
    }
}