package nl.mmgp.randomprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RandomProfilesActivity extends AppCompatActivity {

    private ArrayList<Profile> profiles;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RequestQueue queue;

    private final String URL = "https://randomuser.me/api/?results=";
    private String generated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_profiles);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        generated = sharedPreferences.getString("generated", "20");

        recyclerView = findViewById(R.id.profile_recyclerview);

        queue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        profiles = new ArrayList<>();

        if(recyclerAdapter != null){
            updateRecyclerView();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + generated,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            //New try to keep exceptions from impacting all profiles.
                            try {
                                JSONObject jsonProfile = jsonArray.getJSONObject(i);
                                // Gender
                                String genderString = jsonProfile.getString("gender");
                                Gender gender = Gender.valueOf(genderString.substring(0, 1).toUpperCase() + genderString.substring(1).toLowerCase());

                                // Name
                                String firstName = jsonProfile.getJSONObject("name").getString("first");
                                String lastName = jsonProfile.getJSONObject("name").getString("last");
                                String name = firstName + " " + lastName;

                                // Image URL
                                String imageURL = jsonProfile.getJSONObject("picture").getString("large");

                                profiles.add(new Profile(name, imageURL, gender, false));
                            } catch (JSONException e) {
                                // Oops
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Set recyclerview
                    if(recyclerAdapter == null){
                        setRecyclerView();
                    } else {
                        updateRecyclerView();
                    }
                }, error -> {
            Toast.makeText(this, getResources().getString(R.string.bad_reply), Toast.LENGTH_LONG).show();
        });

        queue.add(stringRequest);
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