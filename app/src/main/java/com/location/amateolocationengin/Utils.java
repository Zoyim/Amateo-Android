package com.location.amateolocationengin;

import com.location.amateolocationengin.entities.ApiError;
import com.location.amateolocationengin.network.RetrofitBuilder;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;

public class Utils {
    public static ApiError converErrors(ResponseBody responseBody) {
        try {
            return (ApiError) RetrofitBuilder.getRetrofit().responseBodyConverter(ApiError.class, new Annotation[0]).convert(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
