package com.example.capstone.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
    Car activeCar;
    EditText editCarTitle;
    String carTitle;
    String setDate;
    int setAlert;
    int carID;
    int vacationID;
    TextView editCarDate;
    DatePickerDialog.OnDateSetListener carDate;
    final Calendar myCalendarDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        Random r = new Random();
        setAlert = r.nextInt(9999);

        repository = new Repository(getApplication());
        carTitle = getIntent().getStringExtra("title");
        editCarTitle = findViewById(R.id.carTitle);
        editCarTitle.setText(carTitle);
        carID = getIntent().getIntExtra("carID", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        setDate = getIntent().getStringExtra("carDate");
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (setDate != null) {
            try {
                Date carDate = sdf.parse(setDate);
                myCalendarDate.setTime(carDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        editCarDate = findViewById(R.id.carDate);

        // Set click listener for rental date TextView to open date picker dialog
        editCarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editCarDate.getText().toString();
                if (info.equals("")) info = setDate;
                try {
                    myCalendarDate.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CarDetails.this, carDate, myCalendarDate
                        .get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH),
                        myCalendarDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Set listener for date picker dialog to update date when selected
        carDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarDate.set(Calendar.YEAR, year);
                myCalendarDate.set(Calendar.MONTH, month);
                myCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editCarDate.setText(sdf.format(myCalendarDate.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cardetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        // Save rental details
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

        // Delete the active rental
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

        // Set an alert for the rental
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

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        updateLabel();
    }
}