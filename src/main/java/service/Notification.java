package service;

import java.util.ArrayList;
import java.util.List;

public class Notification {

    private String message;
    private List<String> errors;

    public Notification() {
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public String getMessage() {
        if (hasErrors()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Operation failed. Errors:\n");
            for (String error : errors)
                sb.append(error + "\n");
            return sb.toString();
        }
        return message;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
