package com.example.capstone.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424.capstone.R;
import com.example.capstone.entities.Excursion;

import java.util.List;

// Adapter for displaying a list of excursions in a RecyclerView
public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    // ViewHolder for individual excursion items
    class ExcursionViewHolder extends RecyclerView.ViewHolder {

        private final TextView excursionItemView;

        // Constructor to initialize the ViewHolder and set click listener
        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("title", current.getExcursionTitle());
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("excursionDate", current.getExcursionDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    // Constructor to initialize the adapter
    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Creates a new ViewHolder instance
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    // Binds data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursion current = mExcursions.get(position);
            String title = current.getExcursionTitle();
            int vacationID = current.getVacationID();
            holder.excursionItemView.setText(title);
        } else {
            holder.excursionItemView.setText("No excursion title");
        }
    }

    // Sets the list of excursions and notifies the adapter of data changes
    public void setmExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();
        } else {
            return 0;
        }
    }
}
