package uk.edu.le.co2124.part2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.edu.le.co2124.part2.database.SafetyCheck;
import uk.edu.le.co2124.part2.database.SafetyRepository;

public class SafetyViewModel extends AndroidViewModel {

    private SafetyRepository repository;
    private LiveData<List<SafetyCheck>> allChecks;

    public SafetyViewModel(@NonNull Application application) {
        super(application);
        repository = new SafetyRepository(application);
        allChecks = repository.getAllChecks();
    }

    public LiveData<List<SafetyCheck>> getAllChecks() {
        return allChecks;
    }
}
