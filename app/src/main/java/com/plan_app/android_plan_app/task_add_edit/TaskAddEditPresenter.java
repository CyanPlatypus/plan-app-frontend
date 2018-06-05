package com.plan_app.android_plan_app.task_add_edit;

import android.support.annotation.Nullable;

import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.TasksDataSource;
import com.plan_app.android_plan_app.data.source.TasksRepository;

import java.util.Objects;


public class TaskAddEditPresenter implements TaskAddEditContract.Presenter {

    private TaskAddEditContract.View mView;

    private TasksRepository mTasksRepository;

    private Task mTask = null;

    public TaskAddEditPresenter(TaskAddEditContract.View view,
                                TasksRepository tasksRepository,
                                @Nullable String taskId) {
        mView = view;
        mTasksRepository = tasksRepository;
        if (taskId != null) {
            mTasksRepository.getTask(taskId, new TasksDataSource.GetTaskCallback() {
                @Override
                public void onTaskLoaded(Task task) {
                    mTask = task;
                }

                @Override
                public void onDataNotAvailable() {
                }
            });
        }
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
        if (actualHours.equals(""))
            actualHours = "0";
        if (plannedHours.equals(""))
            plannedHours = "0";
        if (isNewTask()) {
            mTask = new Task(name,
                    description,
                    Integer.parseInt(plannedHours),
                    Integer.parseInt(actualHours),
                    false);
        }
        else {
            mTask.setActualHours(Integer.parseInt(actualHours));
            mTask.setPlannedHours(Integer.parseInt(plannedHours));
            mTask.setDescription(description);
            mTask.setName(name);
        }
        mTasksRepository.saveTask(mTask);

        mView.showTasksList();
    }


    public void populateTask() {
        if (isNewTask()) {
            throw new RuntimeException("try to populate task which is new");
        }
        mView.setName(mTask.getName());
        mView.setDescription(mTask.getDescription());
        mView.setActualHours(Integer.toString(mTask.getActualHours()));
        mView.setPlannedHours(Integer.toString(mTask.getPlannedHours()));
    }

    private boolean isNewTask() {
        return mTask == null;
    }
}
