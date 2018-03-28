package repository.user;

import model.User;

import java.util.List;

public interface UserRepository {

    User findByUsernameAndPassword(String username, String password) throws UserAuthenticationException;

    boolean create(User user) throws UserAuthenticationException;

    List<User> findAllUsers();

}
