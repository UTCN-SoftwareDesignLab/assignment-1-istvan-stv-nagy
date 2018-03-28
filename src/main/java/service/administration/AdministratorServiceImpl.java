package service.administration;

import model.Client;
import model.Role;
import model.User;
import model.builder.ClientBuilder;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserAuthenticationException;
import repository.user.UserRepository;
import service.Notification;
import service.PasswordEncoder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorServiceImpl implements AdministratorService {

    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;

    public AdministratorServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification create(User user, List<String> roleNames) {
        Notification notification = new Notification();

        List<Role> roles = new ArrayList<>();
        for (String roleName : roleNames) {
            roles.add(rightsRolesRepository.findRoleByTitle(roleName));
        }
        user.setRoles(roles);

        UserValidator userValidator = new UserValidator(user);
        if (userValidator.validate()) {
            try {
                user.setPassword(PasswordEncoder.encodePassword(user.getPassword()));
                userRepository.create(user);
                notification.setMessage("User registered!");
            } catch (UserAuthenticationException e) {
                notification.addError(e.getMessage());
            }
        } else {
            notification.setErrors(userValidator.getErrors());
        }

        return notification;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public Notification update(Long userID, User newUser) {
        return null;
    }

    @Override
    public Notification delete(Long userID) {
        return null;
    }
}
