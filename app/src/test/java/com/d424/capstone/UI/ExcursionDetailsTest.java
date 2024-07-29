package com.d424.capstone.UI;

import android.text.InputFilter;
import android.widget.EditText;
import android.widget.Toast;

import com.d424.capstone.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class ExcursionDetailsTest {

    private ExcursionDetails excursionDetails;
    private EditText editTitle;

    @Before
    public void setUp() {
        excursionDetails = Robolectric.buildActivity(ExcursionDetails.class).create().get();
        editTitle = excursionDetails.findViewById(R.id.excursionTitle);
    }

    @Test
    public void testInputFilter_AllowsLettersAndSpaces() {
        editTitle.setText("Valid Title");
        assertEquals("Valid Title", editTitle.getText().toString());
    }

    @Test
    public void testInputFilter_DisallowsSpecialCharacters() {
        editTitle.setText("Invalid@Title");
        assertEquals("", editTitle.getText().toString());
        assertEquals("Integers or special characters are not allowed", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testInputFilter_DisallowsIntegers() {
        editTitle.setText("Invalid123");
        assertEquals("", editTitle.getText().toString());
        assertEquals("Integers or special characters are not allowed", ShadowToast.getTextOfLatestToast());
    }
}