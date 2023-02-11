package com.missio.worship.missioworshipbackend.libs.songs;

import com.missio.worship.missioworshipbackend.libs.enums.SongRithm;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Song;
import lombok.Data;


@Data
public class SongSlim {
    private Integer id;

    private String name;

    private String artist;

    private SongRithm rithm;

    private String linkToTrack;

    private String linkToYoutube;

    public SongSlim(Song fromSong) {
        this.id = fromSong.getId();
        this.artist = fromSong.getArtist();
        this.name = fromSong.getName();
        this.rithm = fromSong.getRithm();
        this.linkToTrack = fromSong.getLinkToTrack();
        this.linkToYoutube = fromSong.getLinkToYoutube();
    }
}
