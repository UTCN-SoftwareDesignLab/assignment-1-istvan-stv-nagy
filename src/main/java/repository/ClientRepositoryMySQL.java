package repository;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepositoryMySQL implements ClientRepository {

    private Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client findById(Long id) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM client WHERE id = ?");
            findStatement.setLong(1, id);
            ResultSet rs = findStatement.executeQuery();
            if (rs.next()) {
                return new ClientBuilder()
                        .setId(rs.getLong("id"))
                        .setIDNumber(rs.getString("idNumber"))
                        .setName(rs.getString("name"))
                        .setAddress(rs.getString("address"))
                        .build();
            }
            else throw new EntityNotFoundException(id, "Client");
        } catch (SQLException | EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean create(Client client) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO client values(null, ?, ?, ?)");
        insertStatement.setString(1, client.getName());
        insertStatement.setString(2, client.getIdNumber());
        insertStatement.setString(3, client.getAddress());
        insertStatement.executeUpdate();
        return true;
    }

    @Override
    public boolean update(Long clientID, Client newClient) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE client SET name=?, idNumber=?, address=? WHERE client_id = " + clientID);
            updateStatement.setString(1, newClient.getName());
            updateStatement.setString(2, newClient.getIdNumber());
            updateStatement.setString(3, newClient.getAddress());
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
