package in.neuw.ott.utils;

import com.fasterxml.uuid.Generators;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    private static final String PATTERN_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static String formatDate(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

        return formatter.format(instant);
    }

    public static String UUIDv7() {
        return Generators.timeBasedEpochGenerator().generate().toString();
    }

    public static String maskEmail(String email) {
        return email
                .replaceAll("(?<=.)[^.@](?=[^.@]*[^.@][@.])|(?<=@.)[^.@](?=[^.@]*[^.@][.])", "*")
                .replaceAll("\\*{3,}", "**");
    }

}
