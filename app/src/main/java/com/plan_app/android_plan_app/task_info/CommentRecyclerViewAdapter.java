package com.plan_app.android_plan_app.task_info;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plan_app.android_plan_app.R;
import com.plan_app.android_plan_app.data.Comment;
import com.plan_app.android_plan_app.tasks.TasksFragment;

import java.util.List;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {

    private List<Comment> mComments;
    //private final TasksFragment.OnListFragmentInteractionListener mListener;

    public CommentRecyclerViewAdapter(/*TasksFragment.OnListFragmentInteractionListener listener,*/
                                      List<Comment> comments) {
        mComments = comments;
        //mListener = listener;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comment, parent, false);
        return new CommentRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecyclerViewAdapter.ViewHolder holder, int position) {
        Comment comment =  mComments.get(position);
        holder.mItem =comment;
        holder.mUserName.setText("Tom");
        holder.mCommentName.setText(comment.getName());
        holder.mCommentText.setText(comment.getDescription());

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onTaskClick(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUserName;
        public final TextView mCommentName;
        public final TextView mCommentText;
        public Comment mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserName = view.findViewById(R.id.user_name);
            mCommentName = view.findViewById(R.id.comment_name);
            mCommentText = view.findViewById(R.id.comment_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUserName.getText() + "'";
        }
    }

}
