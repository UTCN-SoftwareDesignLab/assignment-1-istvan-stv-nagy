package model.builder;

import model.Activity;

import java.sql.Date;

public class ActivityBuilder {

    private Activity activity;

    public ActivityBuilder() {
        this.activity = new Activity();
    }

    public ActivityBuilder setId(Long id) {
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setUserId(Long userId) {
        activity.setUserID(userId);
        return this;
    }

    public ActivityBuilder setAction(String action) {
        activity.setAction(action);
        return this;
    }

    public ActivityBuilder setDescription(String description) {
        activity.setDescription(description);
        return this;
    }

    public ActivityBuilder setDate(Date date) {
        activity.setDate(date);
        return this;
    }

    public Activity build() {
        return activity;
    }

}
