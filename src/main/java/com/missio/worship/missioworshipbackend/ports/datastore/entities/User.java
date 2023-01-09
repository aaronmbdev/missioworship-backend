package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, name = "name")
    @NonNull
    private String name;
    @Column(nullable = false, name = "email")
    @NonNull
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user_id")
    @ToString.Exclude
    private Set<Role> roles;
}
