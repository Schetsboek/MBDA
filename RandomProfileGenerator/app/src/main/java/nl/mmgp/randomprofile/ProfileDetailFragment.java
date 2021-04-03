package nl.mmgp.randomprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class ProfileDetailFragment extends Fragment {

    private Profile profile;

    public ProfileDetailFragment() {
        super(R.layout.fragment_profile_detail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView nameTextView = view.findViewById(R.id.name_textview);
        TextView genderTextView = view.findViewById(R.id.gender_textview);
        TextView favoriteTextView = view.findViewById(R.id.favorite_textview);
        ImageView profilePictureImageView = view.findViewById(R.id.profile_picture);

        Button editButton = view.findViewById(R.id.button_edit_profile);

        profile = requireArguments().getParcelable("profile");

        if (getActivity() instanceof ProfileActivity) {
            ProfileActivity profileActivity = (ProfileActivity) this.getActivity();
            editButton.setOnClickListener(v -> profileActivity.setFragment(new ProfileEditFragment(), profile));
        }

        //Name
        String name = profile.getName();
        nameTextView.setText(name);

        //Gender
        Gender gender = profile.getGender();
        if (gender == Gender.Male) {
            genderTextView.setText(getResources().getString(R.string.male));
            genderTextView.setTextColor(getResources().getColor(R.color.male));
        } else if (gender == Gender.Female) {
            genderTextView.setText(getResources().getString(R.string.female));
            genderTextView.setTextColor(getResources().getColor(R.color.female));
        } else {
            genderTextView.setText(getResources().getString(R.string.no_gender));
            genderTextView.setTextColor(getResources().getColor(R.color.no_gender));
        }

        if(profile.isFavorite()){
            favoriteTextView.setText(getResources().getString(R.string.favorite));
            favoriteTextView.setTextColor(getResources().getColor(R.color.favorite));
        } else {
            favoriteTextView.setText(getResources().getString(R.string.notfavorite));
            favoriteTextView.setTextColor(getResources().getColor(R.color.notfavorite));
        }

        //Image
        String imageUrl = profile.getImageUrl();
        new LoadImageAsync(profilePictureImageView).execute(imageUrl);
    }
}