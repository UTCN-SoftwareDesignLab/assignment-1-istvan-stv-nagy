package presentation.administration;

import model.Account;
import model.Activity;
import model.User;
import model.builder.AccountBuilder;
import model.builder.UserBuilder;
import presentation.CommandType;
import presentation.factory.TableFactory;
import repository.EntityNotFoundException;
import service.Notification;
import service.administration.AdministratorService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdministratorController {

    private AdministratorPage administratorPage;

    private AdministratorService administratorService;

    private ActionListener actionListener;

    private TableFactory tableFactory;

    public AdministratorController(AdministratorPage administratorPage, AdministratorService administratorService, ActionListener loginPageListener, TableFactory tableFactory) {
        this.administratorPage = administratorPage;
        this.administratorService = administratorService;
        this.actionListener = new AdministratorControllerListener();
        administratorPage.setupButtons(actionListener, loginPageListener);
        this.tableFactory = tableFactory;
    }

    public void setVisible(boolean visible) {
        administratorPage.setVisible(visible);
    }

    private User extractUserFromTable(JTable table) {
        int selectedRow = table.getSelectedRow();
        Long id = Long.parseLong(table.getValueAt(selectedRow, 0).toString());
        String username = table.getValueAt(selectedRow, 1).toString();
        return new UserBuilder()
                .setId(id)
                .setUsername(username)
                .build();
    }

    private class AdministratorControllerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            Notification notification = new Notification();

            String command = actionEvent.getActionCommand();

            CommandType commandType = CommandType.valueOf(command);

            switch (commandType) {
                case CREATE_USER:
                    User user = new UserBuilder()
                            .setUsername(administratorPage.getUsername())
                            .setPassword(administratorPage.getPassword())
                            .build();
                    notification = administratorService.create(user, administratorPage.getSelectedCheckboxes());
                    break;
                case FINDALL_USERS:
                    administratorPage.setUsersTable(tableFactory.createUsersTable(administratorService.findAllUsers()));
                    break;

                case FIND_USER:
                    Long userID = Long.parseLong(administratorPage.getUserId());
                    try {
                        List<User> userList = new ArrayList<>();
                        userList.add(administratorService.findUserById(userID));
                        administratorPage.setUsersTable(tableFactory.createUsersTable(userList));
                    } catch (EntityNotFoundException e) {
                        notification.addError(e.getMessage());
                    }
                    break;

                case DELETE_USER:
                    userID = administratorPage.getSelectedUserId();
                    notification = administratorService.delete(userID);
                    break;

                case UPDATE_USER:
                    userID = administratorPage.getSelectedUserId();
                    notification = administratorService.update(userID, extractUserFromTable(administratorPage.getUserTable()));
                    break;

                case GENERATE_REPORT:
                    userID = administratorPage.getSelectedUserId();
                    Date dateFrom = Date.valueOf(administratorPage.getDateFrom());
                    Date dateTo = Date.valueOf(administratorPage.getDateTo());
                    List<Activity> activityList = administratorService.generateReportForUser(userID, dateFrom, dateTo);
                    administratorPage.setReportTable(tableFactory.createActivitiesTable(activityList));
                    break;

                case LOGOUT:
                    administratorPage.close();
                    break;
            }

            administratorPage.showMessage(notification.getMessage());
        }
    }

}
