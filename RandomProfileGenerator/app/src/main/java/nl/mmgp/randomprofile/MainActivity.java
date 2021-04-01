package nl.mmgp.randomprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Button buttonRandomProfiles = findViewById(R.id.button_random_profiles);
        Button buttonFavoriteProfiles = findViewById(R.id.button_favorite_profiles);
        Button buttonEditSettings = findViewById(R.id.button_edit_settings);

        buttonRandomProfiles.setOnClickListener(v -> openRandomProfilesActivity());
        buttonFavoriteProfiles.setOnClickListener(v -> openFavoriteProfilesActivity());
        buttonEditSettings.setOnClickListener(v -> openPreferenceActivity());
    }

    private void openRandomProfilesActivity(){
        Intent intent = new Intent(this, RandomProfilesActivity.class);
        startActivity(intent);
    }

    private void openPreferenceActivity(){
        Intent intent = new Intent(this, Preference.class);
        startActivity(intent);
    }

    private void openFavoriteProfilesActivity(){
        Intent intent = new Intent(this, FavoriteProfilesActivity.class);
        startActivity(intent);
    }
}