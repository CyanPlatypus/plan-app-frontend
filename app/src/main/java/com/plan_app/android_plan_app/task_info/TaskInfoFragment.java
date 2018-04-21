package com.plan_app.android_plan_app.task_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plan_app.android_plan_app.R;


public class TaskInfoFragment extends Fragment implements TaskInfoContract.View {

    private TextView mName;

    private TextView mDescription;

    private TextView mHours;

    private TaskInfoContract.Presenter mPresenter;

    @Override
    public void setPresenter(@NonNull TaskInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public TaskInfoFragment() {
        // Required empty public constructor
    }

    public static TaskInfoFragment newInstance() {
        TaskInfoFragment fragment = new TaskInfoFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task_info, container, false);
        setHasOptionsMenu(true);
        mName = root.findViewById(R.id.name);
        mDescription = root.findViewById(R.id.description);
        mHours = root.findViewById(R.id.hours);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_task);

        fab.setOnClickListener(v -> mPresenter.editTask());

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.taskinfo_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                mPresenter.deleteTask();
                return true;
        }
        return false;
    }
    @Override
    public void showName(String name) {
        mName.setText(name);
        mName.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideName() {
        mName.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(String description) {
        mDescription.setText(description);
        mDescription.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDescription() {
        mDescription.setVisibility(View.GONE);
    }

    @Override
    public void showHours(String actual, String planned) {
        mHours.setText(actual + "/" + planned);
    }

    @Override
    public void showTaskDeleted() {
        getActivity().finish();
    }

    @Override
    public void showTaskEdit(@NonNull String taskId) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(active) {
            mName.setText("");
            mHours.setText("");
            mDescription.setText(R.string.loading_text);
        }
    }

    @Override
    public void showTaskLoadingError() {
        mName.setText("");
        mHours.setText("");
        mDescription.setText(R.string.loading_error_text);
    }

}
