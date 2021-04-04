package nl.mmgp.randomprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FactsObserver {

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

        doBindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
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

    @Override
    public void getFact(String fact) {
        runOnUiThread(() -> Toast.makeText(this, getResources().getString(R.string.did_you_know) + "\n" + fact, Toast.LENGTH_SHORT).show());
    }

    // Clean service binding
    private boolean mShouldUnbind;
    private FactsService mBoundService;

    private final ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((FactsService.LocalBinder)service).getService();

            mBoundService.setObserver((FactsObserver) MainActivity.this);
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };

    void doBindService() {
        if (bindService(new Intent(MainActivity.this, FactsService.class), mConnection, Context.BIND_AUTO_CREATE)) {
            mShouldUnbind = true;
        }
    }

    void doUnbindService() {
        if (mShouldUnbind) {
            unbindService(mConnection);
            mShouldUnbind = false;
        }
    }
}