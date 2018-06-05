package com.plan_app.android_plan_app.data.source.remote;


import android.support.annotation.NonNull;

import com.plan.dto.CommentDto;
import com.plan_app.android_plan_app.data.Comment;
import com.plan_app.android_plan_app.data.source.CommentDataSource;
import com.plan_app.android_plan_app.server.remote_api_service.PlanService;
import com.plan_app.android_plan_app.server.remote_api_service.ServiceGenerator;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteCommentDataSource implements CommentDataSource {
    //singletone
    private static RemoteCommentDataSource INSTANCE;

    private String tokenWithType;

    public static RemoteCommentDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteCommentDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getComments(@NonNull LoadCommentsCallback callback, @NonNull  Integer taskId) {
        PlanService planService = ServiceGenerator
                .createService(PlanService.class);
        Call<Iterable<CommentDto>> call = planService.getTaskComments(taskId);

        call.enqueue(new Callback<Iterable<CommentDto>>() {
            @Override
            public void onResponse(@NonNull Call<Iterable<CommentDto>> call,
                                   @NonNull Response<Iterable<CommentDto>> response) {

                if (response.isSuccessful()){

                    Iterable<CommentDto> commentDto = response.body();

                    ArrayList<Comment> comments = new ArrayList<>();

                    for(CommentDto cDto: commentDto) {
                        comments.add( ConvertToComment(cDto));
                    }

                    callback.onCommentsLoaded(comments);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Iterable<CommentDto>> call, @NonNull Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getComment(@NonNull String commentId, @NonNull GetCommentCallback callback) {

    }

    @Override
    public void saveComment(@NonNull Comment comment, @NonNull Integer taskId) {
        PlanService planService = ServiceGenerator.createService(PlanService.class);

        Call<ResponseBody> call = planService.addComment(ConvertToCommentDto(comment), taskId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void refreshComments() {

    }

    @Override
    public void deleteComment(@NonNull Integer commentId) {
        if(commentId == null) return ;

        PlanService planService = ServiceGenerator.createService(PlanService.class);

        Call<ResponseBody> call = planService.removeComment(commentId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private static Comment ConvertToComment(CommentDto cDto){
        Comment comment = new Comment();
        comment.setRemoteId(cDto.getId());
        comment.setName(cDto.getName());
        comment.setDescription(cDto.getDescription());
        comment.setHours(cDto.getHours());
        return comment;
    }

    private static CommentDto ConvertToCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getRemoteId());
        commentDto.setName(comment.getName());
        commentDto.setDescription(comment.getDescription());
        commentDto.setHours(comment.getHours());
        return commentDto;
    }
}
