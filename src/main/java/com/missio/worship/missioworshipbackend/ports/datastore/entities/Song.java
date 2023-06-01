package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import com.missio.worship.missioworshipbackend.libs.enums.SongRithm;
import com.missio.worship.missioworshipbackend.ports.api.songs.SongInput;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "songs")
public class Song {

    public Song(final SongInput input) {
        active = input.active();
        notes = input.notes();
        lastSunday = input.lastSunday();
        rithm = input.rithm();
        name = input.name();
        artist = input.artist();
        linkToYoutube = input.linkToYoutube();
        this.creationDate = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_sequence")
    @SequenceGenerator(name = "song_sequence", sequenceName = "song_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(nullable = false, name = "artist")
    @NonNull
    private String artist;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "rithm")
    @NonNull
    private SongRithm rithm;

    @Column(nullable = false, name = "youtubeLink")
    @NonNull
    private String linkToYoutube;

    @Column(name = "lastSunday")
    private Date lastSunday;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "notes")
    private String notes;

    @Column(nullable = false, name = "active")
    @NonNull
    private boolean active;

    public Song() {}
}
