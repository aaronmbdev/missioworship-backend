package com.missio.worship.missioworshipbackend.libs.users;

import com.missio.worship.missioworshipbackend.ports.datastore.entities.Role;
import lombok.*;
import org.springframework.data.util.Pair;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFullResponse {

    private Integer id;
    private String name;
    private String email;
    private List<Pair<Integer,String>> roles;
}
