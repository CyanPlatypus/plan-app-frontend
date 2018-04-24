package com.plan_app.android_plan_app.data.source.remote;

import android.support.annotation.NonNull;

import com.plan.dto.TaskDto;
import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.TasksDataSource;
import com.plan_app.android_plan_app.server.remote_api_service.PlanService;
import com.plan_app.android_plan_app.server.remote_api_service.ServiceGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoteTasksDataSource implements TasksDataSource {

    private static RemoteTasksDataSource INSTANCE;

    private String tokenWithType;

    public static RemoteTasksDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteTasksDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        PlanService planService = ServiceGenerator
                .createService(PlanService.class);
        Call<Iterable<TaskDto>> call = planService.getTasks();

        call.enqueue(new Callback<Iterable<TaskDto>>() {
            @Override
            public void onResponse(@NonNull Call<Iterable<TaskDto>> call, @NonNull Response<Iterable<TaskDto>> response) {

                if (response.isSuccessful()){

                    Iterable<TaskDto> tasksDto = response.body();

                    ArrayList<Task> tasks = new ArrayList<>();

                    for(TaskDto tDto: tasksDto) {
                        Task t = new Task(tDto.getName(),
                                tDto.getDescription(), tDto.getId().toString(),
                                0, 0, false);

                        tasks.add(t);
                    }

                    callback.onTasksLoaded(tasks);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Iterable<TaskDto>> call, @NonNull Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
