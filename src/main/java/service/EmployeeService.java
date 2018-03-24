package service;

import model.Account;
import model.Client;
import repository.EntityNotFoundException;

public interface EmployeeService {

    Notification transfer(Long accountFromId, Long accountToId, double amount);

    Notification create(Client client);

    Notification create(Long clientID, Account account);

    Notification update(Long clientID, Client newClient);

    Notification update(Long accountID, Account newAccount);

    Client findClientById(Long id) throws EntityNotFoundException;

    Account findAccountById(Long id) throws EntityNotFoundException;

    Notification delete(Long accountID);

}
