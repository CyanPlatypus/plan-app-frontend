package com.plan_app.android_plan_app.task_add_edit;

import android.support.annotation.Nullable;

import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.TasksDataSource;
import com.plan_app.android_plan_app.data.source.TasksRepository;


public class TaskAddEditPresenter implements TaskAddEditContract.Presenter {

    private TaskAddEditContract.View mView;

    private TasksRepository mTasksRepository;

    private String mTaskId;

    public TaskAddEditPresenter(TaskAddEditContract.View view,
                                TasksRepository tasksRepository,
                                @Nullable String taskId) {
        mView = view;
        mTasksRepository = tasksRepository;
        mTaskId = taskId;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!isNewTask()) {
            populateTask();
        }
    }

    @Override
    public void saveTask(String name, String description, String actualHours, String plannedHours) {
        Task task;
        if (mTaskId == null) {
            task = new Task(name,
                    description,
                    Integer.parseInt(plannedHours),
                    Integer.parseInt(actualHours),
                    false);
        }
        else {
            task = new Task(name,
                    description,
                    mTaskId,
                    Integer.parseInt(plannedHours),
                    Integer.parseInt(actualHours),
                    false);
        }
        mTasksRepository.saveTask(task);

        mView.showTasksList();
    }


    public void populateTask() {
        if (isNewTask()) {
            throw new RuntimeException("try to populate task which is new");
        }
        mTasksRepository.getTask(mTaskId, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                if (!mView.isActive())
                    return;
                mView.setName(task.getName());
                mView.setDescription(task.getDescription());
                mView.setActualHours(Integer.toString(task.getActualHours()));
                mView.setPlannedHours(Integer.toString(task.getPlannedHours()));
            }

            @Override
            public void onDataNotAvailable() {
                if (!mView.isActive())
                    return;
                mView.showTaskLoadingError();
            }
        });
    }

    private boolean isNewTask() {
        return mTaskId == null;
    }
}
