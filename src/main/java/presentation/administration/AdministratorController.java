package presentation.administration;

import model.User;
import model.builder.UserBuilder;
import presentation.factory.TableFactory;
import service.Notification;
import service.administration.AdministratorService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorController {

    private AdministratorPage administratorPage;

    private AdministratorService administratorService;

    private ActionListener actionListener;

    public AdministratorController(AdministratorPage administratorPage, AdministratorService administratorService, ActionListener loginPageListener) {
        this.administratorPage = administratorPage;
        this.administratorService = administratorService;
        this.actionListener = new AdministratorControllerListener();
        administratorPage.setupButtons(actionListener, loginPageListener);
    }

    private class AdministratorControllerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            Notification notification = new Notification();

            switch (actionEvent.getActionCommand()) {
                case "create_user" :
                    User user = new UserBuilder()
                            .setUsername(administratorPage.getUsername())
                            .setPassword(administratorPage.getPassword())
                            .build();
                    notification = administratorService.create(user, administratorPage.getSelectedCheckboxes());
                    break;
                case "findall_users" :
                    administratorPage.setUsersTable(new TableFactory().createUsersTable(administratorService.findAllUsers()));
                    break;
            }

            administratorPage.showMessage(notification.getMessage());
        }
    }

}
