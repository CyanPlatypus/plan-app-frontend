package com.plan_app.android_plan_app.server.remote_api_service;

import com.plan.dto.TaskDto;
import com.plan_app.android_plan_app.server.response.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ella on 22.04.2018.
 */

public interface PlanService {

    @GET("/tasks")
    Call<Iterable<TaskDto>> getTasks();

    @POST("/oauth/token")
    Call<AuthenticationResponse> signIn(@Query("grant_type") String grantType,
                                        @Query("username") String username,
                                        @Query("password") String password);
}
