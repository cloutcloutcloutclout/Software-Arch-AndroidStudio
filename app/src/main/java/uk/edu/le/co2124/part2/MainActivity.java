package uk.edu.le.co2124.part2;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Arrays;
import uk.edu.le.co2124.part2.database.Defect;
import uk.edu.le.co2124.part2.database.SafetyCheck;
import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;
import uk.edu.le.co2124.part2.database.SafetyRepository;

public class MainActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        populateChecks();

        RecyclerView recyclerView = findViewById(R.id.mRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SafetyAdapter adapter = new SafetyAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);
        viewModel.getAllChecksWithDefects().observe(this, adapter::setSafetyChecks);

        adapter.setOnDeleteClickListener(item -> {
            viewModel.deleteSafetyCheck(item.safetyCheck);
        });

        adapter.setOnItemClickListener(item -> {
            Intent detailIntent = new Intent(this, DetailActivity.class);
            detailIntent.putExtra("Check", item);
            startActivity(detailIntent);
        });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        if (fab != null) {
            fab.setOnClickListener(v -> {
                addCheckDialog();
            });
        }
    }

    // Function to populate the data with example data for testing purposes
    private void populateChecks() {
        SafetyRepository repository = new SafetyRepository(this.getApplication());

        SafetyCheck safe1 = new SafetyCheck("20/03/2024", "HU55 BFF", "James Driver", SafetyCheck.OverallStatus.PASS);
        SafetyCheck safe2 = new SafetyCheck("14/04/2024", "JF14 LLM", "Mark Car", SafetyCheck.OverallStatus.FAIL);

        Defect defect1 = new Defect();
        defect1.description = "Passenger Headlight Failure";
        defect1.severity = Defect.Severity.HIGH;

        Defect defect2 = new Defect();
        defect2.description = "Driver Side Coilover Worn";
        defect2.severity = Defect.Severity.LOW;

        Defect[] defects1 = {};

        Defect[] defects2 = {defect1, defect2};

        repository.insertSafetyCheck(safe1, Arrays.asList(defects1));

        repository.insertSafetyCheck(safe2, Arrays.asList(defects2));
    }
    public void addCheckDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = AddCheckDialog.newInstance();
        newFragment.show(ft, "dialog");
    }
}
