package repository;

import model.Client;

import java.sql.SQLException;

public interface ClientRepository {

    Client findById(Long id) throws EntityNotFoundException;

    boolean create(Client client);

    boolean update(Long clientID, Client newClient);
}
