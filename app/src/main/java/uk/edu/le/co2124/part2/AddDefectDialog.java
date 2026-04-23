package uk.edu.le.co2124.part2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

import uk.edu.le.co2124.part2.database.Defect;
import uk.edu.le.co2124.part2.database.SafetyCheck;
import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;
import uk.edu.le.co2124.part2.database.SafetyRepository;

public class AddDefectDialog extends DialogFragment {
    SafetyCheckWithDefects mCheck;
    // Create a new instance of MyDialogFragment
    static AddDefectDialog newInstance(SafetyCheckWithDefects check) {
        AddDefectDialog f = new AddDefectDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("safetyCheck", check);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCheck = (SafetyCheckWithDefects) getArguments().getSerializable("safetyCheck");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_defect, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SafetyRepository repository = new SafetyRepository(this.getActivity().getApplication());

        EditText mDescription = view.findViewById(R.id.defect_description);

        Button mSubmit = view.findViewById(R.id.defect_submit);

        AutoCompleteTextView mAutoView = view.findViewById(R.id.autocomplete_txt);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, getNames(Defect.Severity.class));
        mAutoView.setAdapter(adapterItems);


        mSubmit.setOnClickListener(listen -> {
            Defect newDefect = new Defect();
            String severity = mAutoView.getText().toString();

            if (!mDescription.getText().toString().isEmpty() || !severity.isEmpty()) {
                newDefect.description = mDescription.getText().toString();
                newDefect.severity = Defect.Severity.valueOf(severity);
                newDefect.parentCheckId = mCheck.safetyCheck.checkId;
            } else {
                Toast.makeText(view.getContext(), "Missing Description or Severity, try again.", Toast.LENGTH_LONG).show();
            }

            try {
                repository.insertDefect(newDefect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(view.getContext(), "Defect Added", Toast.LENGTH_SHORT).show();
        });

        // set DialogFragment title
        getDialog().setTitle("Add Defect");
    }
    // Because I care
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
