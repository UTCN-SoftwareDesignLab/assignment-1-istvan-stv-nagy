package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String createSQLForTable(String table) {
        switch (table) {
            case CLIENT:
                return "CREATE TABLE IF NOT EXISTS client (" +
                        " client_id int(11) NOT NULL AUTO_INCREMENT," +
                        " name varchar(200) NOT NULL," +
                        " idNumber varchar(13) NOT NULL, " +
                        " address varchar(500) NOT NULL," +
                        "  PRIMARY KEY (client_id)," +
                        "  UNIQUE KEY id_UNIQUE (client_id)" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

            case ACCOUNT:
                return "CREATE TABLE IF NOT EXISTS account (" +
                        " account_id int(11) NOT NULL AUTO_INCREMENT," +
                        " client_id int(11) NOT NULL, " +
                        " type varchar(100) NOT NULL, " +
                        " balance DOUBLE NOT NULL, " +
                        " creationDate DATETIME NOT NULL, " +
                        " PRIMARY KEY (account_id)," +
                        " INDEX client_id_idx (client_id ASC)," +
                        " CONSTRAINT client_id " +
                        "   FOREIGN KEY (client_id)" +
                        "   REFERENCES client(client_id)" +
                        "   ON DELETE CASCADE" +
                        "   ON UPDATE CASCADE);";

            default:
                return "";
        }
    }

}
