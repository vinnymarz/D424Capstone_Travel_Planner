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
import com.example.capstone.entities.Car;

import java.util.List;

// Adapter for displaying a list of car rentals in a RecyclerView
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    // ViewHolder for individual car rental items
    class CarViewHolder extends RecyclerView.ViewHolder {

        private final TextView carItemView;

        // Constructor to initialize the ViewHolder and set click listener
        private CarViewHolder(View itemView) {
            super(itemView);
            carItemView = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Car current = mCars.get(position);
                    Intent intent = new Intent(context, CarDetails.class);
                    intent.putExtra("id", current.getCarID());
                    intent.putExtra("title", current.getCarTitle());
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("carDate", current.getCarDate());
                    context.startActivity(intent);
                }
            });
        }

    }

    private List<Car> mCars;
    private final Context context;
    private final LayoutInflater mInflater;

    // Constructor to initialize the adapter
    public CarAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //Creates a new ViewHolder instance
    @Override
    public CarAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.car_list_item, parent, false);
        return new CarAdapter.CarViewHolder(itemView);
    }

    // Binds data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull CarAdapter.CarViewHolder holder, int position) {
        if (mCars != null) {
            Car current = mCars.get(position);
            String carTitle = current.getCarTitle();
            int vacationID = current.getVacationID();
            holder.carItemView.setText(carTitle);
        } else {
            holder.carItemView.setText("No car title");
        }
    }

    //Sets the list of car rentals and notifies the adapter that the data has changed
    public void setmCars(List<Car> cars) {
        mCars = cars;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (mCars != null) {
            return mCars.size();
        } else {
            return 0;
        }
    }
}