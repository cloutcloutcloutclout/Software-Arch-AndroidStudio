package uk.edu.le.co2124.part2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import uk.edu.le.co2124.part2.database.Defect;
import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SafetyCheckWithDefects checkWithDefects = (SafetyCheckWithDefects) getIntent().getSerializableExtra("Check", SafetyCheckWithDefects.class);

        TextView mRegistration = findViewById(R.id.textview_reg);
        mRegistration.setText(checkWithDefects.safetyCheck.vehicleRegistration);

        TextView mDriver = findViewById(R.id.textview_driver);
        mDriver.setText(checkWithDefects.safetyCheck.driverName);

        TextView mDate = findViewById(R.id.textview_date);
        mDate.setText(checkWithDefects.safetyCheck.date);

        TextView mStatus = findViewById(R.id.textview_status);
        mStatus.setText(checkWithDefects.safetyCheck.overallStatus.name());

        ArrayAdapter<String> arr;
        List<String> descriptions = checkWithDefects.defects.stream()
                .map(defect -> String.join("", defect.description != null ? defect.description : "No description", " - Severity: ",
                        defect.severity != null ? defect.severity.name() : "NONE") )
                .collect(Collectors.toList());
        arr = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                descriptions);
        ListView mList = findViewById(R.id.list);
        mList.setAdapter(arr);

        Button mReport = findViewById(R.id.email_report);

        mReport.setOnClickListener(item -> {
            Intent emailReport = new Intent(Intent.ACTION_SEND);
            emailReport.putExtra(Intent.EXTRA_SUBJECT, String.join("", "Safety Defect Report: ", checkWithDefects.safetyCheck.vehicleRegistration));
            emailReport.putExtra(Intent.EXTRA_TEXT, descriptions.toString().replace("[", "").replace("]", "").replace(", ", "\n"));
            emailReport.setType("message/rfc822");
            startActivity(Intent.createChooser(emailReport, "Choose an email client: "));
        });

    }
}
