package com.location.amateolocationengin.network;

import com.location.amateolocationengin.entities.AccessToken;
import com.location.amateolocationengin.models.CatalogueModel;
import com.location.amateolocationengin.models.EnginModel;
import com.location.amateolocationengin.models.PartenaireModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("engins-partenaire")
    Call<List<EnginModel>> engins();

    @FormUrlEncoded
    @POST("login")
    Call<AccessToken> login(@Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST("logout")
    Call<AccessToken> logout(@Field("access_token") AccessToken accessToken);

    @GET("partenairedetails")
    Call<PartenaireModel> partenaires();

    @FormUrlEncoded
    @POST("refresh")
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    @FormUrlEncoded
    @POST("register")
    Call<AccessToken> register(@Field("name") String name, @Field("prenom") String prenom, @Field("email") String email, @Field("password") String password);

    @GET("categories")
    Call<List<CatalogueModel>> getCategories();
}
