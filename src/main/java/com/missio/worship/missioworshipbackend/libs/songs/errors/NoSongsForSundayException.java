package com.missio.worship.missioworshipbackend.libs.songs.errors;
public class NoSongsForSundayException extends Exception {
    public NoSongsForSundayException(String msg) {
        super(msg);
    }
}
