package uk.edu.le.co2124.part2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SafetyCheckWithDefects a = (SafetyCheckWithDefects) getIntent().getSerializableExtra("Check", SafetyCheckWithDefects.class);
    }
}
