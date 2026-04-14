package uk.edu.le.co2124.part2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uk.edu.le.co2124.part2.database.SafetyCheck;

public class DetailActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;
    private TextView tvDate, tvReg, tvDriver, tvStatus;
    private DefectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Safety Check Details");
        }

        tvDate = findViewById(R.id.tvDetailDate);
        tvReg = findViewById(R.id.tvDetailReg);
        tvDriver = findViewById(R.id.tvDetailDriver);
        tvStatus = findViewById(R.id.tvDetailStatus);

        RecyclerView recyclerView = findViewById(R.id.rvDefects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DefectAdapter();
        recyclerView.setAdapter(adapter);

        long checkId = getIntent().getLongExtra("CHECK_ID", -1);

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);
        if (checkId != -1) {
            viewModel.getCheckWithDefects(checkId).observe(this, checkWithDefects -> {
                if (checkWithDefects != null) {
                    SafetyCheck check = checkWithDefects.safetyCheck;
                    tvDate.setText("Date: " + check.date);
                    tvReg.setText("Vehicle Reg: " + check.vehicleRegistration);
                    tvDriver.setText("Driver: " + check.driverName);
                    tvStatus.setText("Status: " + check.overallStatus.name());
                    
                    adapter.setDefects(checkWithDefects.defects);
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
