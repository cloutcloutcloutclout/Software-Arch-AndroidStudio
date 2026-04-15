package uk.edu.le.co2124.part2.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "safety_checks")
public class SafetyCheck {
    @PrimaryKey(autoGenerate = true)
    public long checkId;
    public String date;
    public String vehicleRegistration;
    public String driverName;

    public enum OverallStatus { PASS, FAIL }
    public OverallStatus overallStatus;

    // Constructor isn't strictly necessary but extremely convenient
    public SafetyCheck(String date, String vehicleRegistration, String driverName, OverallStatus overallStatus) {
        this.date = date;
        this.vehicleRegistration = vehicleRegistration;
        this.driverName = driverName;
        this.overallStatus = overallStatus;
    }
}
