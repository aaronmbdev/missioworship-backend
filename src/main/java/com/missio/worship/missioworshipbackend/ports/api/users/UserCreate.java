package com.missio.worship.missioworshipbackend.ports.api.users;

import java.util.List;

public record UserCreate(String name, String email, List<Integer> roles) {
}
