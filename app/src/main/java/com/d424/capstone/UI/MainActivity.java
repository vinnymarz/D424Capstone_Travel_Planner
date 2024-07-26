package com.d424.capstone.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.d424.capstone.R;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Inflate View

        String[] users = getResources().getStringArray(R.array.users);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, users);

        // Bind to autoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setAdapter(arrayAdapter);
        }

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedUserType = (String) parent.getItemAtPosition(position);
            changeBackgroundColor(selectedUserType);
        });




        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Vacations.class);
                intent.putExtra("test", "Information sent");
                startActivity(intent);
            }
        });
    }

    private void changeBackgroundColor(String selectedUserType) {
        ConstraintLayout constraintLayout = findViewById(R.id.mainConstraintLayout);
        if (constraintLayout == null) {
            return; // Handle the case where the layout is not found
        }

        int backgroundColor = R.color.blue; // Default background color
        int backgroundImage = R.drawable.plane_background; // Default background image

        // Use a switch statement with an enum for better readability and type safety
        switch (Objects.requireNonNull(UserType.fromString(selectedUserType))) {
            case LEISURE_TRAVELER:
                backgroundColor = R.color.leisure_traveler_background;
                backgroundImage = R.drawable.leisure_traveler_background_image;
                break;
            case BUSINESS_TRAVELER:
                backgroundColor = R.color.business_traveler_background;
                backgroundImage = R.drawable.business_traveler_background_image;
                break;
            case FREQUENT_TRAVELER:
                backgroundColor = R.color.frequent_traveler_background;
                backgroundImage = R.drawable.frequent_traveler_background_image;
                break;
            case GROUP_PLANNER:
                backgroundColor = R.color.group_planner_background;
                backgroundImage = R.drawable.group_planner_background_image;
                break;
            case TRAVEL_INFLUENCER:
                backgroundColor = R.color.travel_influencer_background;
                backgroundImage = R.drawable.travel_influencer_background_image;
                break;
            // Default case is already handled by the initial value of backgroundColor
        }

        constraintLayout.setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
        constraintLayout.setBackgroundResource(backgroundImage);
    }

    // Define an enum for user types
    private enum UserType {
        LEISURE_TRAVELER, BUSINESS_TRAVELER, FREQUENT_TRAVELER, GROUP_PLANNER, TRAVEL_INFLUENCER;

        public static UserType fromString(String userType) {
            try {
                return UserType.valueOf(userType.toUpperCase().replace(" ", "_"));
            } catch (IllegalArgumentException e) {
                return null; // Handle invalid user types
            }
        }
    }
}