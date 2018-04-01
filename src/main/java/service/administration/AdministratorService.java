package service.administration;

import model.Activity;
import model.Client;
import model.User;
import repository.EntityNotFoundException;
import service.Notification;

import java.sql.Date;
import java.util.List;

public interface AdministratorService {

    Notification create(User user, List<String> roleNames);

    Notification update(Long userID, User newUser);

    Notification delete(Long userID);

    User findUserById(Long userID) throws EntityNotFoundException;

    List<User> findAllUsers();

    List<Activity> generateReportForUser(Long userID, Date dateFrom, Date dateTo);
}
