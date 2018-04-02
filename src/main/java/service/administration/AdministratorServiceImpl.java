package service.administration;

import model.Activity;
import model.Client;
import model.Role;
import model.User;
import model.validator.UserValidator;
import repository.EntityNotFoundException;
import repository.activity.ActivityRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserAuthenticationException;
import repository.user.UserRepository;
import service.Notification;
import service.PasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdministratorServiceImpl implements AdministratorService {

    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;
    private ActivityRepository activityRepository;

    public AdministratorServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.activityRepository = activityRepository;
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
    public List<Activity> generateReportForUser(Long userID, Date dateFrom, Date dateTo) {
        return activityRepository.findActivitiesForUser(userID)
                .parallelStream()
                .filter(a -> dateFrom.compareTo(a.getDate()) <= 0 && dateTo.compareTo(a.getDate()) >= 0)
                .collect(Collectors.toList());
    }

    @Override
    public Notification update(Long userID, User newUser) {
        Notification notification = new Notification();
        UserValidator userValidator = new UserValidator(newUser);
        if (!userValidator.validateUsername(newUser.getUsername())) {
            notification.setErrors(userValidator.getErrors());
            return notification;
        }
        if (userRepository.update(userID, newUser)) {
            notification.setMessage("User " + userID + " updated successfully!");
        } else {
            notification.addError("MYSQL error!");
        }
        return notification;
    }

    @Override
    public Notification delete(Long userID) {
        Notification notification = new Notification();
        try {
            if (userRepository.delete(userID)) {
                notification.setMessage("User " + userID + " deleted successfully!");
            } else {
                notification.addError("MYSQL error!");
            }
        } catch (EntityNotFoundException e) {
            notification.addError("User with id " + userID + " not found!");
        }
        return notification;
    }

    @Override
    public User findUserById(Long userID) throws EntityNotFoundException {
        return userRepository.findById(userID);
    }
}
