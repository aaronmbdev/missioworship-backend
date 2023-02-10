package com.missio.worship.missioworshipbackend.libs.songs.errors;

import lombok.Getter;

import java.util.List;

@Getter
public class CouldNotCreateSongException extends Exception {
    private final List<String> errors;
    public CouldNotCreateSongException(final List<String> errors) {
        this.errors = errors;
    }
}
