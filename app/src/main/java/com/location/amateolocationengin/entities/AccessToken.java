package com.location.amateolocationengin.entities;

import com.squareup.moshi.Json;

public class AccessToken {
    @Json(name = "access_token")
    String accessToken;
    @Json(name = "expires_in")
    int expiresIn;
    @Json(name = "refresh_token")
    String refreshToken;
    @Json(name = "token_type")
    String tokenType;

    public String getTokenType() {
        return this.tokenType;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
