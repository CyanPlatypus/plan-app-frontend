package com.plan_app.android_plan_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.plan_app.android_plan_app.Helpers.EmptyCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void okButtonClicked(View view){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080") //10.0.2.2:8080
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlanService service = retrofit.create(PlanService.class);

        Call<ResponseBody> call =  service.getHello();
        //call.enqueue(new EmptyCallback<ResponseBody>(MainActivity.this));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){

                    JsonObject post = new JsonObject().get(response.body().toString()).getAsJsonObject();
                    if (post.get("Level").getAsString().contains("Administrator")) {

                }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

        //String message = resp.body().toString();

        //EditText editText = (EditText) findViewById(R.id.text_input);
        //String message = editText.getText().toString();

        //Toast.makeText(MainActivity.this, message,
        //        Toast.LENGTH_LONG).show();


    }
}
