import com.mysql.jdbc.jdbc2.optional.JDBC4ConnectionWrapper;
import database.JDBConnectionWrapper;
import presentation.LoginController;
import presentation.LoginPage;
import repository.*;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.Notification;
import java.sql.*;
import service.user.AuthenticationServiceImpl;

public class Boot {

    public static void main(String args[]) {

        Connection connection = new JDBConnectionWrapper("bank_test").getConnection();

        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        new LoginController(new LoginPage(), new AuthenticationServiceImpl(userRepository, rightsRolesRepository));

        EmployeeService service = new EmployeeServiceImpl(
                new ClientRepositoryMySQL(new JDBConnectionWrapper("bank_test").getConnection()),
                new AccountRepositoryMySQL(new JDBConnectionWrapper("bank_test").getConnection()));

        Notification n = service.transfer(1L, 2L, 10.00);

        System.out.println(n.getMessage());

        n = service.delete(5L);

        System.out.println(n.getMessage());

    }
}
