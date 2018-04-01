package repository.activity;

import model.Activity;

import java.util.List;

public interface ActivityRepository {

    boolean create(Activity activity);

    List<Activity> findActivitiesForUser(Long userID);

}
