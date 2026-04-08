package uk.edu.le.co2124.part2.database;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static SafetyCheck.OverallStatus toOverallStatus(String value) {
        return value == null ? null : SafetyCheck.OverallStatus.valueOf(value);
    }

    @TypeConverter
    public static String fromOverallStatus(SafetyCheck.OverallStatus status) {
        return status == null ? null : status.name();
    }

    @TypeConverter
    public static Defect.Severity toSeverity(String value) {
        return value == null ? null : Defect.Severity.valueOf(value);
    }

    @TypeConverter
    public static String fromSeverity(Defect.Severity severity) {
        return severity == null ? null : severity.name();
    }
}