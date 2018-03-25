package repository.user;

import model.User;

public interface UserRepository {

    User findByUsernameAndPassword(String username, String password) throws UserAuthenticationException;

    boolean create(User user) throws UserAuthenticationException;

}
