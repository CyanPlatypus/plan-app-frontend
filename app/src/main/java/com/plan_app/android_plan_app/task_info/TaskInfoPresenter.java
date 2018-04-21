package com.plan_app.android_plan_app.task_info;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.TasksDataSource;
import com.plan_app.android_plan_app.data.source.TasksRepository;


public class TaskInfoPresenter implements TaskInfoContract.Presenter {
    private TaskInfoContract.View mView;

    private TasksRepository mTaskRepository;

    private String mTaskId;

    public TaskInfoPresenter(@NonNull TaskInfoContract.View view,
                             @NonNull TasksRepository tasksRepository,
                             @NonNull String taskId) {
        mView = view;
        mTaskRepository = tasksRepository;
        mTaskId = taskId;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        mView.setLoadingIndicator(true);
        mTaskRepository.getTask(mTaskId, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                mView.setLoadingIndicator(false);
                showTask(task);
            }

            @Override
            public void onDataNotAvailable() {
                mView.showTaskLoadingError();
            }
        });
    }

    private void showTask(@NonNull Task task) {
        String name = task.getName();
        String description = task.getDescription();
        String actual = Integer.toString(task.getActualHours());
        String planned = Integer.toString(task.getPlannedHours());
        mView.showName(name);
        if (description == null) {
            mView.hideDescription();
        }
        else {
            mView.showDescription(description);
        }
        mView.showHours(actual, planned);
    }

    @Override
    public void editTask() {

    }

    @Override
    public void deleteTask() {
        mTaskRepository.deleteTask(mTaskId);
        mView.showTaskDeleted();
    }

    @Override
    public void addHours() {

    }

    @Override
    public void addComment() {

    }
}
