package service.administration;

import model.User;
import service.Notification;

import java.util.List;

public interface AdministratorService {

    Notification create(User user, List<String> roleNames);

    Notification update(Long userID, User newUser);

    Notification delete(Long userID);

    List<User> findAllUsers();
}
