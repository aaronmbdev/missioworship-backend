package com.missio.worship.missioworshipbackend.libs.songs.errors;

public class InvalidDateFilterException extends Exception {
    public InvalidDateFilterException(String value) {
        super("El filtro de fecha tiene un valor inválido. Solo puede ser created o played. Se recibió " + value);
    }
}
