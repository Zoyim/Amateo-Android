package com.location.amateolocationengin.ui.espace;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.TokenManager;
import com.location.amateolocationengin.Utils;
import com.location.amateolocationengin.entities.AccessToken;
import com.location.amateolocationengin.network.ApiService;
import com.location.amateolocationengin.network.RetrofitBuilder;
import com.location.amateolocationengin.ui.dashboard.DashboardActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspaceConnexionActivity extends AppCompatActivity {
    private static final String TAG = "EspaceConnexionActivity";
    Call<AccessToken> call;
    ProgressBar mProgressBar;
    ApiService service;
    boolean singleBack = false;
    @BindView(R.id.til_mobile)
    TextInputLayout tilMobile;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    TokenManager tokenManager;
    AwesomeValidation validator;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_espace_connexion);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ButterKnife.bind(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        service = (ApiService) RetrofitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        setupRules();
    }

    @Override
    public void onBackPressed() {
        if (this.singleBack) {
            super.onBackPressed();
            return;
        }
        this.singleBack = true;
        Toast.makeText(this, "Appuyez à nouveau pour quitter", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                singleBack = false;
            }
        }, 2000);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        String mobile = tilMobile.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        tilMobile.setError(null);
        tilPassword.setError(null);

        validator.clear();
        if (validator.validate()) {

            mProgressBar.setVisibility(View.VISIBLE);
            call = service.login(mobile, password);

            call.enqueue(new Callback<AccessToken>() {
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w(TAG, "onResponse: " + response);
                    if (response.isSuccessful()) {
                        tokenManager.saveToken(response.body());
                        startActivity(new Intent(EspaceConnexionActivity.this, DashboardActivity.class));
                        finish();
                        return;
                    }
                    if (response.code() == 422) {
                        mProgressBar.setVisibility(View.GONE);
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(EspaceConnexionActivity.this, Utils.converErrors(response.errorBody()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void handleErrors(ResponseBody responseBody) {
        for (Map.Entry entry : Utils.converErrors(responseBody).getErrors().entrySet()) {
            if (((String) entry.getKey()).equals("mobile")) {
                this.tilMobile.setError((CharSequence) ((List) entry.getValue()).get(0));
            }
            if (((String) entry.getKey()).equals("password")) {
                this.tilPassword.setError((CharSequence) ((List) entry.getValue()).get(0));
            }
        }
    }

    public void setupRules() {
        this.validator.addValidation(this, R.id.til_mobile, Patterns.PHONE, R.string.err_mobile);
        this.validator.addValidation(this, R.id.til_password, RegexTemplate.NOT_EMPTY, R.string.err_password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Call<AccessToken> call2 = this.call;
        if (call2 != null) {
            call2.cancel();
            this.call = null;
        }
    }
}
