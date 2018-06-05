package com.plan_app.android_plan_app;

import android.app.Application;

import io.realm.Realm;


public class PlanningApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
