package com.plan_app.android_plan_app.data;

import com.plan.dto.CommentDto;

/**
 * Created by Ella on 05.06.2018.
 */

public class Comment {

    private Integer remoteId;
    private String name;
    private String description;
    private double hours = 0.0;

    public Integer getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Integer remoteId) {
        this.remoteId = remoteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }


}
