package com.plan_app.android_plan_app.Helpers;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.plan_app.android_plan_app.MainActivity;
import com.plan_app.android_plan_app.R;

import java.io.IOException;

import classes.UserDto;
import retrofit2.Call;
import retrofit2.Callback;
import okhttp3.ResponseBody;

/**
 * Created by Ella on 10.03.2018.
 */

public class EmptyCallback<T> implements Callback<T> {

    protected Context _context;

    public EmptyCallback(Context con){
        _context = con;
    }

    @Override
    public void onResponse(Call<T> call, retrofit2.Response<T> response) {

        if (response.isSuccessful())
        {

            String message = null;
            try {
                message = ((ResponseBody)response.body()).string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(_context, message,
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String message =  t.getMessage();
        Toast.makeText(_context, message,
                Toast.LENGTH_LONG).show();
    }
}
