package repository.activity;

import model.Activity;
import model.builder.ActivityBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepositoryMySQL implements ActivityRepository {

    private final Connection connection;

    public ActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Activity activity) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO activity values (null,?,?,?,?);");

            preparedStatement.setLong(1, activity.getUserID());
            preparedStatement.setString(2, activity.getAction());
            preparedStatement.setString(3, activity.getDescription());
            preparedStatement.setDate(4, activity.getDate());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Activity> findActivitiesForUser(Long userID) {
        List<Activity> activities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM activity WHERE user_id=?;");
            preparedStatement.setLong(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Activity activity = new ActivityBuilder()
                        .setId(resultSet.getLong("id"))
                        .setUserId(resultSet.getLong("user_id"))
                        .setAction(resultSet.getString("action"))
                        .setDescription(resultSet.getString("description"))
                        .setDate(resultSet.getDate("activityDate"))
                        .build();
                activities.add(activity);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }
}
