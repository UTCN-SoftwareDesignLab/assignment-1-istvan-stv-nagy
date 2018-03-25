package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserAuthenticationException;
import repository.user.UserRepository;
import service.Notification;

import java.security.MessageDigest;
import java.util.Collections;

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
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
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
        if (userValidator.validate(user)) {
            user.setPassword(encodePassword(password));
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

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
