package presentation.login;

import model.User;
import presentation.administration.AdministratorController;
import presentation.administration.AdministratorPage;
import presentation.employee.EmployeeController;
import presentation.employee.EmployeePage;
import repository.user.UserAuthenticationException;
import service.Notification;
import service.administration.AdministratorService;
import service.employee.EmployeeService;
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
    private EmployeeService employeeService;
    private AdministratorService administratorService;
    //sub-controllers
    //private EmployeeController employeeController;

    public LoginController(LoginPage loginPage, AuthenticationService authenticationService, EmployeeService employeeService, AdministratorService administratorService) {
        this.loginPage = loginPage;
        this.actionListener = new LoginButtonListener();
        loginPage.setupButtons(actionListener);

        this.authenticationService = authenticationService;
        this.employeeService = employeeService;
        this.administratorService = administratorService;
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
                        loginPage.setVisible(false);
                        if (activeUser.hasRoleOf("employee"))
                            new EmployeeController(new EmployeePage(), employeeService, actionListener, activeUser.getUsername());
                        if (activeUser.hasRoleOf("administrator"))
                            new AdministratorController(new AdministratorPage(), administratorService, actionListener);
                    } catch (UserAuthenticationException e) {
                        loginPage.showMessage(e.getMessage());
                        loginPage.resetPage();
                    }
                    break;
                case "register" :
                    username = loginPage.getUsername();
                    password = String.copyValueOf(loginPage.getPassword());
                    notification = authenticationService.register(username, password);
                    loginPage.showMessage(notification.getMessage());
                    break;
                case "logout" :
                    loginPage.resetPage();
                    loginPage.setVisible(true);
                    break;
            }
        }
    }

}
