package uk.edu.le.co2124.part2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

import uk.edu.le.co2124.part2.database.Defect;
import uk.edu.le.co2124.part2.database.SafetyCheck;
import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;
import uk.edu.le.co2124.part2.database.SafetyRepository;

public class AddCheckDialog extends DialogFragment {
    static AddCheckDialog newInstance() {
        AddCheckDialog f = new AddCheckDialog();

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_check, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SafetyRepository repository = new SafetyRepository(getActivity().getApplication());

        EditText mDriver = view.findViewById(R.id.check_driver);

        EditText mDate = view.findViewById(R.id.check_date);

        EditText mPlate = view.findViewById(R.id.check_plate);

        Button mSubmit = view.findViewById(R.id.check_submit);

        String[] array = {"Toast", "Machines"};
        AutoCompleteTextView mAutoView = view.findViewById(R.id.autocomplete_check);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, AddDefectDialog.getNames(SafetyCheck.OverallStatus.class));
        mAutoView.setAdapter(adapterItems);

        mSubmit.setOnClickListener(submit -> {



//            SafetyCheck check = new SafetyCheck();
        });

    }
}
