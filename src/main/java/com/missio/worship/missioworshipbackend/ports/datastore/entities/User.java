package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, name = "name")
    @NonNull
    private String name;
    @Column(nullable = false, name = "email", unique = true)
    @NonNull
    private String email;

}
