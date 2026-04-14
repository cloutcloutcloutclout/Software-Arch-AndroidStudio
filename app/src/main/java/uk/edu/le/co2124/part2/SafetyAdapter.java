package uk.edu.le.co2124.part2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;

public class SafetyAdapter extends RecyclerView.Adapter<SafetyAdapter.SafetyViewHolder> {

    private List<SafetyCheckWithDefects> items = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SafetyCheckWithDefects item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SafetyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row, parent, false);
        return new SafetyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SafetyViewHolder holder, int position) {
        SafetyCheckWithDefects current = items.get(position);
        String summary = current.safetyCheck.date + " - " + current.safetyCheck.vehicleRegistration + 
                         " - " + (current.defects != null ? current.defects.size() : 0) + " Defects";
        holder.tvSummary.setText(summary);
        holder.tvStatus.setText("Status: " + current.safetyCheck.overallStatus.name());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setSafetyChecks(List<SafetyCheckWithDefects> checks) {
        this.items = checks;
        notifyDataSetChanged();
    }

    class SafetyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSummary, tvStatus;

        public SafetyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSummary = itemView.findViewById(R.id.tvSafetySummary);
            tvStatus = itemView.findViewById(R.id.tvSafetyStatus);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(items.get(getAdapterPosition()));
                }
            });
        }
    }
}
