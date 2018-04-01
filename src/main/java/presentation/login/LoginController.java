package presentation.login;

import model.User;
import presentation.CommandType;
import presentation.administration.AdministratorController;
import presentation.employee.EmployeeController;
import repository.user.UserAuthenticationException;
import service.Notification;
import service.user.AuthenticationService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    //view
    private LoginPage loginPage;
    //action listener
    private ActionListener actionListener;
    //services
    private AuthenticationService authenticationService;
    //sub-controllers
    private EmployeeController employeeController;
    private AdministratorController administratorController;

    public LoginController(LoginPage loginPage, AuthenticationService authenticationService) {
        this.loginPage = loginPage;
        this.actionListener = new LoginButtonListener();
        loginPage.setupButtons(actionListener);

        this.authenticationService = authenticationService;
    }

    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public void setAdministratorController(AdministratorController administratorController) {
        this.administratorController = administratorController;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String username;
            String password;
            Notification notification;

            String command = actionEvent.getActionCommand();

            CommandType commandType = CommandType.valueOf(command);

            switch (commandType) {
                case LOGIN:
                    username = loginPage.getUsername();
                    password = String.copyValueOf(loginPage.getPassword());
                    try {
                        User activeUser = authenticationService.login(username, password);
                        loginPage.showMessage("Login successful!");
                        loginPage.setVisible(false);
                        if (activeUser.hasRoleOf("employee")) {
                            employeeController.setVisible(true);
                            employeeController.setActiveEmployee(activeUser);
                        }
                        if (activeUser.hasRoleOf("administrator"))
                            administratorController.setVisible(true);
                    } catch (UserAuthenticationException e) {
                        loginPage.showMessage(e.getMessage());
                        loginPage.resetPage();
                    }
                    break;
                case REGISTER:
                    username = loginPage.getUsername();
                    password = String.copyValueOf(loginPage.getPassword());
                    notification = authenticationService.register(username, password);
                    loginPage.showMessage(notification.getMessage());
                    break;
                case LOGOUT:
                    loginPage.resetPage();
                    loginPage.setVisible(true);
                    break;
            }
        }
    }

}
