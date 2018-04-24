package com.plan_app.android_plan_app.task_add_edit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.plan_app.android_plan_app.R;


public class TaskAddEditFragment extends Fragment implements TaskAddEditContract.View {

    private TaskAddEditContract.Presenter mPresenter;

    private EditText mName;

    private EditText mDescription;

    private EditText mActualHours;

    private EditText mPlannedHours;

    public TaskAddEditFragment() {}

    public static TaskAddEditFragment newInstance() {
        return new TaskAddEditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_taskaddedit, container, false);
        mName = root.findViewById(R.id.name);
        mDescription = root.findViewById(R.id.description);
        mActualHours = root.findViewById(R.id.actualHours);
        mPlannedHours = root.findViewById(R.id.plannedHours);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_task_done);

        fab.setOnClickListener(v -> {
            mPresenter.saveTask(mName.getText().toString(),
                                mDescription.getText().toString(),
                                mActualHours.getText().toString(),
                                mPlannedHours.getText().toString());
        });

        return root;
    }


    @Override
    public void setPresenter(@NonNull TaskAddEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showTasksList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setName(@NonNull String name) {
        mName.setText(name);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void setActualHours(@NonNull String actual) {
        mActualHours.setText(actual);
    }

    @Override
    public void setPlannedHours(@NonNull String planned) {
        mPlannedHours.setText(planned);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showTaskLoadingError() {
        Snackbar.make(mName, getString(R.string.loading_error_text), Snackbar.LENGTH_LONG).show();
    }
}
