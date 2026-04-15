package uk.edu.le.co2124.part2.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "defects",
        foreignKeys = @ForeignKey(
                entity = SafetyCheck.class,
                parentColumns = "checkId",
                childColumns = "parentCheckId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("parentCheckId")})
public class Defect implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long defectId;
    public long parentCheckId;
    public String description;


    // enum severity for 2 values
    public enum Severity { LOW, HIGH } // Low High
    public Severity severity;
}