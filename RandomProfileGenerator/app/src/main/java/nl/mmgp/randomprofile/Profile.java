package nl.mmgp.randomprofile;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    private String name;
    private String imageUrl;
    private Gender gender;
    private boolean favorite;

    public Profile(String name, String imageUrl, Gender gender, boolean favorite) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.gender = gender;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(imageUrl);
        out.writeString(gender.name());
        out.writeByte((byte) (favorite ? 1 : 0));
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Profile(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        gender = Gender.valueOf(in.readString());
        favorite = in.readByte() != 0;
    }
}
