package com.texasimaginology.ticms;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class CheckInternetConnection extends AsyncTask<String, Void, Boolean> {

    private Context context;

    public static Boolean NETWORK_STATUS;

    CheckInternetConnection(Activity activity) {
        this.context = activity.getApplicationContext();
        Activity activity1 = activity;
    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // Do something with the result here
    }

    @Override
    protected Boolean doInBackground(String... params) {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                NETWORK_STATUS = true;
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                NETWORK_STATUS = false;
                Log.w("connection", "Error checking internet connection", e);
            }
        } else {
            // If i want to show the toast, it's true
            //Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show(); // Just another function to show a toast
            NETWORK_STATUS = false;
            Log.d("Connection","No internet connection");
        }
        return false;
    }

    protected boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;

    }
}