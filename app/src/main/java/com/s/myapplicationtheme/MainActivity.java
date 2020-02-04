package com.s.myapplicationtheme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "THEMES";
    private int isLight;
    private boolean isChecked;
    private TextView tv;
    private int currentTheme;
    private int oldTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        isChecked = sharedPref.getBoolean("caps_pref", false);
        String lister = sharedPref.getString("list_preference", "1");
        oldTheme = Integer.parseInt(lister);

        // Following options to change the Theme must precede setContentView().

        toggleTheme();

        setContentView(R.layout.activity_main);
        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Prefs.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

//            case (R.id.help):
//                // Actions for Help page
//                Intent i = new Intent(this, Help.class);
//                startActivity(i);
//                return true;
//
//            case(R.id.about):
//                // Actions for About page
//                Intent k = new Intent(this, About.class);
//                startActivity(k);
//                return true;

            case (R.id.prefs):
                // Actions for preferences page
                Log.i(TAG, "Settings");
                Intent j = new Intent(this, Prefs.class);
                startActivity(j);
                return true;

            // Exit: not really needed because back button serves same function,
            // but we include as illustration since some users may be more
            // comfortable with an explicit quit button.

            case (R.id.quit):
                finishUp();
                return true;

        }
        return false;
    }

    public void finishUp() {
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * onResume is called when the activity is going to foreground.
     */

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        toggleTheme();
    }

    private void toggleTheme() {

        // Following options to change the Theme must precede setContentView().

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        isChecked = sharedPref.getBoolean("caps_pref", false);
        String lister = sharedPref.getString("list_preference", "1");
        String myName = sharedPref.getString("edittext_preference", "");

        currentTheme = Integer.parseInt(lister);
        if (currentTheme == 1) {
            isLight = 0;
        } else if (currentTheme == 2) {
            isLight = 1;
        } else {
            isLight = 2;
        }

        // Convert name to all upper case if that preference checked
        String temp = "Hello " + myName;
        if (isChecked) {
            // Strings in Java (Android) are immutable, so must return new value
            temp = temp.toUpperCase(Locale.US);
        }
        if (tv != null) tv.setText(temp);

        Log.i(TAG, "MainActivity:  isLight=" + isLight + " lister=" + lister + " Name=" + myName
                + " isChecked=" + isChecked);

        if (isLight == 0) {
            setTheme(R.style.BrownActionBar);
        } else if (isLight == 1) {
            setTheme(R.style.HoloCustom);
        } else {
            setTheme(R.style.AppTheme);
        }

        // If theme has changed, force a restart of MainActivity to get the new theme
        // to display for it. That this is required may be a known bug in Android.  See
        //
        //    https://code.google.com/p/android/issues/detail?id=4394
        //
        // for further discussion.

        if (oldTheme != currentTheme) {

            oldTheme = currentTheme;

            Intent k = new Intent(this, MainActivity.class);

            // Following flag clears the activity with old theme from the stack so an exit from the
            // activity with new theme will not take you back to the version with the old theme.

            k.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(k);
        }
    }
}
