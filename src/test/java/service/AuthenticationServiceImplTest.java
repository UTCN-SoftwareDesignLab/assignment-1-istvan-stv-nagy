package service;

import database.Boostrap;
import database.Constants;
import database.JDBConnectionWrapper;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserAuthenticationException;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthenticationServiceImplTest {

    private static final String VALID_USERNAME = "tester@test.com";
    private static final String VALID_PASSWORD = "valid123$";

    private static AuthenticationService authenticationService;

    @BeforeClass
    public static void setup() throws SQLException {
        Boostrap.setup();
        Connection connection = new JDBConnectionWrapper(Constants.Schemas.JUNIT_TEST).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
    }

    @Test
    public void register() {
        Notification notification = authenticationService.register(VALID_USERNAME, VALID_PASSWORD);
        Assert.assertTrue(!notification.hasErrors());
    }

    @Test
    public void login() throws UserAuthenticationException {
        User user = authenticationService.login(VALID_USERNAME, VALID_PASSWORD);
        Assert.assertTrue(user != null);
    }

}
