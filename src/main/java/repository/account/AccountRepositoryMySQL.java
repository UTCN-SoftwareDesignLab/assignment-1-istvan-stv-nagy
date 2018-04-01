package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository {

    private Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM account WHERE account_id = ?");
            findStatement.setLong(1, id);
            ResultSet rs = findStatement.executeQuery();
            if (rs.next()) {
                return new AccountBuilder()
                        .setId(rs.getLong("account_id"))
                        .setType(rs.getString("type"))
                        .setBalance(rs.getDouble("balance"))
                        .setCreationDate(rs.getDate("creationDate"))
                        .build();
            }
            else throw new EntityNotFoundException(id, "Account");
        } catch (SQLException e) {
            throw new EntityNotFoundException(id, "Account");
        }
    }

    @Override
    public List<Account> findAccountsForClient(Long clientID) {
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM account WHERE client_id = ?");
            findStatement.setLong(1, clientID);
            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                accounts.add(new AccountBuilder()
                                .setId(rs.getLong("account_id"))
                                .setType(rs.getString("type"))
                                .setBalance(rs.getDouble("balance"))
                                .setCreationDate(rs.getDate("creationDate"))
                                .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean create(Long clientID, Account account) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO account values(null, ?, ?, ?, ?)");
            insertStatement.setLong(1, clientID);
            insertStatement.setString(2, account.getType());
            insertStatement.setDouble(3, account.getBalance());
            insertStatement.setDate(4, account.getCreationDate());

            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean update(Long accountID, Account newAccount) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE account SET `type`=?, balance=?, creationDate=? WHERE account_id = " + accountID);
            updateStatement.setString(1, newAccount.getType());
            updateStatement.setDouble(2, newAccount.getBalance());
            updateStatement.setDate(3, newAccount.getCreationDate());
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long accountID) throws EntityNotFoundException {
        try {
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM account WHERE account_id = ?");
            deleteStatement.setLong(1, accountID);
            if (deleteStatement.executeUpdate() != 0)
                return true;
            else
                throw new EntityNotFoundException(accountID, "Account");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
