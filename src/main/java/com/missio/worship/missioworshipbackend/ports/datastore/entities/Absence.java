package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "absence")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false, name = "absence_date")
    @NonNull
    private Date absenceDate;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
