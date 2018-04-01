package repository.client;

import model.Client;
import repository.EntityNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client findById(Long id) throws EntityNotFoundException;

    boolean create(Client client);

    boolean update(Long clientID, Client newClient) throws EntityNotFoundException;
}
