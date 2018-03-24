package model.validator;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_NAME_LENGTH = 5;

    private static final int ID_LENGTH = 13;
    private static final String ID_PATTERN = "[1-2][0-9]{12}";

    private List<String> errors;
    private final Client client;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateName(client.getName());
        validateID(client.getIdNumber());
        return errors.isEmpty();
    }

    private void validateName(String name) {
        if (name.length() < MIN_NAME_LENGTH)
            errors.add("Client name too short!");
        if (name.length() > MAX_NAME_LENGTH)
            errors.add("Client name too long!");
    }

    private void validateID(String id) {
        if (id.length() != ID_LENGTH) {
            errors.add("Identification number must have exactly " + ID_LENGTH + "digits!");
        }
        if (!Pattern.compile(ID_PATTERN).matcher(id).matches()) {
            errors.add("Invalid identification number format!");
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
