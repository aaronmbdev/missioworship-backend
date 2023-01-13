package com.missio.worship.missioworshipbackend.ports.datastore.entities;
import java.util.Set;

public class UserSampler {
    public static User sample() {
        var user = new User();
        user.setId(1);
        user.setEmail("myEmail@email.com");
        user.setName("Dummy user");
        user.setRoles(Set.of(RoleSampler.sample(), RoleSampler.sample()));
        return user;
    }
}