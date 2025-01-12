package com.pi.clique_vagas_api.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateUtils {

    private static final ZoneId ZONE_ID = ZoneOffset.of("-03:00");

    public static ZonedDateTime nowInZone() {
        return Instant.now().atZone(ZONE_ID);
    }
}
