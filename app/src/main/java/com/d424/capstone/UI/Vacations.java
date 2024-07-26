package com.d424.capstone.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.d424.capstone.R;
import com.d424.capstone.database.Repository;
import com.d424.capstone.entities.Excursion;
import com.d424.capstone.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Vacations extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacations);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vacations.this, VacationDetails.class);
                startActivity(intent);
            }
        });

        Log.d("Vacations", "onCreate called"); // Log activity creation

        // Initialize RecyclerView and populate it with vacation data
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
        Log.d("Vacations", "Vacations set to adapter, count: " +allVacations.size()); // Log after setting vacations
    }

    // Inflate the menu for the Vacations activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacations, menu);
        Log.d("Vacations", "onCreateOptionsMenu called");
        return true;
    }

    // Refresh the RecyclerView with updated vacation data
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Vacations", "onResume called"); // Log when activity resumes
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Takes user back to home
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        // Inserts sample data into the database
        if (item.getItemId() == R.id.mysample) {
            repository = new Repository(getApplication());
            Vacation vacation = new Vacation(0, "Singapore", "Marina Bay Sands", "08/10/24", "08/12/24");
            repository.insert(vacation);
            vacation = new Vacation(0, "Dubai", "Le Meridien Mina Seyahi", "08/10/24", "08/12/24");
            repository.insert(vacation);
            Excursion excursion = new Excursion(0, "Food Tasting Tour", 1, "08/10/24");
            repository.insert(excursion);
            excursion = new Excursion(0, "Sightseeing", 1, "08/10/24");
            repository.insert(excursion);
            return true;
        }

        // Handle navigation to the Logging Activity
        if (id == R.id.viewLogs) {
            // Log messages first
            Log.d("Vacations", "Navigating to Logging activity");// Then navigate
            Intent intent = new Intent(Vacations.this, Logging.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}