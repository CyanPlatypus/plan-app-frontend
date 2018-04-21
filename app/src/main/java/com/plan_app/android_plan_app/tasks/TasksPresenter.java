package com.plan_app.android_plan_app.tasks;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.TasksDataSource;
import com.plan_app.android_plan_app.data.source.TasksRepository;

import java.util.List;


public class TasksPresenter implements TasksContract.Presenter {
    private final TasksRepository mTasksRepository;

    private final TasksContract.View mTasksView;

    public TasksPresenter(TasksRepository tasksRepository, TasksContract.View tasksView) {
        mTasksRepository = tasksRepository;
        mTasksView = tasksView;
        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks(false);
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        if (forceUpdate) {
            mTasksRepository.refreshTasks();
        }
        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {

            @Override
            public void onTasksLoaded(List<Task> tasks) {
                if (tasks.size() == 0) {
                    mTasksView.showNoTasks();
                }
                else {
                    mTasksView.showTasks(tasks);
                }
            }

            @Override
            public void onDataNotAvailable() {
                mTasksView.showNoTasks();
            }
        });
    }

    @Override
    public void openTaskInfo(@NonNull Task task) {
        mTasksView.showTaskInfo(task.getId());
    }
}
