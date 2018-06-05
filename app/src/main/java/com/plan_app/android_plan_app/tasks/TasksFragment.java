package com.plan_app.android_plan_app.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plan_app.android_plan_app.R;
import com.plan_app.android_plan_app.data.Task;
import com.plan_app.android_plan_app.task_add_edit.TaskAddEditActivity;
import com.plan_app.android_plan_app.task_info.TaskInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TasksFragment extends Fragment implements TasksContract.View {

    private TasksContract.Presenter mPresenter;

    private TaskRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TasksFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new TaskRecyclerViewAdapter(new OnListFragmentInteractionListener() {
                @Override
                public void onTaskClick(Task task) {
                    mPresenter.openTaskInfo(task);
                }
            }, new ArrayList<>());
            recyclerView.setAdapter(mAdapter);
        }

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_task);
        fab.setOnClickListener(v -> {
            mPresenter.addNewTask();
        });
        return view;
    }


    @Override
    public void setPresenter(@NonNull TasksContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showTasks(List<Task> tasks) {
        mAdapter.setTasks(tasks);
    }

    @Override
    public void showNoTasks() {
        mAdapter.setTasks(new ArrayList<>());
    }

    @Override
    public void showTaskInfo(String taskId) {
        Intent intent = new Intent(getContext(), TaskInfoActivity.class);
        intent.putExtra(TaskInfoActivity.EXTRA_TASK_ID, taskId);
        startActivity(intent);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(getContext(), TaskAddEditActivity.class);
        startActivityForResult(intent, TaskAddEditActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        Snackbar.make(getView(), getString(R.string.task_succesfully_saved), Snackbar.LENGTH_LONG).show();
    }

    interface OnListFragmentInteractionListener {
        void onTaskClick(Task task);
    }
}
