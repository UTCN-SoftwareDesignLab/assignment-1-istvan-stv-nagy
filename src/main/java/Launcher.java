import database.JDBConnectionWrapper;
import presentation.login.LoginController;
import presentation.login.LoginPage;
import repository.AccountRepository;
import repository.AccountRepositoryMySQL;
import repository.ClientRepository;
import repository.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.administration.AdministratorService;
import service.administration.AdministratorServiceImpl;
import service.employee.EmployeeService;
import service.employee.EmployeeServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;

public class Launcher {

    public Launcher() {
        //create connection
        Connection connection = new JDBConnectionWrapper("bank_test").getConnection();

        //create repositories
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        AccountRepository accountRepository = new AccountRepositoryMySQL(connection);

        //create services
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        EmployeeService employeeService = new EmployeeServiceImpl(clientRepository, accountRepository);
        AdministratorService administratorService = new AdministratorServiceImpl(userRepository, rightsRolesRepository);

        LoginPage loginPage = new LoginPage();

        //create controllers
        LoginController loginController = new LoginController(loginPage, authenticationService, employeeService, administratorService);
    }

}
