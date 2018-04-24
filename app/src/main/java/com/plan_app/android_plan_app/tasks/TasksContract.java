package com.plan_app.android_plan_app.tasks;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.BasePresenter;
import com.plan_app.android_plan_app.BaseView;
import com.plan_app.android_plan_app.data.Task;

import java.util.List;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

//        void setLoadingIndicator(boolean active);

        void showTasks(List<Task> tasks);

        void showAddTask();
//
        void showTaskInfo(String taskId);
//
//        void showTaskMarkedComplete();
//
//        void showTaskMarkedActive();
//
//        void showCompletedTasksCleared();
//
//        void showLoadingTasksError();

        void showNoTasks();

//        void showNoActiveTasks();
//
//        void showNoCompletedTasks();
//
        void showSuccessfullySavedMessage();
//
        boolean isActive();
//
//        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter {

        void onAddActivityResult(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void openTaskInfo(@NonNull Task task);

        void addNewTask();
//
//        void completeTask(@NonNull Task completedTask);
//
//        void activateTask(@NonNull Task activeTask);
//
//        void clearCompletedTasks();
    }
}
