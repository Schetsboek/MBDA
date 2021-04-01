package nl.mmgp.randomprofile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Preference extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //get a handle on preferences that require validation
        android.preference.Preference generatedPreference = getPreferenceScreen().findPreference("generated");

        //Validate numbers only
        generatedPreference.setOnPreferenceChangeListener((preference, o) -> isNumber(o));
    }

    private boolean isNumber(Object o){
        String value = o.toString();
        if(!value.equals("") && value.matches("\\d*")){
            return true;
        } else {
            Toast.makeText(this, getResources().getString(R.string.enter_valid_number), Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
