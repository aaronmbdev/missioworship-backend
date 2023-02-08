package com.missio.worship.missioworshipbackend.ports.api.songs;

import com.missio.worship.missioworshipbackend.libs.enums.SongRithm;

import java.util.Date;

public record SongInput(String name,
                        String artist,
                        SongRithm rithm,
                        String linkToTrack,
                        String linkToYoutube,
                        Date lastSunday,
                        String notes,
                        Boolean active) {
}
