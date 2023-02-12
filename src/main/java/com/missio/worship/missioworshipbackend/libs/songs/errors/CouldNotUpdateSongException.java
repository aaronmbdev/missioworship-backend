package com.missio.worship.missioworshipbackend.libs.songs.errors;

import lombok.Getter;

import java.util.List;

@Getter
public class CouldNotUpdateSongException extends Exception {
    private final List<String> errors;
    public CouldNotUpdateSongException(final List<String> errors) {
        this.errors = errors;
    }
}
