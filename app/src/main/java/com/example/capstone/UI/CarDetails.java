package com.example.capstone.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstone.database.Repository;
import com.example.capstone.viewmodel.CarViewModelFactory;
import com.example.d424.capstone.R;
import com.example.capstone.entities.Car;
import com.example.capstone.viewmodel.CarViewModel;

import java.util.List;

public class CarDetails extends AppCompatActivity {
    private static final String TAG = "CarDetailsActivity";
    private CarViewModel carViewModel;
    private Spinner spinnerCars;
    private int vacationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        spinnerCars = findViewById(R.id.carSpinner);
        // Initialize the Repository
        Repository repository = new Repository(getApplication());
        // Create an instance of the ViewModelFactory
        CarViewModelFactory factory = new CarViewModelFactory(repository);
        // Use the factory to instantiate the CarViewModel
        carViewModel = new ViewModelProvider(this, factory).get(CarViewModel.class);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        Log.d(TAG, "onCreate: Received vacationID: " + vacationID);

        carViewModel.getAvailableCars(vacationID).observe(this, cars -> {
            Log.d(TAG, "Observing available cars: " + cars.size() + " cars received for vacationID: " + vacationID);
            ArrayAdapter<Car> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cars);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCars.setAdapter(adapter);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cardetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Log.d(TAG, "Home option selected, finishing activity.");
            finish();
            return true;
        } else if (id == R.id.carsave) {
            Car selectedCar = (Car) spinnerCars.getSelectedItem();
            if (selectedCar != null) {
                Log.d(TAG, "Saving car: " + selectedCar.toString() + " for vacationID: " + vacationID);
                selectedCar.setVacationID(vacationID);
                carViewModel.saveCar(selectedCar);
                finish();
            } else {
                Log.d(TAG, "No car selected to save.");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}