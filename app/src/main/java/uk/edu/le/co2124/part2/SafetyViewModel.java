package uk.edu.le.co2124.part2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.edu.le.co2124.part2.database.SafetyCheck;
import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;
import uk.edu.le.co2124.part2.database.SafetyRepository;

public class SafetyViewModel extends AndroidViewModel {

    private final SafetyRepository repository;
    private final LiveData<List<SafetyCheckWithDefects>> allChecksWithDefects;

    public SafetyViewModel(@NonNull Application application) {
        super(application);
        repository = new SafetyRepository(application);
        allChecksWithDefects = repository.getAllChecksWithDefects();
    }

    public LiveData<List<SafetyCheckWithDefects>> getAllChecksWithDefects() {
        return allChecksWithDefects;
    }

    public void deleteSafetyCheck(SafetyCheck check) {
        repository.deleteSafetyCheck(check);
    }
}
