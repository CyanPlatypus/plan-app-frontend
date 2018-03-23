package com.plan_app.android_plan_app;

import android.annotation.TargetApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.plan_app.android_plan_app.Helpers.EmptyCallback;
import com.plan_app.android_plan_app.adapters.MyAdapter;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import dto.TaskDto;
import dto.UserDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter(myDataset);
        //mRecyclerView.setAdapter(mAdapter);

//        TreeNode root = TreeNode.root();
//
//        TreeNode parent = new TreeNode("Bs");
//        TreeNode child0 = new TreeNode("Bob");
//        TreeNode child1 = new TreeNode("Bella");
//
//        TreeNode who = new TreeNode("Who");
//        TreeNode p = new TreeNode("Pablo");
//        TreeNode pd = new TreeNode("Pablo Diego");
//        TreeNode pdf = new TreeNode("Pablo Diego Francisco");
//
//        pd.addChild(pdf);
//        p.addChild(pd);
//        who.addChild(p);
//
//        parent.addChildren(child0, child1);
//
//        root.addChildren(who, parent);
//
//        AndroidTreeView tView = new AndroidTreeView(this, root);
//        tView.setDefaultAnimation(true);
//        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
//        ViewGroup containerView = (ViewGroup) findViewById(R.id.my_view_group);
//        //linlay
//        LinearLayout ll =  (LinearLayout) findViewById(R.id.linlay);
        //containerView.addView(tView.getView());

    }

    private static Retrofit getRetrofit(){
        return  new Retrofit.Builder()
                .baseUrl("http://192.168.0.13:8080") //10.0.2.2:8080
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

    }

    public void getTask(View view){
        Retrofit retrofit = getRetrofit();
        PlanService service = retrofit.create(PlanService.class);
        Call<Iterable<TaskDto>> call =  service.getTasks(1);





        call.enqueue(new Callback<Iterable<TaskDto>>() {
            @Override
            public void onResponse(Call<Iterable<TaskDto>> call, Response<Iterable<TaskDto>> response) {

                if (response.isSuccessful()){

                    Iterable<TaskDto> users = response.body();

                    ArrayList<TaskDto> us = new ArrayList<>();

                    for(TaskDto e: users) {
                        us.add(e);
                    }



                    //mAdapter = new MyAdapter(us);
                    //mRecyclerView.setAdapter(mAdapter);

                    DisplayTree(us);
                }
            }

            @Override
            public void onFailure(Call<Iterable<TaskDto>> call, Throwable t) {
            }
        });





    }

    private void DisplayTree(ArrayList<TaskDto> us){
        TreeNode root = TreeNode.root();
        for(TaskDto task:us){
            root.addChild(BuildNode(task));
        }

        AndroidTreeView tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        ViewGroup containerView = (ViewGroup) findViewById(R.id.my_view_group);
        ViewGroup cView = (ViewGroup) findViewById(R.id.linlay);
        cView.addView(tView.getView());
    }

    private TreeNode BuildNode(TaskDto task)
    {
        TreeNode node = new TreeNode(task.getName());

        for (TaskDto child: task.getInnerTasks()){
            node.addChild(BuildNode(child));
        }
        return node;
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
