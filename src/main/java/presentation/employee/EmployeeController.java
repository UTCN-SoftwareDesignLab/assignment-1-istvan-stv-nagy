package presentation.employee;

import model.*;
import model.builder.AccountBuilder;
import model.builder.ActivityBuilder;
import model.builder.BillBuilder;
import model.builder.ClientBuilder;
import model.validator.ClientValidator;
import presentation.CommandType;
import presentation.factory.TableFactory;
import repository.EntityNotFoundException;
import service.Notification;
import service.employee.EmployeeService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EmployeeController {

    private EmployeePage employeePage;

    private EmployeeService employeeService;

    private ActionListener actionListener;

    private TableFactory tableFactory;

    private User activeEmployee;

    public EmployeeController(EmployeePage employeePage, EmployeeService employeeService,  ActionListener loginPageListener, TableFactory tableFactory) {
        this.employeePage = employeePage;
        this.employeeService = employeeService;
        this.actionListener = new EmployeeControllerListener();
        employeePage.setupButtons(actionListener, loginPageListener);
        this.tableFactory = tableFactory;
    }

    public void setActiveEmployee(User activeEmployee) {
        this.activeEmployee = activeEmployee;
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

    public void setVisible(boolean visible) {
        employeePage.setVisible(visible);
    }

    private class EmployeeControllerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            Notification notification = new Notification();

            String command = actionEvent.getActionCommand();

            CommandType commandType = CommandType.valueOf(command);

            switch (commandType) {
                case ADD_CLIENT :
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
                case FIND_CLIENT:
                    Long clientID = Long.parseLong(employeePage.getClientId());
                    try {
                        Client foundClient = employeeService.findClientById(clientID);
                        List<Client> result = new ArrayList<>();
                        result.add(foundClient);
                        employeePage.setClientTable(tableFactory.createClientsTable(result));
                    } catch (EntityNotFoundException e) {
                        notification.addError(e.getMessage());
                    }
                    break;
                case FINDALL_CLIENTS:
                    List<Client> clients = employeeService.findAllClients();
                    employeePage.setClientTable(tableFactory.createClientsTable(clients));
                    break;
                case UPDATE_CLIENT:
                    clientID = employeePage.getSelectedClientId();
                    Client newClient = extractClientFromTable(employeePage.getClientTable());
                    notification = employeeService.update(clientID, newClient);
                    break;

                case ADD_ACCOUNT:
                    clientID = employeePage.getSelectedClientId();
                    Account newAccount = new AccountBuilder()
                            .setBalance(Double.parseDouble(employeePage.getAccountBalance()))
                            .setType(employeePage.getAccountType())
                            .setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()))
                            .build();
                    notification = employeeService.create(clientID, newAccount);
                    break;
                case FIND_ACCOUNT:
                    clientID = employeePage.getSelectedClientId();
                    List<Account> accounts = employeeService.findAccountsForClient(clientID);
                    employeePage.setAccountTable(tableFactory.createAccountsTable(accounts));
                    break;
                case DELETE_ACCOUNT:
                    Long accountID = employeePage.getSelectedAccountId();
                    notification = employeeService.delete(accountID);
                    break;
                case UPDATE_ACCOUNT:
                    accountID = employeePage.getSelectedAccountId();
                    notification = employeeService.update(accountID, extractAccountFromTable(employeePage.getAccountTable()));
                    break;

                case TRANSFER_FROM:
                    accountID = employeePage.getSelectedAccountId();
                    employeePage.setTransferFromId(accountID);
                    break;
                case TRANSFER_TO:
                    accountID = employeePage.getSelectedAccountId();
                    employeePage.setTransferToId(accountID);
                    break;
                case TRANSFER:
                    double amount = Double.parseDouble(employeePage.getAmount());
                    Long accountFromID = Long.parseLong(employeePage.getTransferFromId());
                    Long accountToID = Long.parseLong(employeePage.getTransferToId());
                    notification = employeeService.transfer(accountFromID, accountToID, amount);
                    break;

                case PAY_BILL:
                    accountID = employeePage.getSelectedAccountId();
                    String description = employeePage.getBillDescription();
                    amount = Double.parseDouble(employeePage.getBillAmount());
                    Bill bill = new BillBuilder()
                            .setAmount(amount)
                            .setDescription(description)
                            .build();
                    notification = employeeService.processBill(accountID, bill);
                    break;

                case LOGOUT:
                    employeePage.close();
                    break;
            }

            if (notification.getMessage() != null) {
                Activity activity = new ActivityBuilder()
                        .setUserId(activeEmployee.getId())
                        .setAction(commandType.toString())
                        .setDescription(notification.getMessage())
                        .setDate(new Date((Calendar.getInstance().getTime()).getTime()))
                        .build();
                employeeService.create(activity);
            }

            employeePage.showMessage(notification.getMessage());

        }
    }

}
