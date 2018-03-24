package service;

import model.Account;
import model.Client;
import model.validator.ClientValidator;
import repository.AccountRepository;
import repository.ClientRepository;
import repository.EntityNotFoundException;

public class EmployeeServiceImpl implements EmployeeService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    public EmployeeServiceImpl(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification transfer(Long accountFromId, Long accountToId, double amount) {
        Notification notification = new Notification();

        try {
            Account accountFrom = findAccountById(accountFromId);
            Account accountTo = findAccountById(accountToId);
            double accountBalance = accountFrom.getBalance();
            if (accountBalance >= amount) {
                accountFrom.setBalance(accountBalance - amount);
                accountTo.setBalance(accountTo.getBalance() + amount);
                if (accountRepository.update(accountFromId, accountFrom) && accountRepository.update(accountToId, accountTo)) {
                    notification.setMessage("Transfer was successful!");
                } else {
                    notification.addError("MYSQL error!");
                }
            } else {
                notification.addError("Not enough money!");
            }
        } catch (EntityNotFoundException e) {
            notification.addError("Account not found!");
        }


        return notification;
    }

    @Override
    public Notification create(Client client) {
        Notification notification = new Notification();
        ClientValidator clientValidator = new ClientValidator(client);
        if (!clientValidator.validate()) {
            notification.setErrors(clientValidator.getErrors());
        } else {
            if (clientRepository.create(client)) {
                notification.setMessage("Client " + client.toString() + " created successfully!");
            } else {
                notification.addError("MYSQL error!");
            }
        }
        return notification;
    }

    @Override
    public Notification create(Long clientID, Account account) {
        Notification notification = new Notification();
        if (accountRepository.create(clientID, account)) {
            notification.setMessage("Account " + account.toString() + " added to client with id " + clientID + "!");
        } else {
            notification.addError("MYSQL error!");
        }
        return notification;
    }

    @Override
    public Notification update(Long clientID, Client newClient) {
        Notification notification = new Notification();
        ClientValidator clientValidator = new ClientValidator(newClient);
        if (!clientValidator.validate()) {
            notification.setErrors(clientValidator.getErrors());
        } else {
            if (clientRepository.update(clientID, newClient)) {
                notification.setMessage("Client " + newClient.toString() + " updated successfully!");
            } else {
                notification.addError("MYSQL error!");
            }
        }
        return notification;
    }

    @Override
    public Notification update(Long accountID, Account newAccount) {
        Notification notification = new Notification();
        if (accountRepository.update(accountID, newAccount)) {
            notification.setMessage("Account " + accountID + " updated successfully!");
        } else {
            notification.addError("MYSQL error!");
        }
        return notification;
    }

    @Override
    public Notification delete(Long accountID) {
        Notification notification = new Notification();
        try {
            if (accountRepository.delete(accountID)) {
                notification.setMessage("Account " + accountID + " deleted successfully!");
            } else {
                notification.addError("MYSQL error!");
            }
        } catch (EntityNotFoundException e) {
            notification.addError("Account with id " + accountID + " not found!");
        }
        return notification;
    }


    @Override
    public Client findClientById(Long id) throws EntityNotFoundException {
        return clientRepository.findById(id);
    }

    @Override
    public Account findAccountById(Long id) throws EntityNotFoundException {
        return accountRepository.findById(id);
    }
}
