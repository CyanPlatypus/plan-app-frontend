package com.plan_app.android_plan_app;

import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.plan_app.android_plan_app.Helpers.EmptyCallback;

import java.util.ArrayList;
import java.util.stream.Collectors;

import dto.UserDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static Retrofit getRetrofit(){
        return  new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080") //10.0.2.2:8080
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

    }

    //@TargetApi(24)
    public void getUsersButtonClicked(View view){
        Retrofit retrofit = getRetrofit();
        PlanService service = retrofit.create(PlanService.class);
        Call<ArrayList<UserDto>> call =  service.getUsers();

        call.enqueue(new Callback<ArrayList<UserDto>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDto>> call, Response<ArrayList<UserDto>> response) {

                if (response.isSuccessful()){

                    ArrayList<UserDto> users = response.body();//.forEach( a-> a.getFirstName());
                    //users.forEach( a-> a.getFirstName());

                    EditText editText = (EditText) findViewById(R.id.text_input);
                    //String.join("; ", users);

//                    String commaSeparatedNumbers = users.stream()
//                            .map(i -> i.getFirstName())
//                            .collect(Collectors.joining(", "));


                    editText.setText(users.get(0).getFirstName());
                    //JsonObject post = new JsonObject().get(response.body().toString()).getAsJsonObject();
                    //if (post.get("Level").getAsString().contains("Administrator")) {

                //}
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserDto>> call, Throwable t) {
            }
        });
    }

    public void okButtonClicked(View view){

        Retrofit retrofit = getRetrofit();

        PlanService service = retrofit.create(PlanService.class);

        Call<ResponseBody> call =  service.getHello();
        call.enqueue(new EmptyCallback<ResponseBody>(MainActivity.this));

//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                if (response.isSuccessful()){
//
//                    JsonObject post = new JsonObject().get(response.body().toString()).getAsJsonObject();
//                    if (post.get("Level").getAsString().contains("Administrator")) {
//
//                }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//            }
//        });

        //String message = resp.body().toString();

        //EditText editText = (EditText) findViewById(R.id.text_input);
        //String message = editText.getText().toString();

        //Toast.makeText(MainActivity.this, message,
        //        Toast.LENGTH_LONG).show();


    }
}
