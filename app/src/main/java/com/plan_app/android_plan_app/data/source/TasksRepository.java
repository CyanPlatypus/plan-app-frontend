package com.plan_app.android_plan_app.data.source;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksRepository implements TasksDataSource {
    private static TasksRepository INSTANCE = null;

    private Map<String, Task> mCache;

    private TasksRepository() {
        mCache = new HashMap<>();
        mCache.put("1", new Task("Task1", "Task1 description", "1", 10,15, true));
        mCache.put("2", new Task("Task2", "Task2 description", "2", 13, 5, false));
        mCache.put("3", new Task("Task3", "Task3 description", "3", 5, 5, true));
        mCache.put("4", new Task("Task4", "Task4 description", "4", 12 ,32, false));
        mCache.put("5", new Task("Task5", "Task5 description", "5", 1, 2, true));

    }

    public static TasksRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository();
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        callback.onTasksLoaded(new ArrayList<>(mCache.values()));
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
        mCache.put(task.getId(), task);
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
        mCache.remove(taskId);
    }
}
