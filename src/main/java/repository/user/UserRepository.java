package repository.user;

import model.Account;
import model.User;
import repository.EntityNotFoundException;

import java.util.List;

public interface UserRepository {

    User findByUsernameAndPassword(String username, String password) throws UserAuthenticationException;

    boolean create(User user) throws UserAuthenticationException;

    boolean update(Long userID, User newUser);

    List<User> findAllUsers();

    User findById(Long userID) throws EntityNotFoundException;

    boolean delete(Long accountID) throws EntityNotFoundException;

}
