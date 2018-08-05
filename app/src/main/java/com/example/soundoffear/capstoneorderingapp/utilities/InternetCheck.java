package com.example.soundoffear.capstoneorderingapp.utilities;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.soundoffear.capstoneorderingapp.R;

public class InternetCheck extends AsyncTask<String, Void, Integer> {

    private Context context;

    public InternetCheck(Context context) {
        this.context = context;
    }

    public boolean isInternetConnectionAvaliable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if(connectivityManager != null) {
            NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
            if(networkInfo !=null) {
                return networkInfo.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    @Override
    protected Integer doInBackground(String... strings) {

        int resultValue = 0;

        Runtime runtime = Runtime.getRuntime();

        try {
            Process process = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int result = process.waitFor();
            if(result == 0) {
                resultValue = 1;
            } else {
                resultValue = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultValue =0;
        }

        return resultValue;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(isInternetConnectionAvaliable()) {
            if(integer ==1) {
                Toast.makeText(context, R.string.con_av, Toast.LENGTH_SHORT).show();
            }

            if(integer ==0) {
                Toast.makeText(context, R.string.con_not_av, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.con_not_av, Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(integer);
    }
}
