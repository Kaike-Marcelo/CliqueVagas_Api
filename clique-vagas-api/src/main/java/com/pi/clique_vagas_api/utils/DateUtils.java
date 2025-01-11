package com.pi.clique_vagas_api.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtils {

    private static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    public static ZonedDateTime nowInZone() {
        return Instant.now().atZone(ZONE_ID);
    }
}

