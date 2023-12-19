package com.location.amateolocationengin.network;

public interface VilleCallback {
    void onCityNameReceived(String nomVille);
    void onApiError(int statusCode);
    void onApiFailure(Throwable t);
}
