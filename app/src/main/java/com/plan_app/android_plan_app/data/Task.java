package com.plan_app.android_plan_app.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.UUID;


@Entity(tableName = "tasks")
public class Task {

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @PrimaryKey
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @Nullable
    private final String description;

    private final int plannedHours;

    private Integer remoteId;

    public int getPlannedHours() {
        return plannedHours;
    }

    public int getActualHours() {
        return actualHours;
    }

    private final int actualHours;

    public boolean getIsCompleted() {
        return isCompleted;
    }

    private final boolean isCompleted;

    public Integer getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Integer remoteId) {
        this.remoteId = remoteId;
    }

    @Ignore
    public Task(@NonNull String name, @Nullable String description, @NonNull String id,
                int plannedHours, int actualHours, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.plannedHours = plannedHours;
        this.actualHours = actualHours;
        this.isCompleted = isCompleted;
    }

    @Ignore
    public Task(@NonNull String name, @Nullable String description,
                int plannedHours, int actualHours, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID().toString();
        this.plannedHours = plannedHours;
        this.actualHours = actualHours;
        this.isCompleted = isCompleted;
    }
}
