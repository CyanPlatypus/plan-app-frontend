package com.plan_app.android_plan_app.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.TasksDataSource;
import com.plan_app.android_plan_app.data.source.TasksRepository;
import com.plan_app.android_plan_app.task_add_edit.TaskAddEditActivity;

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
                if (!mTasksView.isActive())
                    return;
                if (tasks.size() == 0) {
                    mTasksView.showNoTasks();
                }
                else {
                    mTasksView.showTasks(tasks);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (!mTasksView.isActive())
                    return;
                mTasksView.showNoTasks();
            }
        });
    }

    @Override
    public void openTaskInfo(@NonNull Task task) {
        mTasksView.showTaskInfo(task.getId());
    }

    @Override
    public void onAddActivityResult(int requestCode, int resultCode) {
        if (requestCode == TaskAddEditActivity.REQUEST_ADD_TASK
                && resultCode == Activity.RESULT_OK) {
            mTasksView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void addNewTask() {
        mTasksView.showAddTask();
    }
}
