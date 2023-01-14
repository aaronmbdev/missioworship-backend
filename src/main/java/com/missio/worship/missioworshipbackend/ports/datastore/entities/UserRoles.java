package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Role role;

    public UserRoles(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
