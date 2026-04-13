package uk.edu.le.co2124.part2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafetyRepository {

    private final SafetyCheckDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SafetyRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        dao = db.safetyCheckDao();
    }

    // Background threading

    public void insertSafetyCheck(SafetyCheck check, List<Defect> defects) {
        executor.execute(() -> {
            long newCheckId = dao.insertSafetyCheck(check);
            if (defects != null) {
                for (Defect defect : defects) {
                    defect.parentCheckId = newCheckId;
                    dao.insertDefect(defect);
                }
            }
        });
    }

    public void insertDefect(Defect defect) {
        executor.execute(() -> dao.insertDefect(defect));
    }

    public void updateSafetyCheck(SafetyCheck check) {
        executor.execute(() -> dao.updateSafetyCheck(check));
    }

    public void deleteSafetyCheck(SafetyCheck check) {
        executor.execute(() -> dao.deleteSafetyCheck(check));
    }

    public void deleteDefect(Defect defect) {
        executor.execute(() -> dao.deleteDefect(defect));
    }

    // LiveData + Room

    public LiveData<SafetyCheckWithDefects> getCheckWithDefects(long checkId) {
        return dao.getCheckWithDefects(checkId);
    }

    public LiveData<List<SafetyCheckWithDefects>> getAllChecksWithDefects() {
        return dao.getAllChecksWithDefects();
    }

    public LiveData<List<SafetyCheck>> getAllChecks() {
        return dao.getAllChecks();
    }
}