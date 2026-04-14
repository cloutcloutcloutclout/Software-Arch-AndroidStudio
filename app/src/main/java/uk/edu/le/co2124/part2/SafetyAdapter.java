package uk.edu.le.co2124.part2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.edu.le.co2124.part2.database.SafetyCheck;
import uk.edu.le.co2124.part2.database.SafetyCheckWithDefects;

public class SafetyAdapter extends RecyclerView.Adapter<SafetyAdapter.SafetyViewHolder> {

    private List<SafetyCheckWithDefects> safetyChecks = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(SafetyCheckWithDefects safetyCheckWithDefects);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SafetyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_row, parent, false);
        return new SafetyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SafetyViewHolder holder, int position) {
        SafetyCheckWithDefects current = safetyChecks.get(position);
        String summary = String.format("%s - %s - %d Defects",
                current.safetyCheck.date,
                current.safetyCheck.vehicleRegistration,
                current.defects != null ? current.defects.size() : 0);
        holder.tvSafetySummary.setText(summary);
        holder.tvSafetyStatus.setText("Status: " + current.safetyCheck.overallStatus.name());

        if (current.safetyCheck.overallStatus == SafetyCheck.OverallStatus.FAIL) {
            holder.ivStatusIcon.setImageResource(R.drawable.outline_car_gear_24);
        } else {
            holder.ivStatusIcon.setImageResource(R.drawable.outline_directions_car_24);
        }
    }

    @Override
    public int getItemCount() {
        return safetyChecks.size();
    }

    public void setSafetyChecks(List<SafetyCheckWithDefects> safetyChecks) {
        this.safetyChecks = safetyChecks;
        notifyDataSetChanged();
    }

    class SafetyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSafetySummary;
        private final TextView tvSafetyStatus;
        private final ImageView ivStatusIcon;

        public SafetyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSafetySummary = itemView.findViewById(R.id.tvSafetySummary);
            tvSafetyStatus = itemView.findViewById(R.id.tvSafetyStatus);
            ivStatusIcon = itemView.findViewById(R.id.ivStatusIcon);



            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(safetyChecks.get(position));
                }
            });
        }
    }
}
