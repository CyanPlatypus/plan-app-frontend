package com.plan_app.android_plan_app.task_info;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.plan_app.android_plan_app.R;
import com.plan_app.android_plan_app.data.source.TasksRepository;


public class TaskInfoActivity extends AppCompatActivity {

    public static String EXTRA_TASK_ID = "TASK_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasksinfo_activity);
        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested task id
        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);

        TaskInfoFragment taskInfoFragment = (TaskInfoFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (taskInfoFragment == null) {
            taskInfoFragment = TaskInfoFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, taskInfoFragment);
            transaction.commit();
        }

        // Create the presenter
        new TaskInfoPresenter(
                taskInfoFragment,
                TasksRepository.getInstance(),
                taskId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
