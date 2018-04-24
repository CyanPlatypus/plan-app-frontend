package com.plan_app.android_plan_app.server.remote_api_service;

import com.plan.dto.TaskDto;
import com.plan.dto.UserCreateRequestDto;
import com.plan_app.android_plan_app.server.response.AuthenticationResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface PlanService {

    @GET("/tasks")
    Call<Iterable<TaskDto>> getTasks();

    @POST("/oauth/token")
    Call<AuthenticationResponse> signIn(@Query("grant_type") String grantType,
                                        @Query("username") String username,
                                        @Query("password") String password);
    @POST("/signup")
    Call<ResponseBody> signUp(@Body UserCreateRequestDto dto);
}
