package com.plan_app.android_plan_app.data.source;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Comment;
import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.remote.RemoteCommentDataSource;
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
            realm.executeTransaction(realm1 -> {
                realm1.insertOrUpdate(task);
            });
            RemoteTasksDataSource.getInstance().editTask(task);
        }
        else {
            RemoteTasksDataSource.getInstance().saveTask(task, new SaveTaskCallback() {
                @Override
                public void onTaskSaved(Integer remoteId) {
                    realm.executeTransaction(realm1 -> {
                        task.setRemoteId(remoteId);
                        realm1.insert(task);
                    });
                }
                @Override
                public void onDataNotAvailable() {
                    realm.executeTransaction(realm1 -> {
                        realm1.insert(task);
                    });
                }
            });
        }
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

    public void refreshComments(@NonNull String taskId) {
        Realm realm = Realm.getDefaultInstance();
        Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
        if (task != null && task.getRemoteId() != null) {
            RemoteCommentDataSource.getInstance().getComments(task.getRemoteId(), new CommentDataSource.LoadCommentsCallback() {
                @Override
                public void onCommentsLoaded(List<Comment> comments) {
                    realm.executeTransaction(realm1 -> {
                        for (Comment comment : comments) {
                            task.getComments().add(comment);
                        }
                    });
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }
    }

    public void getComments(@NonNull String taskId, CommentDataSource.LoadCommentsCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
        if (task == null) {
            callback.onDataNotAvailable();
        }
        else {
            callback.onCommentsLoaded(task.getComments());
        }
    }

    public void addComment(@NonNull String taskId, @NonNull Comment comment) {
        Realm realm = Realm.getDefaultInstance();
        Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
        realm.executeTransaction(realm1 -> {
            task.getComments().add(comment);
        });
        realm.close();
        RemoteCommentDataSource.getInstance().saveComment(comment, task.getRemoteId());
    }

    public void deleteComment(@NonNull Comment comment) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            comment.deleteFromRealm();
        });
        realm.close();
        RemoteCommentDataSource.getInstance().deleteComment(comment.getRemoteId());
    }
}
