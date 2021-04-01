package nl.mmgp.randomprofile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class ProfileEditFragment extends Fragment {

    private Profile profile;

    private EditText nameEditText;
    private Button genderButton;
    private Button favoriteButton;
    private ImageView profilePictureImageView;

    public ProfileEditFragment() {
        super(R.layout.fragment_profile_detail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        nameEditText = view.findViewById(R.id.name_edittext);
        genderButton = view.findViewById(R.id.gender_button);
        favoriteButton = view.findViewById(R.id.favorite_button);
        profilePictureImageView = view.findViewById(R.id.profile_picture);

        Button photoButton = view.findViewById(R.id.photo_button);
        Button saveButton = view.findViewById(R.id.button_save_profile);

        profile = (Profile) requireArguments().getParcelable("profile");

        if(getActivity() instanceof ProfileActivity) {
            ProfileActivity profileActivity = (ProfileActivity) this.getActivity();

            photoButton.setOnClickListener(v -> {
                dispatchTakePictureIntent();
            });

            saveButton.setOnClickListener(v -> {
                profile.setName(nameEditText.getText().toString());
                profileActivity.setFragment(new ProfileDetailFragment(), profile);
            });
        }

        //Name
        String name = profile.getName();
        nameEditText.setText(name);

        //Gender
        setGenderButtonStyle();
        genderButton.setOnClickListener(v -> switchGender());

        //Favorite
        setFavoriteButtonStyle();
        favoriteButton.setOnClickListener(v -> switchFavorite());

        //Image
        String imageUrl = profile.getImageUrl();
        new LoadImageAsync(profilePictureImageView).execute(imageUrl);

        profilePictureImageView.setOnClickListener(v -> setRandomImage());
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, 1);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePictureImageView.setImageBitmap(imageBitmap);
        }
    }


    private void switchGender(){
        if(profile.getGender() == Gender.Male){
            profile.setGender(Gender.Female);
        } else {
            profile.setGender(Gender.Male);
        }
        setGenderButtonStyle();
    }

    private void setGenderButtonStyle(){
        Gender gender = profile.getGender();
        if(gender == Gender.Male){
            genderButton.setText(getResources().getString(R.string.male));
            genderButton.setBackgroundColor(getResources().getColor(R.color.male));
        } else if(gender == Gender.Female){
            genderButton.setText(getResources().getString(R.string.female));
            genderButton.setBackgroundColor(getResources().getColor(R.color.female));
        } else {
            genderButton.setText(getResources().getString(R.string.no_gender));
            genderButton.setBackgroundColor(getResources().getColor(R.color.no_gender));
        }
    }

    private void switchFavorite(){
        if(profile.isFavorite()){
            //remove
        } else {
            //add
        }

        profile.setFavorite(!profile.isFavorite());
        setFavoriteButtonStyle();
    }

    private void setFavoriteButtonStyle(){
        if(profile.isFavorite()){
            favoriteButton.setText(getResources().getString(R.string.favorite));
            favoriteButton.setBackgroundColor(getResources().getColor(R.color.favorite));
        } else {
            favoriteButton.setText(getResources().getString(R.string.notfavorite));
            favoriteButton.setBackgroundColor(getResources().getColor(R.color.notfavorite));
        }
    }

    private void setRandomImage(){
        Random random = new Random();
        int randomID = random.nextInt(100);

        String url = "https://randomuser.me/api/portraits/";
        if(profile.getGender() == Gender.Male){
            url += "men/" + randomID + ".jpg";
        } else {
            url += "women/" + randomID + ".jpg";
        }
        profile.setImageUrl(url);
        new LoadImageAsync(profilePictureImageView).execute(profile.getImageUrl());
    }
}