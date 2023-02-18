package com.missio.worship.missioworshipbackend.ports.datastore.entities;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RoleSampler {
    public static Role sample() {
        var role = new Role("Admin", 2);
        role.setId(1);
        return role;
    }
}