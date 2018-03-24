package service;

import model.Client;

public interface EmployeeService {

    Notification create(Client client);

    Notification update(Long clientID, Client newClient);

    Client findById(Long id);

}
