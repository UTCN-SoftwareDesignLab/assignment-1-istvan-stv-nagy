package repository;

import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountRepositoryMySQL implements AccountRepository {

    private Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
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
            e.printStackTrace();
            return false;
        }
    }
}
