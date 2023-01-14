package com.missio.worship.missioworshipbackend.libs.users.errors;

public class RoleAlreadyExistsException extends Exception {
    public RoleAlreadyExistsException(String name) {
        super("El rol " + name + " ya existe en el sistema.");
    }
}
