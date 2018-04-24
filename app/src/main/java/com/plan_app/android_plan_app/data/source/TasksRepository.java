package com.plan_app.android_plan_app.data.source;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.remote.RemoteTasksDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksRepository implements TasksDataSource {
    private static TasksRepository INSTANCE = null;

    private Map<String, Task> mCache;

    private TasksRepository() {
        mCache = new HashMap<>();
//        mCache.put("1", new Task("Task1", "Task1 description", "1", 10,15, true));
//        mCache.put("2", new Task("Task2", "Task2 description", "2", 13, 5, false));
//        mCache.put("3", new Task("Task3", "Task3 description", "3", 5, 5, true));
//        mCache.put("4", new Task("Task4", "Task4 description", "4", 12 ,32, false));
//        mCache.put("5", new Task("Task5", "Task5 description", "5", 1, 2, true));
    }

    public static TasksRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        //callback.onTasksLoaded(new ArrayList<>(mCache.values()));
        RemoteTasksDataSource.getInstance().getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                mCache.clear();
                for (Task t :tasks) {
                    if(!mCache.containsKey(t.getId()))
                        mCache.put(t.getId(), t);
                }

                callback.onTasksLoaded(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        Task task = mCache.get(taskId);
        if (task == null) {
            callback.onDataNotAvailable();
        }
        else {
            callback.onTaskLoaded(task);
        }
    }

    @Override
    public void saveTask(@NonNull Task task) {
        //edit
        if(mCache.containsKey(task.getId())){
            RemoteTasksDataSource.getInstance().editTask(task);
        }
        //add
        else{
        RemoteTasksDataSource.getInstance().saveTask(task);
        mCache.put(task.getId(), task);
        }
    }


    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {
        mCache.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        if(mCache.containsKey(taskId)){
            Integer id = mCache.get(taskId).getRemoteId();
            RemoteTasksDataSource.getInstance().deleteTask(id);
        }
        mCache.remove(taskId);
    }
}
