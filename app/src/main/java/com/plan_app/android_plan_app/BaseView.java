package com.plan_app.android_plan_app;


import android.support.annotation.NonNull;

public interface BaseView<T> {
  void setPresenter (@NonNull T presenter);
}
