package com.plan_app.android_plan_app.data.source;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.remote.RemoteTasksDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class TasksRepository implements TasksDataSource {
    private static TasksRepository INSTANCE = null;

    private TasksRepository() {
//        mCache = new HashMap<>();
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
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Task> tasks = realm.where(Task.class).findAll();
        callback.onTasksLoaded(tasks);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
        if (task != null) {
            callback.onTaskLoaded(realm.copyFromRealm(task));
        }
        else {
            callback.onDataNotAvailable();
        }
        realm.close();
    }

    @Override
    public void saveTask(@NonNull Task task) {
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(Task.class).equalTo("id", task.getId()).findFirst() != null) {
            RemoteTasksDataSource.getInstance().editTask(task);
        }
        else {
            RemoteTasksDataSource.getInstance().saveTask(task);
        }
        realm.executeTransaction(realm1 -> {
            realm1.insertOrUpdate(task);
        });
        realm.close();
    }


    @Override
    public void refreshTasks() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Task> tasks = realm.where(Task.class).isNull("remoteId").findAll();
        RemoteTasksDataSource.getInstance().getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                realm.executeTransaction(realm1 -> {
                    realm1.insertOrUpdate(tasks);
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

    @Override
    public void deleteAllTasks() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.deleteAll();
        });
        realm.close();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        Realm realm = Realm.getDefaultInstance();
        Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
        if (task != null) {
            Integer remoteId = task.getRemoteId();
            realm.executeTransaction(realm1 -> {
                task.deleteFromRealm();
            });
            if (remoteId != null) {
                RemoteTasksDataSource.getInstance().deleteTask(remoteId);
            }
        }
        realm.close();
    }
}
