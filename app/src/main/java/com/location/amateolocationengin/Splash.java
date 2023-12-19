package com.location.amateolocationengin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
public class Splash extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    private LinearLayout errorLayout;
    private Button btnRefresh;
    private ProgressBar progressBarError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        viewFlipper = findViewById(R.id.viewFlipper);
        progressBarError = findViewById(R.id.progressBarError);
        if (progressBarError != null) {
            checkMobileData();
        }
    }

    private void checkMobileData() {
        progressBarError.setVisibility(View.VISIBLE);
        new MobileDataCheckTask(this).execute();
    }

    @SuppressWarnings("deprecation")
    private static class MobileDataCheckTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<Splash> splashReference;

        MobileDataCheckTask(Splash splash) {
            splashReference = new WeakReference<>(splash);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String command = "ping -c 1 www.google.com";
                Process process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line).append("\n");
                }

                int exitCode = process.waitFor();

                return (exitCode == 0 && response.toString().contains("bytes from"));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean hasData) {
            Splash splashActivity = splashReference.get();
            if (splashActivity != null) {
                splashActivity.handleMobileDataResult(hasData);
            } else {
                Toast.makeText(splashActivity, "Une erreur s'est produite. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void handleMobileDataResult(boolean hasData) {
        progressBarError.setVisibility(View.GONE);

        if (hasData) {
            startActivity(new Intent(Splash.this, MainActivity.class));
            finish();
        } else {
            viewFlipper.setDisplayedChild(1);

            errorLayout = findViewById(R.id.errorLayout);
            btnRefresh = findViewById(R.id.btnRefresh);
            progressBarError = findViewById(R.id.progressBarError);

            btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkMobileData();
                }
            });

            Toast.makeText(Splash.this, "La connexion est indisponible.", Toast.LENGTH_SHORT).show();
        }
    }
}