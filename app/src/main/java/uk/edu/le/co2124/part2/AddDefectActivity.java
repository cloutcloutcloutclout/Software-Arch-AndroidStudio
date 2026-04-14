package uk.edu.le.co2124.part2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import uk.edu.le.co2124.part2.database.Defect;
import uk.edu.le.co2124.part2.database.SafetyCheck;

public class AddDefectActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;
    private EditText etReg, etDriver, etDesc;
    private RadioGroup rgSeverity;
    private Spinner spinnerDefects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.addDefect);
        }

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);
        etReg = findViewById(R.id.etVehicleReg);
        etDriver = findViewById(R.id.etDriverName);
        etDesc = findViewById(R.id.etDefectDescription);
        rgSeverity = findViewById(R.id.rgSeverity);
        spinnerDefects = findViewById(R.id.spinnerDefects);

        // Load saved state
        etReg.setText(viewModel.getCurrentVehicleReg());
        etDriver.setText(viewModel.getCurrentDriverName());
        etDesc.setText(viewModel.getCurrentDefectDescription());
        rgSeverity.check(viewModel.getCurrentSeverityId());
        spinnerDefects.setSelection(viewModel.getCurrentSpinnerPosition());

        // State saving for rotation test
        setupTextSync(etReg, s -> viewModel.setCurrentVehicleReg(s));
        setupTextSync(etDriver, s -> viewModel.setCurrentDriverName(s));
        setupTextSync(etDesc, s -> viewModel.setCurrentDefectDescription(s));
        
        rgSeverity.setOnCheckedChangeListener((g, id) -> viewModel.setCurrentSeverityId(id));
        
        spinnerDefects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setCurrentSpinnerPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        findViewById(R.id.btnSaveDefect).setOnClickListener(v -> saveCheck());
    }

    private void setupTextSync(EditText et, java.util.function.Consumer<String> action) {
        et.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) { action.accept(s.toString()); }
            public void afterTextChanged(Editable s) {}
        });
    }

    private void saveCheck() {
        String reg = etReg.getText().toString().trim();
        String drv = etDriver.getText().toString().trim();
        
        // Use spinner value if custom desc is empty
        String dsc = etDesc.getText().toString().trim();
        if (dsc.isEmpty()) {
            dsc = spinnerDefects.getSelectedItem().toString();
        }

        if (reg.isEmpty() || drv.isEmpty()) {
            Toast.makeText(this, "Please fill in Reg and Driver", Toast.LENGTH_SHORT).show();
            return;
        }

        SafetyCheck check = new SafetyCheck();
        check.date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        check.vehicleRegistration = reg;
        check.driverName = drv;
        check.overallStatus = SafetyCheck.OverallStatus.FAIL;

        Defect defect = new Defect();
        defect.description = dsc;
        defect.severity = (rgSeverity.getCheckedRadioButtonId() == R.id.rbLow) ? Defect.Severity.LOW : Defect.Severity.HIGH;

        ArrayList<Defect> defects = new ArrayList<>();
        defects.add(defect);

        viewModel.insertSafetyCheck(check, defects);
        clearState();
        finish();
    }

    private void clearState() {
        viewModel.setCurrentVehicleReg("");
        viewModel.setCurrentDriverName("");
        viewModel.setCurrentDefectDescription("");
        viewModel.setCurrentSeverityId(R.id.rbHigh);
        viewModel.setCurrentSpinnerPosition(0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
