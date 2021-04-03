package nl.mmgp.randomprofile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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

    public static void addToExternalStorage(Profile profile) throws IOException, JSONException{
        String data = readExternalStorageString();

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name", profile.getName());
        jsonObject.put("imageUrl", profile.getImageUrl());
        jsonObject.put("gender", profile.getGender().toString());

        if(data.equals("")){
            data += jsonObject.toString();
        } else {
            data += "," + jsonObject.toString();
        }

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath(), FILE_URL);

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(data);
        fileWriter.flush();
        fileWriter.close();
    }

    public static void removeFromExternalStorage(Profile profile) throws IOException, JSONException {
        String data = readExternalStorageString();

        JSONObject jsonObject= new JSONObject();
        jsonObject.put("name", profile.getName());
        jsonObject.put("imageUrl", profile.getImageUrl());
        jsonObject.put("gender", profile.getGender().toString());

        JSONArray jsonArray = new JSONArray("["+data+"]");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject dataJSONObject = jsonArray.getJSONObject(i);
            if (dataJSONObject.getString("name").equals(jsonObject.getString("name"))) {
                jsonArray.remove(i);
                break;
            }
        }

        // Remove []
        String newFavorites = jsonArray.toString();
        newFavorites = newFavorites.substring(1, newFavorites.length() - 1);

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath(), FILE_URL);

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(newFavorites);
        fileWriter.flush();
        fileWriter.close();
    }

    public static String readExternalStorageString() throws IOException {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard.getAbsolutePath(), FILE_URL);

        if(file.exists()){
            StringBuilder data = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                data.append(line);
            }
            bufferedReader.close();
            return data.toString();
        } else {
            file.createNewFile();
            return "";
        }
    }
}
