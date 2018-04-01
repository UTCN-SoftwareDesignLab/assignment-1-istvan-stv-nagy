package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {

    boolean create(Long clientID, Account account);

    boolean update(Long accountID, Account newAccount);

    Account findById(Long accountID) throws EntityNotFoundException;

    boolean delete(Long accountID) throws EntityNotFoundException;

    List<Account> findAccountsForClient(Long clientID);
}
