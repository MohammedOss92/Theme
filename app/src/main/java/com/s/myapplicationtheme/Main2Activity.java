package com.s.myapplicationtheme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    public static final String TAG="THEMES";
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
        setContentView(R.layout.activity_main2);
    }
    public void finishUp(){
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /** onResume is called when the activity is going to foreground. */

    @Override
    public void onResume(){
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
        if(currentTheme == 2){
            isLight = 0;
        } else {
            isLight = 1;
        }

        // Convert name to all upper case if that preference checked
        String temp = "Hello "+myName;
        if(isChecked) {
            // Strings in Java (Android) are immutable, so must return new value
            temp = temp.toUpperCase(Locale.US);
        }
        if(tv != null) tv.setText(temp);

        Log.i(TAG, "MainActivity:  isLight="+isLight +" lister="+lister+" Name="+myName
                +" isChecked="+isChecked);

        if(isLight==0) {
            setTheme(R.style.HoloLightCustom);
        } else if(isLight==1) {
            setTheme(R.style.HoloCustom);
        } else {
            setTheme(R.style.MyDialogLight);
        }

        // If theme has changed, force a restart of MainActivity to get the new theme
        // to display for it. That this is required may be a known bug in Android.  See
        //
        //    https://code.google.com/p/android/issues/detail?id=4394
        //
        // for further discussion.

        if(oldTheme != currentTheme){

            oldTheme = currentTheme;

            Intent k = new Intent(this, MainActivity.class);

            // Following flag clears the activity with old theme from the stack so an exit from the
            // activity with new theme will not take you back to the version with the old theme.

            k.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(k);
        }
    }
}
