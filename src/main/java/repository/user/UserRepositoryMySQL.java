package repository.user;

import model.Client;
import model.Role;
import model.User;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws UserAuthenticationException {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=? AND password=?;");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user =  new UserBuilder()
                        .setId(rs.getLong("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(rs.getLong("id")))
                        .build();
                for (Role r : user.getRoles())
                    user.addRights(rightsRolesRepository.findRightsForRole(r.getId()));
                return user;
            } else {
                throw new UserAuthenticationException("Invalid username or password!");
            }
        } catch (SQLException e) {
            throw new UserAuthenticationException("Failed to login!");
        }
    }

    @Override
    public boolean create(User user) throws UserAuthenticationException {
        try {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM users WHERE username =?;");
            selectStatement.setString(1, user.getUsername());
            ResultSet rs = selectStatement.executeQuery();
            if (rs.next()) {
                throw new UserAuthenticationException("Username already used by another user!");
            }

            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users values (null,?,?)");
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, user.getPassword());
            insertStatement.executeUpdate();

            rs = insertStatement.getGeneratedKeys();
            rs.next();
            Long userId = rs.getLong(1);
            user.setId(userId);
            rightsRolesRepository.addRolesToUser(user, user.getRoles());
            return true;
        } catch (SQLException e) {
            throw new UserAuthenticationException("Failed to register!");
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                User user = new UserBuilder()
                        .setId(rs.getLong("id"))
                        .setUsername(rs.getString("username"))
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
