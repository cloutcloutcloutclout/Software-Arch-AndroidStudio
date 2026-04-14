package uk.edu.le.co2124.part2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.mRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SafetyAdapter adapter = new SafetyAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);
        viewModel.getAllChecksWithDefects().observe(this, adapter::setSafetyChecks);

        adapter.setOnItemClickListener(safetyCheckWithDefects -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("CHECK_ID", safetyCheckWithDefects.safetyCheck.checkId);
            startActivity(intent);
        });

        // Button to go to "Add New Defect" screen for the rotation test requirement
        FloatingActionButton fab = findViewById(R.id.fabAdd);
        if (fab != null) {
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddDefectActivity.class);
                startActivity(intent);
            });
        }
    }
}
