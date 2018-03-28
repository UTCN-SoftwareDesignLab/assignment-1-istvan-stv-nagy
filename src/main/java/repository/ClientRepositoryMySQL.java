package repository;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository {

    private Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM client");
            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                Client client = new ClientBuilder()
                        .setId(rs.getLong("client_id"))
                        .setIDNumber(rs.getString("idNumber"))
                        .setName(rs.getString("name"))
                        .setAddress(rs.getString("address"))
                        .build();
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client findById(Long id) throws EntityNotFoundException {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM client WHERE client_id = ?");
            findStatement.setLong(1, id);
            ResultSet rs = findStatement.executeQuery();
            if (rs.next()) {
                return new ClientBuilder()
                        .setId(rs.getLong("client_id"))
                        .setIDNumber(rs.getString("idNumber"))
                        .setName(rs.getString("name"))
                        .setAddress(rs.getString("address"))
                        .build();
            }
            else throw new EntityNotFoundException(id, "Client");
        } catch (SQLException e) {
            throw new EntityNotFoundException(id, "Client");
        }
    }

    @Override
    public boolean create(Client client){
        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO client values(null, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdNumber());
            insertStatement.setString(3, client.getAddress());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
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
