package nl.mmgp.randomprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        Profile profile = (Profile) intent.getParcelableExtra("profile");

        if (savedInstanceState == null) {
            setFragment(new ProfileDetailFragment(), profile);
        }
    }

    public void setFragment(Fragment fragment, Profile profile){
        // Insert the fragment by replacing any existing fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("profile", profile);

        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.profile_framelayout, fragment)
                .commit();
    }


}