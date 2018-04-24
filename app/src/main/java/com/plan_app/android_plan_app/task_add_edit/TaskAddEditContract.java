package com.plan_app.android_plan_app.task_add_edit;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.BasePresenter;
import com.plan_app.android_plan_app.BaseView;


public interface TaskAddEditContract {
    interface View extends BaseView<Presenter> {
        void showTasksList();

        void setName(@NonNull String name);

        void setDescription(String description);

        void setActualHours(@NonNull String actual);

        void setPlannedHours(@NonNull String planned);

        boolean isActive();

        void showTaskLoadingError();
    }

    interface Presenter extends BasePresenter {
        void saveTask(String name, String description, String actualHours, String plannedHours);
    }
}
