package com.plan_app.android_plan_app.data.source;


import android.support.annotation.NonNull;

import com.plan_app.android_plan_app.data.Comment;

import java.util.List;

public interface CommentDataSource {

    interface LoadCommentsCallback {

        void onCommentsLoaded(List<Comment> comments);

        void onDataNotAvailable();
    }

    interface GetCommentCallback {

        void onCommentLoaded(Comment comment);

        void onDataNotAvailable();
    }

    void getComments(@NonNull LoadCommentsCallback callback, @NonNull  Integer taskId);

    void getComment(@NonNull String commentId, @NonNull GetCommentCallback callback);

    void saveComment(@NonNull Comment comment, @NonNull Integer taskId);

    void refreshComments();

    //void deleteAllComments();

    void deleteComment(@NonNull Integer commentId);
}
