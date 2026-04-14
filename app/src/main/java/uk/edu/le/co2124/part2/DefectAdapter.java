package uk.edu.le.co2124.part2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.edu.le.co2124.part2.database.Defect;

public class DefectAdapter extends RecyclerView.Adapter<DefectAdapter.DefectViewHolder> {

    private List<Defect> defects = new ArrayList<>();

    @NonNull
    @Override
    public DefectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new DefectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectViewHolder holder, int position) {
        Defect current = defects.get(position);
        holder.text1.setText(current.description);
        holder.text2.setText("Severity: " + current.severity.name());
    }

    @Override
    public int getItemCount() {
        return defects.size();
    }

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
        notifyDataSetChanged();
    }

    static class DefectViewHolder extends RecyclerView.ViewHolder {
        private final TextView text1;
        private final TextView text2;

        public DefectViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}
