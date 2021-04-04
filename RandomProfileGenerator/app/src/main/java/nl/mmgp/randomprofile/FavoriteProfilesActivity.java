package nl.mmgp.randomprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class FavoriteProfilesActivity extends AppCompatActivity {

    private ArrayList<Profile> profiles;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_profiles);

        recyclerView = findViewById(R.id.profile_recyclerview);

        profiles = new ArrayList<>();
        setRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        profiles = new ArrayList<>();

        if(recyclerAdapter != null){
            updateRecyclerView();
        }

        try{
            String data = Util.readExternalStorageString();
            if(data.equals("")){
                Toast.makeText(this, getResources().getString(R.string.no_favorites), Toast.LENGTH_LONG).show();
            } else {
                JSONArray jsonArray = new JSONArray("["+data+"]");
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    //New try to keep exceptions from impacting all profiles.
                    try {
                        JSONObject jsonProfile = jsonArray.getJSONObject(i);
                        // Gender
                        Gender gender = Gender.valueOf(jsonProfile.getString("gender"));

                        // Name
                        String name = jsonProfile.getString("name");

                        // Image URL
                        String imageURL = jsonProfile.getString("imageUrl");

                        profiles.add(new Profile(name, imageURL, gender, true));
                    } catch (JSONException e) {
                        // Oops
                    }
                }

                //Set recyclerview
                if(recyclerAdapter == null){
                    setRecyclerView();
                } else {
                    updateRecyclerView();
                }
            }
        } catch (IOException | JSONException e) {
            Toast.makeText(this, getResources().getString(R.string.no_favorites), Toast.LENGTH_LONG).show();
        }

    }

    private void updateRecyclerView() {
        recyclerAdapter.setProfiles(profiles);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void setRecyclerView(){
        recyclerAdapter = new RecyclerAdapter(this, profiles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
    }
}