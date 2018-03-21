package com.plan_app.android_plan_app;

import java.util.ArrayList;

import dto.TaskDto;
import dto.UserDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ella on 10.03.2018.
 */

public interface PlanService {
    @GET("plan/hello/")
    Call<ResponseBody> getHello();
    @GET("plan/users/")
    Call<ArrayList<UserDto>> getUsers();
    @GET("plan/user/{id}/tasks")
    Call<Iterable<TaskDto>> getTasks(@Path("id") Integer id);
}
