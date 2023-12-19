package com.location.amateolocationengin.ui.engins;

public interface CityCallback {
    void onCityNameReceived(String cityName);
    void onError(String errorMessage);
}
