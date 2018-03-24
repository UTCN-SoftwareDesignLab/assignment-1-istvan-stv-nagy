package repository;

import model.Account;

public interface AccountRepository {

    boolean create(Long clientID, Account account);

    boolean update(Long accountID, Account newAccount);

    Account findById(Long accountID) throws EntityNotFoundException;

    boolean delete(Long accountID) throws EntityNotFoundException;
}
