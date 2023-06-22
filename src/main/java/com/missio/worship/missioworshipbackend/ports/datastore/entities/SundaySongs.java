package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "sunday_songs")
public class SundaySongs {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sun_song_sequence")
    @SequenceGenerator(name = "sun_song_sequence", sequenceName = "sun_song_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "sunday")
    private Date sunday;

    @ManyToOne
    @JoinColumn(name = "song_id_first")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Song first_song;

    @ManyToOne
    @JoinColumn(name = "song_id_second")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Song second_song;

    @ManyToOne
    @JoinColumn(name = "song_id_third")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Song thrid_song;

    @ManyToOne
    @JoinColumn(name = "song_id_forth")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Song forth_song;
}
