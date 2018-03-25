package service.user;

import model.User;
import repository.user.UserAuthenticationException;
import service.Notification;

public interface AuthenticationService {

    User login(String username, String password) throws UserAuthenticationException;

    Notification register(String username, String password);

}
