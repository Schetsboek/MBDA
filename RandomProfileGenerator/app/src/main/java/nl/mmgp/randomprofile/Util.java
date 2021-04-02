package nl.mmgp.randomprofile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Util {

    private static final String FILE_URL = "favorites.txt";

    public static boolean externalStorageEnabled() {
        // Existence check
        String state = Environment.getExternalStorageState();
        // ReadOnly check
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) && Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    public static boolean addToExternalStorage(Profile profile){
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard.getAbsolutePath(), FILE_URL);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            JSONObject jsonObject= new JSONObject();
            jsonObject.put("name", profile.getName());
            jsonObject.put("imageUrl", profile.getImageUrl());
            jsonObject.put("gender", profile.getGender().toString());

            String data = readExternalStorageString();
            if(data.equals("")){
                data += jsonObject.toString();
            } else {
                data += "," + jsonObject.toString();
            }

            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
            return true;
        } catch (IOException | JSONException e) {
            return false;
        }
    }

    public static boolean removeFromExternalStorage(Profile profile){
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard.getAbsolutePath(), FILE_URL);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            JSONObject jsonObject= new JSONObject();
            jsonObject.put("name", profile.getName());
            jsonObject.put("imageUrl", profile.getImageUrl());
            jsonObject.put("gender", profile.getGender().toString());

            String data = readExternalStorageString();
            if(data.contains(jsonObject.toString())) {
                if(data.contains(jsonObject.toString() + ",")) {
                    data.replaceFirst(jsonObject.toString() + ",", "");
                } else if(data.contains("," + jsonObject.toString())) {
                    data.replaceFirst("," + jsonObject.toString(), "");
                } else {
                    data.replaceFirst(jsonObject.toString(), "");
                }
            } else {
                return false;
            }

            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
            return true;
        } catch (IOException | JSONException e) {
            return false;
        }
    }

    public static String readExternalStorageString() throws IOException {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath(), FILE_URL);

        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));

        StringBuilder data = new StringBuilder();
        String strLine;
        while ((strLine = bufferedReader.readLine()) != null) {
            data.append(strLine).append(",");
        }
        dataInputStream.close();
        return data.toString();
    }
}
