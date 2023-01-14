package com.missio.worship.missioworshipbackend.libs.users.errors;

public class UserNotFound extends Exception {
    public UserNotFound(Integer id) {
        super("El usuario con id '" + id + "' no existe en el sistema.");
    }
}
