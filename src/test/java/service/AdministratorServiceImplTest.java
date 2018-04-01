package service;

import database.Boostrap;
import database.Constants;
import database.JDBConnectionWrapper;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.administration.AdministratorService;
import service.administration.AdministratorServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

public class AdministratorServiceImplTest {

    private static AdministratorService administrationService;

    private static Long USER_ID;
    private static Long DELETED_ID;

    private static void initDatabase() {
        User user1 = new UserBuilder()
                .setUsername("test@test.org")
                .setPassword("pass12345@")
                .build();
        administrationService.create(user1, Collections.singletonList(Constants.Roles.EMPLOYEE));
        USER_ID = administrationService.findAllUsers().get(0).getId();

        User user2 = new UserBuilder()
                .setUsername("deleted@deleted.com")
                .setPassword("deleted123$")
                .build();
        administrationService.create(user2, Collections.singletonList(Constants.Roles.EMPLOYEE));
        DELETED_ID = administrationService.findAllUsers().get(1).getId();
    }

    @BeforeClass
    public static void setup() throws SQLException {
        Boostrap.setup();
        Connection connection = new JDBConnectionWrapper(Constants.Schemas.JUNIT_TEST).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        ActivityRepository activityRepository = new ActivityRepositoryMySQL(connection);
        administrationService = new AdministratorServiceImpl(userRepository, rightsRolesRepository, activityRepository);
        initDatabase();
    }

    @Test
    public void createUser() {
        User user = new UserBuilder()
                .setUsername("newuser@newuser.com")
                .setPassword("newuser123@")
                .build();
        Notification notification = administrationService.create(user, Collections.singletonList(Constants.Roles.EMPLOYEE));
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());
    }

    @Test
    public void updateUser() {
        User newUser = new UserBuilder()
                .setUsername("newbie@new.com")
                .setPassword("newnew123$")
                .build();
        Notification notification = administrationService.update(USER_ID, newUser);
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteUser() throws EntityNotFoundException {
        Notification notification = administrationService.delete(DELETED_ID);
        administrationService.findUserById(DELETED_ID);
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());
    }
    @Test
    public void findUserById() throws EntityNotFoundException {
        User user = administrationService.findUserById(USER_ID);
        Assert.assertTrue(user != null);
    }

}
