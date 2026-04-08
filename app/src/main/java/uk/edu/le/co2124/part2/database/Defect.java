package uk.edu.le.co2124.part2.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "defects",
        foreignKeys = @ForeignKey(
                entity = SafetyCheck.class,
                parentColumns = "checkId",
                childColumns = "parentCheckId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("parentCheckId")})
public class Defect {
    @PrimaryKey(autoGenerate = true)
    public long defectId;
    public long parentCheckId;
    public String description;
    public String severity; // Low High
}