package com.d424.capstone.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.d424.capstone.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ImageView logoImageView = findViewById(R.id.logoImageView);
        setRoundedCorners(logoImageView);

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateCredentials(username, password)) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void setRoundedCorners(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Bitmap roundedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(roundedBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), 16, 16, paint);
        imageView.setImageBitmap(roundedBitmap);
    }

    private boolean validateCredentials(String username, String password) {
        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!username.matches("^[a-zA-Z0-9]*$")) {
            Toast.makeText(this, "Username cannot contain special characters or spaces", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password == null || password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            Toast.makeText(this, "Password must be at least 8 characters long and contain at least one number", Toast.LENGTH_SHORT).show();
            return false;
        }



        // Hash the input password
        String hashedInputPassword = hashPassword(password);

        //String storedHashedPassword = "ef92b778bafe771e89245b89ecbcf8d8b1b9e1b1e1b9e1b9e1b9e1b9e1b9e1b9";

        // Validate the hashed password
        if (!hashedInputPassword.equals(hashPassword(password))) {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}