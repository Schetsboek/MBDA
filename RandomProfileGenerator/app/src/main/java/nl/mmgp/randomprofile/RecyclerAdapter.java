package nl.mmgp.randomprofile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Profile> profiles;

    public RecyclerAdapter(Context context, ArrayList<Profile> profiles){
        this.context = context;
        this.profiles = profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles){
        this.profiles = profiles;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView nameTextView;
        private final TextView genderTextView;
        private final ImageView profilePictureImageView;

        public ViewHolder(@NonNull View view) {
            super(view);
            nameTextView = view.findViewById(R.id.name_textview);
            genderTextView = view.findViewById(R.id.gender_textview);
            profilePictureImageView = view.findViewById(R.id.profile_picture);

            view.setOnClickListener(this);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public TextView getGenderTextView() {
            return genderTextView;
        }

        public ImageView getProfilePictureImageView() {
            return profilePictureImageView;
        }

        @Override
        public void onClick(View view) {
            openDetailPage(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        //Name
        String name = profiles.get(position).getName();
        holder.nameTextView.setText(name);

        //Gender
        Gender gender = profiles.get(position).getGender();
        if(gender == Gender.Male){
            holder.genderTextView.setText(context.getResources().getString(R.string.male));
            holder.genderTextView.setTextColor(context.getResources().getColor(R.color.male));
        } else if(gender == Gender.Female){
            holder.genderTextView.setText(context.getResources().getString(R.string.female));
            holder.genderTextView.setTextColor(context.getResources().getColor(R.color.female));
        } else {
            holder.genderTextView.setText(context.getResources().getString(R.string.no_gender));
            holder.genderTextView.setTextColor(context.getResources().getColor(R.color.no_gender));
        }

        //Image
        String imageUrl = profiles.get(position).getImageUrl();
        new LoadImageAsync(holder.profilePictureImageView).execute(imageUrl);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    private void openDetailPage(int position){
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("profile", profiles.get(position));
        context.startActivity(intent);
    }
}
