package uk.edu.le.co2124.part2.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

// Class implements Serializable so it can be put as an extra on line ~46 of MainActivity, shouldn't break anything
public class SafetyCheckWithDefects implements Serializable {
    @Embedded
    public SafetyCheck safetyCheck;

    @Relation(parentColumn = "checkId", entityColumn = "parentCheckId")
    public List<Defect> defects;
}
