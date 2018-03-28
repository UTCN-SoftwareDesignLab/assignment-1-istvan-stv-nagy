package service.employee;

import model.Account;
import model.Bill;
import model.Client;
import repository.EntityNotFoundException;
import service.Notification;

import java.util.List;

public interface EmployeeService {

    Notification transfer(Long accountFromId, Long accountToId, double amount);

    Notification processBill(Long accountID, Bill bill);

    Notification create(Client client);

    Notification create(Long clientID, Account account);

    Notification update(Long clientID, Client newClient);

    Notification update(Long accountID, Account newAccount);

    List<Client> findAllClients();

    Client findClientById(Long id) throws EntityNotFoundException;

    Account findAccountById(Long id) throws EntityNotFoundException;

    List<Account> findAccountsForClient(Long clientID);

    Notification delete(Long accountID);

}
