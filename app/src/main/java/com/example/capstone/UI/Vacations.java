package com.example.capstone.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424.capstone.R;
import com.example.capstone.database.Repository;
import com.example.capstone.entities.Excursion;
import com.example.capstone.entities.Vacation;
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

        // Initialize RecyclerView and populate it with vacation data
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    // Inflate the menu for the Vacations activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacations, menu);
        return true;
    }

    // Refresh the RecyclerView with updated vacation data
    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //takes user back to home
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

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

        return true;
    }
}