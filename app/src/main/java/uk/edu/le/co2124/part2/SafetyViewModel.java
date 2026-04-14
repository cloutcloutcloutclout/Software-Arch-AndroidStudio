package uk.edu.le.co2124.part2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.edu.le.co2124.part2.database.Defect;
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

    public LiveData<SafetyCheckWithDefects> getCheckWithDefects(long id) {
        return repository.getCheckWithDefects(id);
    }

    public void insertSafetyCheck(SafetyCheck check, List<Defect> defects) {
        repository.insertSafetyCheck(check, defects);
    }
    
    // For "Add New Defect" screen state stability
    private String currentVehicleReg = "";
    private String currentDriverName = "";
    private String currentDefectDescription = "";
    private int currentSeverityId = R.id.rbHigh;

    public String getCurrentVehicleReg() { return currentVehicleReg; }
    public void setCurrentVehicleReg(String vehicleReg) { this.currentVehicleReg = vehicleReg; }

    public String getCurrentDriverName() { return currentDriverName; }
    public void setCurrentDriverName(String driverName) { this.currentDriverName = driverName; }

    public String getCurrentDefectDescription() { return currentDefectDescription; }
    public void setCurrentDefectDescription(String description) { this.currentDefectDescription = description; }

    public int getCurrentSeverityId() { return currentSeverityId; }
    public void setCurrentSeverityId(int severityId) { this.currentSeverityId = severityId; }

}
