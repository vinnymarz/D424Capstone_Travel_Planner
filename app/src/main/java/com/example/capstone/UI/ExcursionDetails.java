package com.example.capstone.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d424.capstone.R;
import com.example.capstone.database.Repository;
import com.example.capstone.entities.Excursion;
import com.example.capstone.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ExcursionDetails extends AppCompatActivity {

    Repository repository;
    Excursion activeExcursion;
    EditText editTitle;
    String title;
    String setDate;
    Random r = new Random();
    int setAlert = r.nextInt(9999);
    int excursionID;
    int vacationID;
    TextView editExcursionDate;
    DatePickerDialog.OnDateSetListener excursionDate;
    final Calendar myCalendarDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        // Log intent data for debugging
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                Log.d("ExcursionDetails", String.format("Intent extra: %s = %s", key, value));
            }
        }

        // Initialize repository and UI elements
        repository = new Repository(getApplication());
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.excursionTitle);
        if (title != null) { // Added null check
            editTitle.setText(title);
        }
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        setDate = getIntent().getStringExtra("excursionDate");
        setAlert = r.nextInt(9999);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editExcursionDate = findViewById(R.id.excursionDate);

        // Set initial text for the button using string resource
        if (setDate == null || setDate.isEmpty()) {
            editExcursionDate.setText(getString(R.string.select_activity_date));
        } else {
            try {
                Date date = sdf.parse(setDate);
                editExcursionDate.setText(sdf.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Set click listener for excursion date TextView to open date picker dialog
        editExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ExcursionDetails.this, excursionDate, myCalendarDate
                        .get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH),
                        myCalendarDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Set listener for date picker dialog to update date when selected
        excursionDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarDate.set(Calendar.YEAR, year);
                myCalendarDate.set(Calendar.MONTH, month);
                myCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        // Apply input filters to restrict special characters and integers in the excursion title
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                        Toast.makeText(ExcursionDetails.this, "Integers or special characters are not allowed", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        };
        editTitle.setFilters(new InputFilter[]{filter});
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editExcursionDate.setText(sdf.format(myCalendarDate.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    // Handle menu item selection for saving, deleting, and setting an alert for the excursion
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        // Save excursion details
        if (item.getItemId() == R.id.activitysave) {
            if (validateInputs()) {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String excursionDateString = sdf.format(myCalendarDate.getTime());
                Vacation vacation = null;
                List<Vacation> vacations = repository.getmAllVacations();
                for (Vacation vac : vacations) {
                    if (vac.getVacationId() == vacationID) {
                        vacation = vac;
                    }
                }
                try {
                    Date excursionDate = sdf.parse(excursionDateString);
                    Date startDate = sdf.parse(vacation.getStartDate());
                    Date endDate = sdf.parse(vacation.getEndDate());
                    assert excursionDate != null;
                    if (excursionDate.before(startDate) || excursionDate.after(endDate)) {
                        Toast.makeText(this, "Activity date must occur between itinerary start and end dates.", Toast.LENGTH_LONG).show();
                        return true;
                    } else {
                        Excursion excursion;
                        if (excursionID == -1) {
                            if (repository.getmAllExcursions().size() == 0) excursionID = 1;
                            else
                                excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                            excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID, excursionDateString);
                            repository.insert(excursion);
                            this.finish();
                        } else {
                            excursion = new Excursion(excursionID, editTitle.getText().toString(), vacationID, excursionDateString);
                            repository.update(excursion);
                            this.finish();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "All fields must be correctly completed", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        // Delete the active excursion
        if (item.getItemId() == R.id.activitydelete) {
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) activeExcursion = excursion;
            }
            repository.delete(activeExcursion);
            Toast.makeText(ExcursionDetails.this, activeExcursion.getExcursionTitle() + " activity was deleted", Toast.LENGTH_LONG).show();
            ExcursionDetails.this.finish();
        }

        // Set an alert for the excursion
        if (item.getItemId() == R.id.activityalert) {
            if (excursionID >= 0) {
                String dateFromScreen = editExcursionDate.getText().toString();
                String alert = "Your " + title + " activity is today";

                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                intent.putExtra("key", alert);
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, setAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

                return true;
            } else {
                Toast.makeText(this, "Activity must be saved before alert is set.", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (setDate != null && !setDate.isEmpty()) {
            updateLabel();
        }
    }

    // Validate user input for excursion title
    private boolean validateInputs() {
        boolean isValid = true;

        if (editTitle.getText().toString().trim().isEmpty()) {
            editTitle.setError("Activity title is required");
            isValid = false;
        }

        if (editExcursionDate.getText().toString().equals("Select Activity Date")) {
            Toast.makeText(this, "Activity date is required", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }
}