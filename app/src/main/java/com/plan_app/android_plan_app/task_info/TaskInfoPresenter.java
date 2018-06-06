package com.plan_app.android_plan_app.task_info;

import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Comment;
import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.data.source.CommentDataSource;
import com.plan_app.android_plan_app.data.source.TasksDataSource;
import com.plan_app.android_plan_app.data.source.TasksRepository;
import com.plan_app.android_plan_app.data.source.remote.RemoteCommentDataSource;

import java.util.List;


public class TaskInfoPresenter implements TaskInfoContract.Presenter {
    private TaskInfoContract.View mView;

    private TasksRepository mTaskRepository;

    private String mTaskId;
    private Integer mTaskRepositoryId;

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

                mTaskRepositoryId = task.getRemoteId();

                if (mView.isActive()) {

                    RemoteCommentDataSource.getInstance().getComments(
                            new CommentDataSource.LoadCommentsCallback() {
                                @Override
                                public void onCommentsLoaded(List<Comment> comments) {
                                    mView.showComments(comments);
                                }

                                @Override
                                public void onDataNotAvailable() {

                                }
                            },mTaskRepositoryId );

                    mView.setLoadingIndicator(false);
                    showTask(task);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (!mView.isActive())
                    return;
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
        mView.showTaskEdit(mTaskId);
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
