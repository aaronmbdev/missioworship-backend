package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RoleSampler {
    public static Role sample() {
        var role = new Role();
        role.setId(1);
        role.setName("Admin");
        return role;
    }
}