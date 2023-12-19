package com.location.amateolocationengin.ui.partenaire;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import androidx.annotation.NonNull;
import com.location.amateolocationengin.R;
import com.location.amateolocationengin.TokenManager;
import com.location.amateolocationengin.Utils;
import com.location.amateolocationengin.entities.AccessToken;
import com.location.amateolocationengin.entities.ApiError;
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
import static android.content.Context.MODE_PRIVATE;

public class EspacePartenaireFragment extends Fragment {
    private static final String TAG = "EspacePartenaireFrag";
    Call<AccessToken> call;
    ProgressBar mProgressBar;
    ApiService service;
    @BindView(R.id.til_mobile)
    TextInputLayout tilMobile;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    TokenManager tokenManager;
    private EspacePartenaireViewModel toolsViewModel;
    AwesomeValidation validator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        toolsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EspacePartenaireViewModel.class);
        View root = inflater.inflate(R.layout.fragment_partenaire, container, false);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        ButterKnife.bind( this, root);
        service = RetrofitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", MODE_PRIVATE));
        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        setupRules();

        if(tokenManager.getToken().getAccessToken() != null){
            startActivity(new Intent(EspacePartenaireFragment.this.getActivity(), DashboardActivity.class));
            getActivity().finish();
        }

        return root;
    }

    @OnClick(R.id.btn_login)
    public void login(){
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
                        startActivity(new Intent(EspacePartenaireFragment.this.getActivity(), DashboardActivity.class));
                        getActivity().finish();
                    }
                    if (response.code() == 422) {
                        mProgressBar.setVisibility(View.GONE);
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        mProgressBar.setVisibility(View.GONE);
                        ApiError apiError = Utils.converErrors(response.errorBody());
                        Toast.makeText(EspacePartenaireFragment.this.getActivity(), apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    public void handleErrors(ResponseBody response) {
        ApiError apiError = Utils.converErrors(response);
        for (Map.Entry<String, List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("mobile")) {
                tilMobile.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("password")) {
                tilPassword.setError(error.getValue().get(0));
            }
        }
    }

    public void setupRules() {
        this.validator.addValidation(getActivity(), R.id.til_mobile, Patterns.PHONE, R.string.err_mobile);
        this.validator.addValidation(getActivity(), R.id.til_password, RegexTemplate.NOT_EMPTY, R.string.err_password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Call<AccessToken> call2 = call;
        if (call2 != null) {
            call2.cancel();
            call = null;
        }
    }
}
