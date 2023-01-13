package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.util.Set;

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
    @Column(nullable = false, name = "email")
    @NonNull
    @Unique
    private String email;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    Set<UserRoles> roles;

}
