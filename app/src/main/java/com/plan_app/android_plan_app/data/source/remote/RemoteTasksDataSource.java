package com.plan_app.android_plan_app.data.source.remote;

import android.support.annotation.NonNull;

import com.plan.dto.TaskDto;
import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.TasksDataSource;
import com.plan_app.android_plan_app.server.remote_api_service.PlanService;
import com.plan_app.android_plan_app.server.remote_api_service.ServiceGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemoteTasksDataSource implements TasksDataSource {

    //singletone
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
//                        Task t = new Task(tDto.getName(),
//                                tDto.getDescription(), tDto.getId().toString(),
//                                (int) tDto.getPlannedHours(), (int) tDto.getPlannedHours(), tDto.isFinished());
//                        t.setRemoteId(tDto.getId());

                        tasks.add( ConvertToTask(tDto));
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
//        PlanService planService = ServiceGenerator.createService(PlanService.class);
//        Call<TaskDto> call = planService.getTask()
    }

    public void saveTask(@NonNull Task task, @NonNull SaveTaskCallback callback) {
        PlanService planService = ServiceGenerator.createService(PlanService.class);

        Call<ResponseBody> call = planService.addTask(ConvertToTaskDto(task));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Integer i = Integer.parseInt(response.body().string());
                    if (i != null)
                        callback.onTaskSaved(i);
                    else
                        callback.onDataNotAvailable();
                }
                catch (Exception e){
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task) {
        PlanService planService = ServiceGenerator.createService(PlanService.class);

        Call<ResponseBody> call = planService.addTask(ConvertToTaskDto(task));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void editTask(Task task){
        PlanService planService = ServiceGenerator.createService(PlanService.class);

        Call<ResponseBody> call = planService.editTask(ConvertToTaskDto(task));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
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

    public void deleteTask(Integer id){
        if(id == null) return ;

        PlanService planService = ServiceGenerator.createService(PlanService.class);

        Call<ResponseBody> call = planService.removeTask(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static TaskDto ConvertToTaskDto(Task task){
        TaskDto tDto = new TaskDto();
        tDto.setActualHours(task.getActualHours());
        tDto.setPlannedHours(task.getPlannedHours());
        tDto.setDescription(task.getDescription());
        tDto.setName(task.getName());
        tDto.setId(task.getRemoteId());
        return tDto;
    }

    public static Task ConvertToTask(TaskDto tDto){
        Task t = new Task(tDto.getName(), tDto.getDescription(),
                (int) tDto.getPlannedHours(), (int) tDto.getActualHours(), tDto.isFinished());
        t.setRemoteId(tDto.getId());
        return t;
    }
}
