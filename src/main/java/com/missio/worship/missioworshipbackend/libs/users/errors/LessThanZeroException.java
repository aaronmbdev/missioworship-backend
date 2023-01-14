package com.missio.worship.missioworshipbackend.libs.users.errors;

public class LessThanZeroException extends Exception {
    public LessThanZeroException() {
        super("El valor de limit y offset deben ser mayores o iguales a cero.");
    }
}
