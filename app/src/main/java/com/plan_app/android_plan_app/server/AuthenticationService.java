package com.plan_app.android_plan_app.server;

import android.util.Base64;

import com.plan.dto.TaskDto;
import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.server.remote_api_service.PlanService;
import com.plan_app.android_plan_app.server.remote_api_service.ServiceGenerator;
import com.plan_app.android_plan_app.server.response.AuthenticationResponse;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Ella on 22.04.2018.
 */

public class AuthenticationService {

    private static final String CLIENT_CREDENTIALS = okhttp3
            .Credentials.basic("my-trusted-client","secret");
            //Base64.encodeToString("my-trusted-client:secret".getBytes(), Base64.DEFAULT);
            //android.util.Base64.encode("my-trusted-client:secret".getBytes(), android.util.Base64.DEFAULT).toString();

    private String tokenWithType;

    private static AuthenticationService INSTANCE;

    public static AuthenticationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationService();
        }
        return INSTANCE;
    }

    public String getTokenWithType(){
        return tokenWithType;
    }

    public  void logIn(String login, String pass, SignInCallback callback)
    {
        PlanService planService = ServiceGenerator
                .createService(PlanService.class, CLIENT_CREDENTIALS);//bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0
        Call<AuthenticationResponse> call = planService.signIn("password", login, pass);

        call.enqueue(new Callback<AuthenticationResponse>() {

            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {

                if (response.isSuccessful()){

                    AuthenticationResponse auth = response.body();

                    tokenWithType = "Bearer " + auth.getAccess_token();

                    callback.onSuccess();
                }
                else
                    callback.onFailure();
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}
