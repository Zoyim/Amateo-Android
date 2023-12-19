package com.location.amateolocationengin.network;

import com.location.amateolocationengin.models.VilleModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VilleApi {
    @GET("/api/ville/{id}")
    Call<VilleModel> getVilleById(@Path("id") int id);
}
