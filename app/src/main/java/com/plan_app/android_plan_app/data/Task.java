package com.plan_app.android_plan_app.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Task extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;

    @Nullable
    private String description;

    private int plannedHours;

    private Integer remoteId;

    private int actualHours;

    private boolean isCompleted;

    RealmList<Comment> comments;

    public Task() {
    }


    public Task(@NonNull String name, @Nullable String description, @NonNull String id,
                int plannedHours, int actualHours, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.plannedHours = plannedHours;
        this.actualHours = actualHours;
        this.isCompleted = isCompleted;
    }


    public Task(@NonNull String name, @Nullable String description,
                int plannedHours, int actualHours, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID().toString();
        this.plannedHours = plannedHours;
        this.actualHours = actualHours;
        this.isCompleted = isCompleted;
    }



    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public int getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(int plannedHours) {
        this.plannedHours = plannedHours;
    }

    public Integer getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Integer remoteId) {
        this.remoteId = remoteId;
    }

    public int getActualHours() {
        return actualHours;
    }

    public void setActualHours(int actualHours) {
        this.actualHours = actualHours;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public RealmList<Comment> getComments() {
        return comments;
    }

    public void setComments(RealmList<Comment> comments) {
        this.comments = comments;
    }
}
