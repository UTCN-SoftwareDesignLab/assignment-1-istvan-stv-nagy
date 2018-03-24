package repository;

import model.Account;

public interface AccountRepository {

    boolean create(Long clientID, Account account);

}
