package com.example.capstone.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.d424.capstone.R;
import com.example.capstone.entities.LogUtils;

public class Logging extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);

        TextView logTextView = findViewById(R.id.logTextView);
        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);

        logTextView.setText(LogUtils.getLogcatOutput());

        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            String filteredLogs = LogUtils.searchLogcatOutput(query);
            logTextView.setText(filteredLogs);
        });
    }
}