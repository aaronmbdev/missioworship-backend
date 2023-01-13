package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import static org.junit.jupiter.api.Assertions.*;

public class RoleSampler {
    public static Role sample() {
        var role = new Role();
        role.setId(1);
        role.setUser_id(new User());
        role.setName("Admin");
        return role;
    }
}