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

            case ACTIVITY:
                return "CREATE TABLE IF NOT EXISTS activity (" +
                        " id int(11) NOT NULL AUTO_INCREMENT," +
                        " user_id int(11) NOT NULL, " +
                        " action varchar(100) NOT NULL, " +
                        " description varchar(300) NOT NULL, " +
                        " activityDate DATETIME NOT NULL, " +
                        " PRIMARY KEY (id)," +
                        " INDEX user_id_idx (user_id ASC)," +
                        " CONSTRAINT user_id " +
                        "   FOREIGN KEY (user_id) " +
                        "   REFERENCES users(id) " +
                        "   ON DELETE CASCADE " +
                        "   ON UPDATE CASCADE);";

            case USER:
                return "CREATE TABLE IF NOT EXISTS users (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  username VARCHAR(200) NOT NULL," +
                        "  password VARCHAR(64) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX username_UNIQUE (username ASC));";

            case ROLE:
                return "  CREATE TABLE IF NOT EXISTS role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX role_UNIQUE (role ASC));";

            case RIGHT:
                return "  CREATE TABLE IF NOT EXISTS `right` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `right` VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                        "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";

            case ROLE_RIGHT:
                return "  CREATE TABLE IF NOT EXISTS role_right (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role_id INT NOT NULL," +
                        "  right_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  INDEX right_id_idx (right_id ASC)," +
                        "  CONSTRAINT role_id" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT right_id" +
                        "    FOREIGN KEY (right_id)" +
                        "    REFERENCES `right` (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case USER_ROLE:
                return "\tCREATE TABLE IF NOT EXISTS user_role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  user_id INT NOT NULL," +
                        "  role_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX user_id_idx (user_id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  CONSTRAINT user_fkid" +
                        "    FOREIGN KEY (user_id)" +
                        "    REFERENCES users (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT role_fkid" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            default:
                return "";
        }
    }

}
