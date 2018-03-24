import com.mysql.jdbc.jdbc2.optional.JDBC4ConnectionWrapper;
import database.JDBConnectionWrapper;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import repository.*;
import service.EmployeeService;
import service.EmployeeServiceImpl;
import service.Notification;

import java.sql.Date;
import java.util.Calendar;

public class Boot {

    public static void main(String args[]) {

        EmployeeService service = new EmployeeServiceImpl(
                new ClientRepositoryMySQL(new JDBConnectionWrapper("bank_test").getConnection()),
                new AccountRepositoryMySQL(new JDBConnectionWrapper("bank_test").getConnection()));

        Notification n = service.transfer(1L, 2L, 10.00);

        System.out.println(n.getMessage());

        n = service.delete(5L);

        System.out.println(n.getMessage());

    }
}
