package com.plan_app.android_plan_app.server.remote_api_service;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.plan_app.android_plan_app.server.AuthenticationService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class ServiceGenerator {
    private static final String API_URL = "http://192.168.0.101:8080";
    //////private static final String API_URL = "http://127.0.0.1:8080";//
    //private static final String API_URL = "http://192.168.0.13:8080";//julie

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(JacksonConverterFactory
                    .create());

    private static Retrofit retrofit = retrofitBuilder.build();

//    public static <S> S createService(Class<S> serviceClass){
//        return createService(serviceClass, null, null, null, );
//    }

    public static <S> S createSimpleService(Class<S> serviceClass){
        return createService(serviceClass,null);
    }

    public static <S> S createService(Class<S> serviceClass){
        return createService(serviceClass, AuthenticationService.getInstance().getTokenWithType());
    }

    public static <S> S createService(Class<S> serviceClass, String tokenWithType){

        if(tokenWithType!= null && !TextUtils.isEmpty(tokenWithType)){

                httpClientBuilder.addInterceptor(new Interceptor() {
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder builder = original.newBuilder()
                                .header("Authorization", tokenWithType);

                        Request request = builder.build();
                        return chain.proceed(request);
                    }
                });

            retrofitBuilder.client(httpClientBuilder.build());
                retrofit = retrofitBuilder.build();

        }

        return retrofit.create(serviceClass);
    }

}
