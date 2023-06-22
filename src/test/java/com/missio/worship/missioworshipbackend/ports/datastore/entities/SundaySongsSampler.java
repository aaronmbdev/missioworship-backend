package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SundaySongsSampler {
    public static SundaySongs sample() {
        var sample = new SundaySongs();
        sample.setId(1);
        sample.setSunday(new Date());
        sample.setForth_song(SongSampler.sample());
        sample.setThrid_song(SongSampler.sample());
        sample.setSecond_song(SongSampler.sample());
        sample.setFirst_song(SongSampler.sample());
        return sample;
    }
}