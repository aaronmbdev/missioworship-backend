package com.missio.worship.missioworshipbackend.libs.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import lombok.*;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserFullResponse {

    Integer id;

    String name;

    String email;

    List<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFullResponse that = (UserFullResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
