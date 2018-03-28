package presentation.employee;

import model.Account;
import model.Bill;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.BillBuilder;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import presentation.factory.TableFactory;
import repository.EntityNotFoundException;
import service.Notification;
import service.employee.EmployeeService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmployeeController {

    private EmployeePage employeePage;

    private EmployeeService employeeService;

    private ActionListener actionListener;

    public EmployeeController(EmployeePage employeePage, EmployeeService employeeService,  ActionListener loginPageListener, String username) {
        this.employeePage = employeePage;
        this.employeeService = employeeService;
        this.actionListener = new EmployeeControllerListener();
        employeePage.setupButtons(actionListener, loginPageListener);
        employeePage.setUsername(username);
    }

    private Client extractClientFromTable(JTable table) {
        int selectedRow = table.getSelectedRow();
        Long id = Long.parseLong(table.getValueAt(selectedRow, 0).toString());
        String name = table.getValueAt(selectedRow, 1).toString();
        String idNumber = table.getValueAt(selectedRow, 2).toString();
        String address = table.getValueAt(selectedRow, 3).toString();
        return new ClientBuilder()
                .setId(id)
                .setName(name)
                .setIDNumber(idNumber)
                .setAddress(address)
                .build();
    }

    private Account extractAccountFromTable(JTable table) {
        int selectedRow = table.getSelectedRow();
        Long id = Long.parseLong(table.getValueAt(selectedRow, 0).toString());
        double balance = Double.parseDouble(table.getValueAt(selectedRow, 1).toString());
        String type = table.getValueAt(selectedRow, 2).toString();
        Date date = Date.valueOf(table.getValueAt(selectedRow, 3).toString());
        return new AccountBuilder()
                .setId(id)
                .setBalance(balance)
                .setType(type)
                .setCreationDate(date)
                .build();
    }

    private class EmployeeControllerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            Notification notification = new Notification();

            switch (actionEvent.getActionCommand()) {
                case "add_client" :
                    Client client = new ClientBuilder()
                            .setName(employeePage.getName())
                            .setIDNumber(employeePage.getIdNumber())
                            .setAddress(employeePage.getAddress())
                            .build();
                    ClientValidator clientValidator = new ClientValidator(client);
                    if (clientValidator.validate()) {
                        notification = employeeService.create(client);
                    } else {
                        notification.setErrors(clientValidator.getErrors());
                    }
                    break;
                case "find_client" :
                    Long clientID = Long.parseLong(employeePage.getClientId());
                    try {
                        Client foundClient = employeeService.findClientById(clientID);
                        List<Client> result = new ArrayList<>();
                        result.add(foundClient);
                        employeePage.setClientTable(new TableFactory().createClientsTable(result));
                    } catch (EntityNotFoundException e) {
                        notification.addError(e.getMessage());
                    }
                    break;
                case "findall_client" :
                    List<Client> clients = employeeService.findAllClients();
                    employeePage.setClientTable(new TableFactory().createClientsTable(clients));
                    break;
                case "update_client" :
                    clientID = employeePage.getSelectedClientId();
                    Client newClient = extractClientFromTable(employeePage.getClientTable());
                    employeeService.update(clientID, newClient);
                    break;

                case "add_account" :
                    clientID = employeePage.getSelectedClientId();
                    Account newAccount = new AccountBuilder()
                            .setBalance(Double.parseDouble(employeePage.getAccountBalance()))
                            .setType(employeePage.getAccountType())
                            .setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()))
                            .build();
                    notification = employeeService.create(clientID, newAccount);
                    break;
                case "find_account" :
                    clientID = employeePage.getSelectedClientId();
                    List<Account> accounts = employeeService.findAccountsForClient(clientID);
                    employeePage.setAccountTable(new TableFactory().createAccountsTable(accounts));
                    break;
                case "delete_account" :
                    Long accountID = employeePage.getSelectedAccountId();
                    notification = employeeService.delete(accountID);
                    break;
                case "update_account" :
                    accountID = employeePage.getSelectedAccountId();
                    notification = employeeService.update(accountID, extractAccountFromTable(employeePage.getAccountTable()));
                    break;

                case "trans-from" :
                    accountID = employeePage.getSelectedAccountId();
                    employeePage.setTransferFromId(accountID);
                    break;
                case "trans-to" :
                    accountID = employeePage.getSelectedAccountId();
                    employeePage.setTransferToId(accountID);
                    break;
                case "transfer" :
                    double amount = Double.parseDouble(employeePage.getAmount());
                    Long accountFromID = Long.parseLong(employeePage.getTransferFromId());
                    Long accountToID = Long.parseLong(employeePage.getTransferToId());
                    notification = employeeService.transfer(accountFromID, accountToID, amount);
                    break;

                case "pay_bill" :
                    accountID = employeePage.getSelectedAccountId();
                    String description = employeePage.getBillDescription();
                    amount = Double.parseDouble(employeePage.getBillAmount());
                    Bill bill = new BillBuilder()
                            .setAmount(amount)
                            .setDescription(description)
                            .build();
                    notification = employeeService.processBill(accountID, bill);
                    break;

                case "logout" :
                    employeePage.close();
                    break;
            }

            employeePage.showMessage(notification.getMessage());

        }
    }

}
