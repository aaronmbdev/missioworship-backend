package com.missio.worship.missioworshipbackend.libs.songs.errors;

public class SongDoesNotExistsException extends Exception {
    public SongDoesNotExistsException(final Integer id) {
        super("La canción con id " + id + " no existe en el sistema.");
    }
}
