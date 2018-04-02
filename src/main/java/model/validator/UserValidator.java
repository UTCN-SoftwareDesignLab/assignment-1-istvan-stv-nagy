package model.validator;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final int MIN_PASS_LENGTH = 6;
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private List<String> errors;
    private final User user;

    public UserValidator(User user) {
        this.user = user;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        return validateUsername(user.getUsername()) && validatePassword(user.getPassword());
    }

    public boolean validateUsername(String username) {
        if (!Pattern.compile(EMAIL_VALIDATION_REGEX).matcher(username).matches()) {
            errors.add("Invalid email!");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.length() < MIN_PASS_LENGTH) {
            errors.add("Password must be at least of length " + MIN_PASS_LENGTH + " !");
            return false;
        }
        if (!containsDigits(password)) {
            errors.add("Password must contain at least 1 digit!");
            return false;
        }
        if (!containsSpecialCharacter(password)) {
            errors.add("Password must contain at least 1 special character!");
            return false;
        }
        return true;
    }

    private boolean containsSpecialCharacter(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        return m.find();
    }

    private boolean containsDigits(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c))
                return true;
        }
        return false;
    }

    public List<String> getErrors() {
        return errors;
    }
}
