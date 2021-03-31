package nl.mmgp.randomprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class RandomProfilesActivity extends AppCompatActivity {

    private ArrayList<Profile> profiles;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_profiles);

        profiles = new ArrayList<>();
        recyclerView = findViewById(R.id.profile_recyclerview);

        profiles.add(new Profile("Caroline 1", "https://randomuser.me/api/portraits/women/7.jpg", Gender.Female, false));
        profiles.add(new Profile("Caroline 2", "https://randomuser.me/api/portraits/women/9.jpg", Gender.Female, false));
        profiles.add(new Profile("Caroline 3", "https://randomuser.me/api/portraits/women/2.jpg", Gender.Female, false));
        profiles.add(new Profile("Caroline 4", "https://randomuser.me/api/portraits/women/5.jpg", Gender.Female, false));
        profiles.add(new Profile("Caroline 5", "https://randomuser.me/api/portraits/women/11.jpg", Gender.Female, false));
        profiles.add(new Profile("Caroline 6", "https://randomuser.me/api/portraits/women/63.jpg", Gender.Female, false));
        profiles.add(new Profile("Caroline 7", "https://randomuser.me/api/portraits/women/25.jpg", Gender.Female, false));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, profiles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
    }
}