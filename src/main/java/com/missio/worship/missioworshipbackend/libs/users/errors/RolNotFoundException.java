package com.missio.worship.missioworshipbackend.libs.users.errors;

public class RolNotFoundException extends RuntimeException {
    public RolNotFoundException(Integer id) {
        super("El rol con ID '" + id + "' no existe en el sistema");
    }
}
