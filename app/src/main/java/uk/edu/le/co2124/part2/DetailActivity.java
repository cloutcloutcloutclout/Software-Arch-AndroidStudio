package uk.edu.le.co2124.part2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import uk.edu.le.co2124.part2.database.Defect;
import uk.edu.le.co2124.part2.database.SafetyCheck;
import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;

public class DetailActivity extends AppCompatActivity {
    private int mStackLevel;
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
        mStatus.setTextColor(checkWithDefects.safetyCheck.overallStatus == SafetyCheck.OverallStatus.FAIL ? 0xFFFF0000 : 0xFF00FF00);

        ArrayAdapter<String> arr;
        List<String> descriptions = checkWithDefects.defects.stream()
                .map(defect -> String.join("", defect.description != null ? defect.description : "No description", " - Severity: ",
                        defect.severity != null ? defect.severity.name() : "NONE") )
                .collect(Collectors.toList());
        if (descriptions.isEmpty()) { descriptions.add("No Defects Listed"); }

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

        FloatingActionButton mAddDefect = findViewById(R.id.add_defect);
        mAddDefect.setOnClickListener(defect -> {
            addDefectDialog();
        });
    }
    public void addDefectDialog() {
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
        DialogFragment newFragment = AddDefectDialog.newInstance();
        newFragment.show(ft, "dialog");
    }

}
