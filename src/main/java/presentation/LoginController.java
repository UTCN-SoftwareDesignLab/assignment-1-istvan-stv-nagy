package presentation;

import model.User;
import repository.user.UserAuthenticationException;
import service.Notification;
import service.user.AuthenticationService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginPage loginPage;
    private ActionListener actionListener;

    private AuthenticationService authenticationService;

    public LoginController(LoginPage loginPage, AuthenticationService authenticationService) {
        this.loginPage = loginPage;
        this.actionListener = new LoginButtonListener();
        loginPage.setupButtons(actionListener);

        this.authenticationService = authenticationService;
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String username;
            String password;
            Notification notification;
            switch (actionEvent.getActionCommand()) {
                case "login" :
                    username = loginPage.getUsername();
                    password = String.copyValueOf(loginPage.getPassword());
                    try {
                        User activeUser = authenticationService.login(username, password);
                        loginPage.showMessage("Login successful!");
                    } catch (UserAuthenticationException e) {
                        loginPage.showMessage(e.getMessage());
                    }
                    break;
                case "register" :
                    username = loginPage.getUsername();
                    password = String.copyValueOf(loginPage.getPassword());
                    notification = authenticationService.register(username, password);
                    loginPage.showMessage(notification.getMessage());
                    break;
            }
        }
    }

}
