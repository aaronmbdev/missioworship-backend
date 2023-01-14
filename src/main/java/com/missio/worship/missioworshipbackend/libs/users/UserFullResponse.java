package com.missio.worship.missioworshipbackend.libs.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import lombok.*;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFullResponse {

    Integer id;

    String name;

    String email;

    List<String> roles;
}
