package com.plan_app.android_plan_app;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ella on 10.03.2018.
 */

public interface PlanService {
    @GET("plan/hello/")
    Call<ResponseBody> getHello();
}
