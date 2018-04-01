import database.JDBConnectionWrapper;
import presentation.administration.AdministratorController;
import presentation.administration.AdministratorPage;
import presentation.employee.EmployeeController;
import presentation.employee.EmployeePage;
import presentation.factory.TableFactory;
import presentation.login.LoginController;
import presentation.login.LoginPage;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
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
        ActivityRepository activityRepository = new ActivityRepositoryMySQL(connection);

        //create services
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);
        EmployeeService employeeService = new EmployeeServiceImpl(clientRepository, accountRepository, activityRepository);
        AdministratorService administratorService = new AdministratorServiceImpl(userRepository, rightsRolesRepository, activityRepository);

        //create views
        LoginPage loginPage = new LoginPage();
        EmployeePage employeePage = new EmployeePage();
        AdministratorPage administratorPage = new AdministratorPage();

        //create controllers
        TableFactory tableFactory = new TableFactory();
        LoginController loginController = new LoginController(loginPage, authenticationService);
        EmployeeController employeeController = new EmployeeController(employeePage, employeeService, loginController.getActionListener(), tableFactory);
        AdministratorController administratorController = new AdministratorController(administratorPage, administratorService, loginController.getActionListener(), tableFactory);
        loginController.setAdministratorController(administratorController);
        loginController.setEmployeeController(employeeController);
    }

}
