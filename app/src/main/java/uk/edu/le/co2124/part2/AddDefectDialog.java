package uk.edu.le.co2124.part2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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

        mSubmit.setOnClickListener(listen -> {
            Defect newDefect = new Defect();
            newDefect.description = mDescription.getText().toString();
            newDefect.severity = Defect.Severity.HIGH;
            newDefect.parentCheckId = mCheck.safetyCheck.checkId;
            try {
                repository.insertDefect(newDefect);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(view.getContext(), "Defect Added", Toast.LENGTH_SHORT).show();
        });

        // set DialogFragment title
        getDialog().setTitle("Dialog");
    }
}
