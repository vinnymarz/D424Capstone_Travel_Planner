package com.example.capstone.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.entities.Excursion;
import com.example.d424.capstone.R;
import com.example.capstone.database.Repository;
import com.example.capstone.entities.Car;
import com.example.capstone.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CarDetails extends AppCompatActivity {

    private Repository repository;
    private Spinner carSpinner;
    private List<Car> availableCars;
    private int vacationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        repository = new Repository(getApplication());
        carSpinner = findViewById(R.id.carSpinner);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        Log.d("CarDetails", "Received vacationID: " + vacationID);

        // Fetch available cars from your repository
        availableCars = repository.getAvailableCars(vacationID);

        // Set up Spinner adapter
        ArrayAdapter<Car> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableCars);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carSpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    /*
    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editCarDate.setText(sdf.format(myCalendarDate.getTime()));
    }

     */

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cardetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.carsave) {
            Car selectedCar = (Car) carSpinner.getSelectedItem();
            if (selectedCar != null) {
                // Associate the selected car with the vacation
                selectedCar.setVacationID(vacationID);

                // Check if the car is already associated with a vacation
                if (repository.getCarById(selectedCar.getCarID()) != null) {
                    repository.update(selectedCar); // Update if already associated
                } else {
                    repository.insert(selectedCar); // Insert if it's a new association
                }

                finish();
            }
            return true; // Indicate that the save item was handled
        }

        /* Save rental details
        if (item.getItemId() == R.id.carsave) {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String carDateString = sdf.format(myCalendarDate.getTime());
            Vacation vacation = null;
            List<Vacation> vacations = repository.getmAllVacations();
            for (Vacation vac : vacations) {
                if (vac.getVacationId() == vacationID) {
                    vacation = vac;
                }
            }
            try {
                Date carDate = sdf.parse(carDateString);
                Date startDate = sdf.parse(vacation.getStartDate());
                Date endDate = sdf.parse(vacation.getEndDate());
                assert carDate != null;
                if (carDate.before(startDate) || carDate.after(endDate)) {
                    Toast.makeText(this, "Car rental date must occur between vacation start and end dates.", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    Car car;
                    if (carID == -1) {
                        if (repository.getmAllCars().size() == 0) carID = 1;
                        else
                            carID = repository.getmAllCars().get(repository.getmAllCars().size() - 1).getCarID() + 1;
                        car = new Car(carID, editCarTitle.getText().toString(), vacationID, carDateString);
                        repository.insert(car);
                        this.finish();
                    } else {
                        car = new Car(carID, editCarTitle.getText().toString(), vacationID, carDateString);
                        repository.update(car);
                        this.finish();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }
        */

        /* Delete the active rental
        if (item.getItemId() == R.id.cardelete) {
            for (Car car : repository.getmAllCars()) {
                if (car.getCarID() == carID) {
                    activeCar = car;
                    break; // Exit the loop once a match is found
                }
            }

            if (activeCar != null) { // Check if activeCar was assigned a value
                repository.delete(activeCar);
                Toast.makeText(CarDetails.this, activeCar.getCarTitle() + " rental was deleted", Toast.LENGTH_LONG).show();
                CarDetails.this.finish();
            } else {
                // Handle the case where no matching car was found (e.g., display an error message)
                Toast.makeText(CarDetails.this, "Car rental not found", Toast.LENGTH_SHORT).show();
            }
        }
        */

        /* Set an alert for the rental
        if (item.getItemId() == R.id.caralert) {
            String dateFromScreen = editCarDate.getText().toString();
            String alert = "Your " + carTitle + " rental is today";

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(CarDetails.this, MyReceiver.class);
            intent.putExtra("key", alert);
            PendingIntent sender = PendingIntent.getBroadcast(CarDetails.this, setAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //updateLabel();
    }
}