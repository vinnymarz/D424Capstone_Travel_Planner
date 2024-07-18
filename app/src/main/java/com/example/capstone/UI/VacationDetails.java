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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424.capstone.R;
import com.example.capstone.database.Repository;
import com.example.capstone.entities.Excursion;
import com.example.capstone.entities.Vacation;
import com.example.capstone.entities.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.util.Random;


public class VacationDetails extends AppCompatActivity {

    Repository repository;
    Vacation currentVacation;
    EditText editTitle;
    EditText editHotel;
    TextView editStartDate;
    TextView editEndDate;
    String title;
    String vacationHotel;
    String setStartDate;
    String setEndDate;
    int vacationID;
    int totalExcursions;
    Random r = new Random();
    int setAlert = r.nextInt(9999);
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    List<Excursion> selectedExcursions = new ArrayList<>();
    List<Car> selectedCars = new ArrayList<>();
    boolean clicked;

    Animation rotateOpen;
    Animation rotateClose;
    Animation fromBottom;
    Animation toBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);


        // Initialize repository and UI elements
        repository = new Repository(getApplication());
        editTitle = findViewById(R.id.titletext);
        editHotel = findViewById(R.id.hoteltext);
        vacationID = getIntent().getIntExtra("id", -1);
        vacationHotel = getIntent().getStringExtra("hotel");
        title = getIntent().getStringExtra("title");
        setStartDate = getIntent().getStringExtra("startdate");
        setEndDate = getIntent().getStringExtra("enddate");
        editTitle.setText(title);
        editHotel.setText(vacationHotel);
        setAlert = r.nextInt(9999);
        clicked = false;
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);


        // Set up FAB to open Excursion and Rental car buttons
        FloatingActionButton fabOpenOptions = findViewById(R.id.add_btn);
        fabOpenOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onAddButtonClicked();
            }
        });

        // Set up FAB to open Excursion Details activity
        FloatingActionButton fabOpenExcursionDetails = findViewById(R.id.activity_btn);
        fabOpenExcursionDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExcursionDetails(vacationID);
            }
        });

        // Set up FAB to open Car Rental activity
        FloatingActionButton fabOpenCarRental = findViewById(R.id.car_btn);
        fabOpenCarRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openCarDetails(vacationID); }
        });


        // Initialize RecyclerView for excursions
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate selectedExcursions list with excursions related to this vacation
        for (Excursion e : repository.getmAllExcursions()) {
            if (e.getVacationID() == vacationID) selectedExcursions.add(e);
        }
        excursionAdapter.setmExcursions(selectedExcursions);


        // Initialize Recycler view for cars
        RecyclerView recyclerView2 = findViewById(R.id.carrecyclerview);
        repository = new Repository(getApplication());
        final CarAdapter carAdapter = new CarAdapter(this);
        recyclerView2.setAdapter(carAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        // Populate selectedCars list with cars related to this vacation
        for (Car c : repository.getmAllCars()) {
            if (c.getVacationID() == vacationID) selectedCars.add(c);
        }
        carAdapter.setmCars(selectedCars);


        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (setStartDate != null) {
            try {
                Date startDate = sdf.parse(setStartDate);
                Date endDate = sdf.parse(setEndDate);
                myCalendarStart.setTime(startDate);
                myCalendarEnd.setTime(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        editStartDate = findViewById(R.id.startDate);
        editEndDate = findViewById(R.id.endDate);

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editStartDate.getText().toString();
                if (info.isEmpty()) info = setStartDate;
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = setEndDate;
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.vacationsave) {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String startDateString = sdf.format(myCalendarStart.getTime());
            String endDateString = sdf.format(myCalendarEnd.getTime());
            try {
                Date startDate = sdf.parse(startDateString);
                Date endDate = sdf.parse(endDateString);
                assert endDate != null;
                if (endDate.before(startDate)) {
                    Toast.makeText(this, "End date cannot be before start date", Toast.LENGTH_LONG).show();
                } else {
                    Vacation vacation;
                    if (vacationID == -1) {
                        if (repository.getmAllVacations().isEmpty()) vacationID = 1;
                        else
                            vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationId() + 1;
                        vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateString, endDateString);
                        repository.insert(vacation);
                        this.finish();
                    } else {
                        vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateString, endDateString);
                        repository.update(vacation);
                        this.finish();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (item.getItemId() == R.id.vacationdelete) {
            for (Vacation vac : repository.getmAllVacations()) {
                if (vac.getVacationId() == vacationID) currentVacation = vac;
            }
            totalExcursions = 0;
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++totalExcursions;
            }
            if (totalExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " has been deleted", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Vacations with excursions cannot be deleted", Toast.LENGTH_LONG).show();
            }
        }
        if (item.getItemId() == R.id.alertstart) {
            String dateFromScreen = editStartDate.getText().toString();
            String alert = "It's time for your vacation to " + title + "!";
            alertPicker(dateFromScreen, alert);
            return true;
        }

        if (item.getItemId() == R.id.alertend) {
            String dateFromScreen = editEndDate.getText().toString();
            String alert = "Your vacation in " + title + " is over.";
            alertPicker(dateFromScreen, alert);
            return true;
        }

        if (item.getItemId() == R.id.share) {
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            sentIntent.putExtra(Intent.EXTRA_TITLE, "Vacation Shared");
            StringBuilder shareData = new StringBuilder();
            shareData.append("Vacation Destination: " + editTitle.getText().toString() + "\n");
            shareData.append("Hotel Name: " + editHotel.getText().toString() + "\n");
            shareData.append("Start Date: " + editStartDate.getText().toString() + "\n");
            shareData.append("End Date: " + editEndDate.getText().toString() + "\n");
            for (int i = 0; i < selectedExcursions.size(); i++) {
                shareData.append("Excursion " + (i + 1) + ": " + selectedExcursions.get(i).getExcursionTitle() + "\n");
                shareData.append("Excursion " + (i + 1) + "Date: " + selectedExcursions.get(i).getExcursionDate() + "\n");
            }
            for (int i = 0; i < selectedCars.size(); i++) {
                shareData.append("Car " + (i + 1) + ": " + selectedCars.get(i).getCarTitle() + "\n");
                shareData.append("Car " + (i + 1) + "Date: " + selectedCars.get(i).getCarDate() + "\n");
            }

            sentIntent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }
        return true;
    }

    public void alertPicker(String dateFromScreen, String alert) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
        intent.putExtra("key", alert);
        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, setAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }

    // To - DO: Fix RecyclerView for Cars
    @Override
    protected void onResume() {
        super.onResume();

        // Excursion RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> selectedExcursions = new ArrayList<>();
        for (Excursion e : repository.getmAllExcursions()) {
            if (e.getVacationID() == vacationID) selectedExcursions.add(e);
        }
        excursionAdapter.setmExcursions(selectedExcursions);

        // Car RecyclerView setup
        RecyclerView carRecyclerView = findViewById(R.id.carrecyclerview);
        final CarAdapter carAdapter = new CarAdapter(this);
        carRecyclerView.setAdapter(carAdapter);
        carRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Car> selectedCars = new ArrayList<>();
        for (Car c : repository.getmAllCars()) {
            if (c.getVacationID() == vacationID) selectedCars.add(c);
        }
        carAdapter.setmCars(selectedCars);

        updateLabelStart();
        updateLabelEnd();

    }

    private void onAddButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;
    }

    // Makes invisible buttons visible
    private void setVisibility(boolean clicked) {
        if (!clicked) {
            findViewById(R.id.activity_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.car_btn).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.activity_btn).setVisibility(View.INVISIBLE);
            findViewById(R.id.car_btn).setVisibility(View.INVISIBLE);
        }
    }

    // Set button animation
    private void setAnimation(boolean clicked) {
        if (!clicked) {
            findViewById(R.id.activity_btn).startAnimation(fromBottom);
            findViewById(R.id.car_btn).startAnimation(fromBottom);
            findViewById(R.id.add_btn).startAnimation(rotateOpen);
        } else {
            findViewById(R.id.activity_btn).startAnimation(toBottom);
            findViewById(R.id.car_btn).startAnimation(toBottom);
            findViewById(R.id.add_btn).startAnimation(rotateClose);
        }
    }

    // Disables hidden buttons from bring clicked
    private void setClickable(boolean clicked) {
        if (!clicked) {
            findViewById(R.id.activity_btn).setClickable(true);
            findViewById(R.id.car_btn).setClickable(true);
        } else {
            findViewById(R.id.activity_btn).setClickable(false); // Disable buttons if clicked
            findViewById(R.id.car_btn).setClickable(false);
        }
    }

    private void openExcursionDetails(int vacationID) {
        Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
        intent.putExtra("vacationID", vacationID);
        startActivity(intent);
    }

    private void openCarDetails(int vacationID) {
        Intent intent = new Intent(VacationDetails.this, CarDetails.class);
        intent.putExtra("vacationID", vacationID);
        startActivity(intent);
    }

}

