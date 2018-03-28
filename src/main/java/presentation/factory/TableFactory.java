package presentation.factory;

import model.Account;
import model.Client;
import model.User;

import javax.swing.*;
import java.util.List;

public class TableFactory {

    private static final int CLIENTS_COLUMNS = 4;
    private static final String[] CLIENTS_FIELD = {"id", "client name", "ID number", "address"};

    private static final int ACCOUNTS_COLUMNS = 4;
    private static final String[] ACCOUNTS_FIELD = {"id", "balance", "type", "creation date"};

    private static final int USERS_COLUMNS = 2;
    private static final String[] USERS_FIELD = {"id", "username"};

    public JTable createClientsTable(List<Client> clients) {
        if (clients.size() == 0)
            return null;
        JTable table = null;
        String columns[] = new String[CLIENTS_COLUMNS];
        int i = 0;
        for (String fieldName : CLIENTS_FIELD) {
            columns[i] = fieldName;
            i++;
        }
        String data[][] = new String[clients.size()][CLIENTS_COLUMNS];
        int row = 0;
        for (Client client : clients) {
            data[row][0] = client.getId().toString();
            data[row][1] = client.getName();
            data[row][2] = client.getIdNumber();
            data[row][3] = client.getAddress();
            row++;
        }
        table = new JTable(data, columns);
        return table;
    }

    public JTable createAccountsTable(List<Account> accounts) {
        if (accounts.size() == 0)
            return null;
        JTable table = null;
        String columns[] = new String[ACCOUNTS_COLUMNS];
        int i = 0;
        for (String fieldName : ACCOUNTS_FIELD) {
            columns[i] = fieldName;
            i++;
        }
        String data[][] = new String[accounts.size()][ACCOUNTS_COLUMNS];
        int row = 0;
        for (Account account : accounts) {
            data[row][0] = account.getId().toString();
            data[row][1] = String.valueOf(account.getBalance());
            data[row][2] = account.getType();
            data[row][3] = account.getCreationDate().toString();
            row++;
        }
        table = new JTable(data, columns);
        return table;
    }

    public JTable createUsersTable(List<User> users) {
        if (users.size() == 0)
            return null;
        JTable table = null;
        String columns[] = new String[USERS_COLUMNS];
        int i = 0;
        for (String fieldName : USERS_FIELD) {
            columns[i] = fieldName;
            i++;
        }
        String data[][] = new String[users.size()][USERS_COLUMNS];
        int row = 0;
        for (User user : users) {
            data[row][0] = user.getId().toString();
            data[row][1] = user.getUsername();
            row++;
        }
        table = new JTable(data, columns);
        return table;
    }

}
