package uk.edu.le.co2124.part2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import uk.edu.le.co2124.part2.database.SafetyCheck;

public class AddDefectDialog extends DialogFragment {
    public interface OnDefectAddedListener {
        void onDefectAdded(String desc, SafetyCheck.OverallStatus status);
    }

    private OnDefectAddedListener listener;

    public static AddDefectDialog newInstance() {
        return new AddDefectDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnDefectAddedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement OnDefectAddedListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO: Implement function
        return null;
    }

}
