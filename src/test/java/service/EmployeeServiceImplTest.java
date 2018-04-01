package service;

import database.Boostrap;
import database.Constants;
import database.JDBConnectionWrapper;
import model.Account;
import model.Bill;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.BillBuilder;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class EmployeeServiceImplTest {

    private static final String VALID_CLIENT_NAME = "Tester Tester";
    private static final String VALID_CLIENT_ID = "1961130314007";
    private static final String VALID_CLIENT_ADDRESS = "Cluj 12";

    private static Long CLIENT_ID;
    private static Long ACCOUNT_ID;
    private static Long DELETED_ACCOUNT_ID;

    private static EmployeeService employeeService;

    private static void initDatabase() {
        //add one client to the database
        Client client = new ClientBuilder()
                .setIDNumber(VALID_CLIENT_ID)
                .setName("Test One")
                .setAddress("address")
                .build();
        employeeService.create(client);
        //find a valid client id
        CLIENT_ID = employeeService.findAllClients().get(0).getId();
        //add one account to the database
        Account account1 = new AccountBuilder()
                .setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()))
                .setType("saving")
                .setBalance(100.00)
                .build();
        employeeService.create(CLIENT_ID, account1);
        Account account2 = new AccountBuilder()
                .setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()))
                .setType("credit")
                .setBalance(500.00)
                .build();
        employeeService.create(CLIENT_ID, account2);
        ACCOUNT_ID = employeeService.findAccountsForClient(CLIENT_ID).get(0).getId();
        DELETED_ACCOUNT_ID = employeeService.findAccountsForClient(CLIENT_ID).get(1).getId();
    }

    @BeforeClass
    public static void setup() throws SQLException {
        Boostrap.setup();
        Connection connection = new JDBConnectionWrapper(Constants.Schemas.JUNIT_TEST).getConnection();
        ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        ActivityRepository activityRepository = new ActivityRepositoryMySQL(connection);
        employeeService = new EmployeeServiceImpl(clientRepository, accountRepository, activityRepository);
        initDatabase();
    }

    @Test
    public void createClient() {
        Client testClient = new ClientBuilder()
                .setName(VALID_CLIENT_NAME)
                .setAddress(VALID_CLIENT_ADDRESS)
                .setIDNumber(VALID_CLIENT_ID)
                .build();
        Notification notification = employeeService.create(testClient);
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());
    }


    @Test
    public void findClientById() throws EntityNotFoundException {
        Client client = employeeService.findClientById(CLIENT_ID);
        Assert.assertTrue(client != null);
    }

    @Test
    public void updateClient() {
        Client testClient = new ClientBuilder()
                .setName("Another name")
                .setAddress("Valid address")
                .setIDNumber(VALID_CLIENT_ID)
                .build();
        Notification notification = employeeService.update(CLIENT_ID, testClient);
        Assert.assertTrue(notification.getMessage(),!notification.hasErrors());
    }

    @Test
    public void findAllClients() {
        List<Client> clientList = employeeService.findAllClients();
        Assert.assertTrue(!clientList.isEmpty());
    }

    @Test
    public void createAccount() {
        Account account = new AccountBuilder()
                .setBalance(200.00)
                .setType("credit")
                .setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()))
                .build();
        Notification notification = employeeService.create(CLIENT_ID, account);
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());
    }

    @Test
    public void updateAccount() {
        Account newAccount = new AccountBuilder()
                .setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()))
                .setType("credit")
                .setBalance(200.00)
                .build();
        Notification notification = employeeService.update(ACCOUNT_ID, newAccount);
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());
    }

    @Test
    public void findAccountById() throws EntityNotFoundException {
        Account account = employeeService.findAccountById(ACCOUNT_ID);
        Assert.assertTrue(account != null);
    }

    @Test
    public void findAccountsForClient() {
        List<Account> accounts = employeeService.findAccountsForClient(CLIENT_ID);
        Assert.assertTrue(!accounts.isEmpty());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteAccount() throws EntityNotFoundException {
        Notification notification = employeeService.delete(DELETED_ACCOUNT_ID);
        employeeService.findAccountById(DELETED_ACCOUNT_ID);
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());
    }

    @Test
    public void transfer() {
        double accountFromBalance = 100.00;
        double accountToBalance = 20.00;
        double amount = 50.00;

        Client newClient = new ClientBuilder()
                .setName("tester")
                .setIDNumber(VALID_CLIENT_ID)
                .setAddress(VALID_CLIENT_ADDRESS)
                .build();
        employeeService.create(newClient);
        List<Client> clientList = employeeService.findAllClients();
        Client client = clientList.get(clientList.size() - 1);
        Long clientID = client.getId();

        Account accountFrom = new AccountBuilder()
                .setBalance(accountFromBalance)
                .setType("saving")
                .setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()))
                .build();
        Account accountTo = new AccountBuilder()
                .setBalance(accountToBalance)
                .setType("saving")
                .setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()))
                .build();
        employeeService.create(clientID, accountFrom);
        employeeService.create(clientID, accountTo);

        List<Account> accounts = employeeService.findAccountsForClient(clientID);
        accountFrom = accounts.get(0);
        accountTo = accounts.get(1);

        Notification notification = employeeService.transfer(accountFrom.getId(), accountTo.getId(), amount);
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());

        accounts = employeeService.findAccountsForClient(clientID);
        accountFrom = accounts.get(0);
        accountTo = accounts.get(1);
        Assert.assertTrue(accountFrom.getBalance() == accountFromBalance - amount);
        Assert.assertTrue(accountTo.getBalance() == accountToBalance + amount);
    }

    @Test
    public void processBill() {
        Bill bill = new BillBuilder()
                .setDescription("test bill")
                .setAmount(10.00)
                .build();
        Notification notification = employeeService.processBill(ACCOUNT_ID, bill);
        Assert.assertTrue(notification.getMessage(), !notification.hasErrors());
    }
}
