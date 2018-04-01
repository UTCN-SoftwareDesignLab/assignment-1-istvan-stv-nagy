package service.employee;

import model.*;
import model.validator.ClientValidator;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import repository.EntityNotFoundException;
import repository.activity.ActivityRepository;
import service.Notification;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;

    public EmployeeServiceImpl(ClientRepository clientRepository, AccountRepository accountRepository, ActivityRepository activityRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.activityRepository = activityRepository;
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
    public Notification processBill(Long accountID, Bill bill) {
        Notification notification = new Notification();

        try {
            Account payingAccount = findAccountById(accountID);
            double accountBalance = payingAccount.getBalance();
            if (accountBalance >= bill.getAmount()) {
                payingAccount.setBalance(accountBalance - bill.getAmount());
                if (accountRepository.update(accountID, payingAccount)) {
                    notification.setMessage("Payment was successful!");
                } else {
                    notification.addError("MYSQL error!");
                }
            } else {
                notification.addError("Not enough money to pay the bill!");
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
    public void create(Activity activity) {
        activityRepository.create(activity);
    }

    @Override
    public Notification update(Long clientID, Client newClient) {
        Notification notification = new Notification();
        ClientValidator clientValidator = new ClientValidator(newClient);
        if (!clientValidator.validate()) {
            notification.setErrors(clientValidator.getErrors());
        } else {
            try {
                if (clientRepository.update(clientID, newClient)) {
                    notification.setMessage("Client " + newClient.toString() + " updated successfully!");
                } else {
                    notification.addError("MYSQL error!");
                }
            } catch (EntityNotFoundException e) {
                notification.addError(e.getMessage());
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
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(Long id) throws EntityNotFoundException {
        Client client = clientRepository.findById(id);
        client.setAccounts(accountRepository.findAccountsForClient(client.getId()));
        return client;
    }

    @Override
    public Account findAccountById(Long id) throws EntityNotFoundException {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findAccountsForClient(Long clientID) {
        return accountRepository.findAccountsForClient(clientID);
    }
}
