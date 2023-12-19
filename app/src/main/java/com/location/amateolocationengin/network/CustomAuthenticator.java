package com.location.amateolocationengin.network;

import com.location.amateolocationengin.TokenManager;
import com.location.amateolocationengin.entities.AccessToken;
import com.google.common.net.HttpHeaders;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Request.Builder;
import okhttp3.Route;

public class CustomAuthenticator implements Authenticator {
    private static CustomAuthenticator INSTANCE;
    private TokenManager tokenManager;

    private CustomAuthenticator(TokenManager tokenManager2) {
        this.tokenManager = tokenManager2;
    }

    static synchronized CustomAuthenticator getInstance(TokenManager tokenManager2) {
        CustomAuthenticator customAuthenticator;
        synchronized (CustomAuthenticator.class) {
            if (INSTANCE == null) {
                INSTANCE = new CustomAuthenticator(tokenManager2);
            }
            customAuthenticator = INSTANCE;
        }
        return customAuthenticator;
    }

    public Request authenticate(Route route, Response response) throws IOException {
        if (responseCount(response) >= 3) {
            return null;
        }
        AccessToken token = this.tokenManager.getToken();
        ApiService apiService = (ApiService) RetrofitBuilder.createService(ApiService.class);
        StringBuilder sb = new StringBuilder();
        sb.append(token.getRefreshToken());
        sb.append("a");
        retrofit2.Response execute = apiService.refresh(sb.toString()).execute();
        if (!execute.isSuccessful()) {
            return null;
        }
        this.tokenManager.saveToken((AccessToken) execute.body());
        Builder newBuilder = response.request().newBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Bearer ");
        sb2.append(((AccessToken) execute.body()).getAccessToken());
        return newBuilder.header(HttpHeaders.AUTHORIZATION, sb2.toString()).build();
    }

    private int responseCount(Response response) {
        int i = 1;
        while (true) {
            response = response.priorResponse();
            if (response == null) {
                return i;
            }
            i++;
        }
    }
}
