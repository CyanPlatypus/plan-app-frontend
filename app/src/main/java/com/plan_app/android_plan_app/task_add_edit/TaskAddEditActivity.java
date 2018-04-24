package com.plan_app.android_plan_app.task_add_edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.plan_app.android_plan_app.R;
import com.plan_app.android_plan_app.data.source.TasksRepository;

public class TaskAddEditActivity extends AppCompatActivity {

    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";

    public static final int REQUEST_ADD_TASK= 1;

    public static final int REQUEST_EDIT_TASK = 2;

    private TaskAddEditPresenter mPresenter;

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskaddedit);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        TaskAddEditFragment addEditTaskFragment = (TaskAddEditFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        String taskId = getIntent().getStringExtra(ARGUMENT_EDIT_TASK_ID);

        setToolbarTitle(taskId);

        if (addEditTaskFragment == null) {
            addEditTaskFragment = TaskAddEditFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, addEditTaskFragment);
            transaction.commit();
        }

        // Create the presenter
        mPresenter = new TaskAddEditPresenter(
                addEditTaskFragment,
                TasksRepository.getInstance(),
                taskId
        );
    }

    private void setToolbarTitle(@Nullable String taskId) {
        if(taskId == null) {
            mActionBar.setTitle(R.string.add_task);
        } else {
            mActionBar.setTitle(R.string.edit_task);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
