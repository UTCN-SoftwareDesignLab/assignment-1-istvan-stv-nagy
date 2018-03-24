package service;

import model.Client;
import model.validator.ClientValidator;
import repository.ClientRepository;

import java.sql.SQLException;

public class EmployeeServiceImpl implements EmployeeService {

    private final ClientRepository clientRepository;

    public EmployeeServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification create(Client client) {
        Notification notification = new Notification();
        ClientValidator clientValidator = new ClientValidator(client);
        if (!clientValidator.validate()) {
            notification.setErrors(clientValidator.getErrors());
        } else {
            try {
                clientRepository.create(client);
                notification.setMessage("Client" + client.toString() + "created successfully!");
            } catch (SQLException e) {
                notification.addError("MYSQL error!");
            }
        }
        return notification;
    }

    @Override
    public Notification update(Long clientID, Client newClient) {
        return null;
    }

    @Override
    public Client findById(Long id) {
        return null;
    }
}
