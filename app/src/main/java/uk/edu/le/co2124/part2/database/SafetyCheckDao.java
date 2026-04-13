package uk.edu.le.co2124.part2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SafetyCheckDao {

    // --- Insert ---
    @Insert
    long insertSafetyCheck(SafetyCheck safetyCheck);

    @Insert
    void insertDefect(Defect defect);

    // Checks with defects
    @Transaction
    @Query("SELECT * FROM safety_checks WHERE checkId = :id")
    LiveData<SafetyCheckWithDefects> getCheckWithDefects(long id);

    // All checks
    @Transaction
    @Query("SELECT * FROM safety_checks ORDER BY checkId DESC")
    LiveData<List<SafetyCheckWithDefects>> getAllChecksWithDefects();

    // Update and deleting check
    @Update
    void updateSafetyCheck(SafetyCheck safetyCheck);

    @Delete
    void deleteSafetyCheck(SafetyCheck safetyCheck);

    @Delete
    void deleteDefect(Defect defect);

    @Query("SELECT * FROM safety_checks ORDER BY checkId DESC")
    LiveData<List<SafetyCheck>> getAllChecks();
}