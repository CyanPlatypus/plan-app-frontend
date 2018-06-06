package com.plan_app.android_plan_app.task_info;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.BasePresenter;
import com.plan_app.android_plan_app.BaseView;
import com.plan_app.android_plan_app.data.Comment;

import java.util.List;


public interface TaskInfoContract {

    interface View extends BaseView<TaskInfoContract.Presenter> {
        void showName(String name);

        void hideName();

        void showDescription(String description);

        void hideDescription();

        void showHours(String actual, String planned);

        void showTaskDeleted();

        void setLoadingIndicator(boolean active);

        void showTaskEdit(String taskId);

        void showTaskLoadingError();

        boolean isActive();

        void showComments (List<Comment> comments);
    }

    interface Presenter extends BasePresenter {
        void editTask();

        void deleteTask();

        void addHours();

        void addComment();
    }
}
