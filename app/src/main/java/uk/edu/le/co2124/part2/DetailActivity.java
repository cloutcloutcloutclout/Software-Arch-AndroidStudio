package uk.edu.le.co2124.part2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SafetyCheckWithDefects checkWithDefects = (SafetyCheckWithDefects) getIntent().getSerializableExtra("Check", SafetyCheckWithDefects.class);

        TextView mRegistration = findViewById(R.id.textview_reg);
        mRegistration.setText(checkWithDefects.safetyCheck.vehicleRegistration);
    }
}
