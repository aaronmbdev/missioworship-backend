package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voting")
public class Voting {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voting_sequence")
    @SequenceGenerator(name = "voting_sequence", sequenceName = "voting_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "beggining")
    @NonNull
    private Date beggining;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "end")
    @NonNull
    private Date end;


    @Column(nullable = false, name = "approvable_songs")
    @NonNull
    private Integer approvableSongs;


    @Column(nullable = false, name = "votes_per_person")
    @NonNull
    private Integer votesPerPerson;
}
