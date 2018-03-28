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
import service.employee.EmployeeServiceImpl;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;

public class Boot {

    public static void main(String args[]) {
        new Launcher();
    }
}
