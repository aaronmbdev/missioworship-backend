package com.missio.worship.missioworshipbackend.libs.users.errors;

public class EmailAlreadyRegisteredException extends Exception {
    public EmailAlreadyRegisteredException(String email) {
        super("El correo electrónico " + email + " ya existe en el sistema");
    }
}
