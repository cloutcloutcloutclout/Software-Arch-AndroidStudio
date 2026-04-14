package uk.edu.le.co2124.part2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.edu.le.co2124.part2.database.Defect;
import uk.edu.le.co2124.part2.database.SafetyCheck;

public class AddDefectActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;
    private EditText etVehicleReg, etDriverName, etDefectDescription;
    private RadioGroup rgSeverity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Safety Check");
        }

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);
        etVehicleReg = findViewById(R.id.etVehicleReg);
        etDriverName = findViewById(R.id.etDriverName);
        etDefectDescription = findViewById(R.id.etDefectDescription);
        rgSeverity = findViewById(R.id.rgSeverity);

        // Restore state from ViewModel
        etVehicleReg.setText(viewModel.getCurrentVehicleReg());
        etDriverName.setText(viewModel.getCurrentDriverName());
        etDefectDescription.setText(viewModel.getCurrentDefectDescription());
        rgSeverity.check(viewModel.getCurrentSeverityId());

        // Update ViewModel as user types to ensure state stability across rotation
        etVehicleReg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setCurrentVehicleReg(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        etDriverName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setCurrentDriverName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        etDefectDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setCurrentDefectDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        rgSeverity.setOnCheckedChangeListener((group, checkedId) -> {
            viewModel.setCurrentSeverityId(checkedId);
        });

        findViewById(R.id.btnSaveDefect).setOnClickListener(v -> {
            String vehicleReg = etVehicleReg.getText().toString().trim();
            String driverName = etDriverName.getText().toString().trim();
            String description = etDefectDescription.getText().toString().trim();
            
            if (vehicleReg.isEmpty() || driverName.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new Safety Check to hold this defect
            SafetyCheck newCheck = new SafetyCheck();
            newCheck.date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            newCheck.vehicleRegistration = vehicleReg;
            newCheck.driverName = driverName;
            newCheck.overallStatus = SafetyCheck.OverallStatus.FAIL;

            // Create the Defect
            Defect defect = new Defect();
            defect.description = description;
            
            int selectedId = rgSeverity.getCheckedRadioButtonId();
            if (selectedId == R.id.rbLow) {
                defect.severity = Defect.Severity.LOW;
            } else {
                defect.severity = Defect.Severity.HIGH;
            }

            List<Defect> defects = new ArrayList<>();
            defects.add(defect);

            // Save to database via ViewModel
            viewModel.insertSafetyCheck(newCheck, defects);
            
            // Clear ViewModel state
            viewModel.setCurrentVehicleReg("");
            viewModel.setCurrentDriverName("");
            viewModel.setCurrentDefectDescription("");
            viewModel.setCurrentSeverityId(R.id.rbHigh);
            
            Toast.makeText(this, "Safety Check Saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
