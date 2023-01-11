package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import com.missio.worship.missioworshipbackend.libs.enums.SongRithm;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "trackLink")
    private String linkToTrack;

    @Column(nullable = false, name = "youtubeLink")
    @NonNull
    private String linkToYoutube;

    @Column(name = "lastSunday")
    private Date lastSunday;

    @Column(name = "notes")
    private String notes;

    @Column(nullable = false, name = "active")
    @NonNull
    private boolean active;
}
