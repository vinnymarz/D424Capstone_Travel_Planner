package com.d424.capstone.UI;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.test.core.app.ApplicationProvider;

import com.d424.capstone.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class SignInActivityTest {

    private SignInActivity signInActivity;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signInButton;

    @Before
    public void setUp() {
        signInActivity = Robolectric.buildActivity(SignInActivity.class).create().get();
        usernameEditText = signInActivity.findViewById(R.id.username);
        passwordEditText = signInActivity.findViewById(R.id.password);
        signInButton = signInActivity.findViewById(R.id.signInButton);
    }

    @Test
    public void testUsernameEmpty() {
        usernameEditText.setText("");
        passwordEditText.setText("password123");
        signInButton.performClick();

        String expectedToastMessage = "Username cannot be empty";
        assertEquals(expectedToastMessage, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testUsernameContainsSpecialCharacters() {
        usernameEditText.setText("user@name");
        passwordEditText.setText("password123");
        signInButton.performClick();

        String expectedToastMessage = "Username cannot contain special characters or spaces";
        assertEquals(expectedToastMessage, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testPasswordEmpty() {
        usernameEditText.setText("username");
        passwordEditText.setText("");
        signInButton.performClick();

        String expectedToastMessage = "Password cannot be empty";
        assertEquals(expectedToastMessage, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testPasswordTooShort() {
        usernameEditText.setText("username");
        passwordEditText.setText("pass");
        signInButton.performClick();

        String expectedToastMessage = "Password must be at least 8 characters long and contain at least one number";
        assertEquals(expectedToastMessage, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testPasswordNoNumber() {
        usernameEditText.setText("username");
        passwordEditText.setText("password");
        signInButton.performClick();

        String expectedToastMessage = "Password must be at least 8 characters long and contain at least one number";
        assertEquals(expectedToastMessage, ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testValidCredentials() {
        usernameEditText.setText("username");
        passwordEditText.setText("password123");
        signInButton.performClick();

        Intent expectedIntent = new Intent(signInActivity, MainActivity.class);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }
}