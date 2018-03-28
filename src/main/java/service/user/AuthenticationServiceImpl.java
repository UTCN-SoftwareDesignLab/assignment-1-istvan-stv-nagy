package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserAuthenticationException;
import repository.user.UserRepository;
import service.Notification;
import service.PasswordEncoder;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public User login(String username, String password) throws UserAuthenticationException {
        User user =  userRepository.findByUsernameAndPassword(username, PasswordEncoder.encodePassword(password));
        List<Role> userRoles = rightsRolesRepository.findRolesForUser(user.getId());
        user.setRoles(userRoles);
        return user;
    }

    @Override
    public Notification register(String username, String password) {
        Role userRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(userRole))
                .build();

        Notification notification = new Notification();
        UserValidator userValidator = new UserValidator(user);
        if (userValidator.validate()) {
            user.setPassword(PasswordEncoder.encodePassword(password));
            try {
                userRepository.create(user);
                notification.setMessage("User registered");
            } catch (UserAuthenticationException e) {
                notification.addError(e.getMessage());
            }
        } else {
            notification.setErrors(userValidator.getErrors());
        }

        return notification;
    }


}
