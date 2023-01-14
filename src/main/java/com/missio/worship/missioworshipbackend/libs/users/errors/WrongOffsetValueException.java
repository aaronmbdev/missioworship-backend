package com.missio.worship.missioworshipbackend.libs.users.errors;

public class WrongOffsetValueException extends Exception {
    public WrongOffsetValueException() {
        super("El offset debe ser múltiplo del valor de limit");
    }
}
