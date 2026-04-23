package uk.edu.le.co2124.part2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        AutoCompleteTextView mAutoView = view.findViewById(R.id.autocomplete_check);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, AddDefectDialog.getNames(SafetyCheck.OverallStatus.class));
        mAutoView.setAdapter(adapterItems);

        mSubmit.setOnClickListener(submit -> {
            String[] data = {mDriver.getText().toString(), mDate.getText().toString(), mPlate.getText().toString(), mAutoView.getText().toString()};

            List<Boolean> completed = Arrays.stream(data).map(String::isEmpty).collect(Collectors.toList());

            List<String> errors = Arrays.asList(new String[]{"No driver name", "No date", "No number plate", "No pass status"});

            List<String> result = IntStream.range(0, errors.size())
                    .mapToObj(i -> completed.get(i) ? errors.get(i) : "")
                    .collect(Collectors.toList());
            boolean allEmpty = result.stream().allMatch(String::isEmpty);

            String message = allEmpty ? "Check successfully added" : result.toString().replace("[","").replace("]", "");

            Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            if (allEmpty) {
                try {
                    SafetyCheck check = new SafetyCheck(data[1], data[2], data[0], SafetyCheck.OverallStatus.valueOf(data[3]));
                    repository.insertSafetyCheck(check, new ArrayList<Defect>());
                } catch (Exception e) {
                    Toast.makeText(this.getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
