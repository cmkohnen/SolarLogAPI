package TransformUtilities.DataConversion;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

public class GetStartOf {
    public static Date day(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date day(Date date) {
        return Date.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static YearMonth yearMonth(Date date) {
        return YearMonth.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static YearMonth yearMonth(LocalDate date) {
        return YearMonth.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
