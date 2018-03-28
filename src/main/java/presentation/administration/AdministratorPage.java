/*
 * Created by JFormDesigner on Wed Mar 28 19:13:54 EEST 2018
 */

package presentation.administration;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Istvan Nagy
 */
public class AdministratorPage extends JFrame {
    public AdministratorPage() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
    }

    public void setupButtons(ActionListener administratorListener, ActionListener loginPageListener) {
        createUser.setActionCommand("create_user");
        findAllButton.setActionCommand("findall_users");

        logoutButton.setActionCommand("logout");

        createUser.addActionListener(administratorListener);
        findAllButton.addActionListener(administratorListener);

        logoutButton.addActionListener(loginPageListener);
    }

    public void setUsersTable(JTable newTable) {
        this.userTable = newTable;
        scrollPane1.setViewportView(userTable);
        repaint();
        revalidate();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return String.copyValueOf(passwordField.getPassword());
    }

    public List<String> getSelectedCheckboxes() {
        List<String> result = new ArrayList<>();
        if (employeeCheckbox.isSelected())
            result.add(employeeCheckbox.getText());
        if (administratorCheckbox.isSelected())
            result.add(administratorCheckbox.getText());
        return result;
    }

    public void showMessage(String message) {
        if (message != null)
            JOptionPane.showMessageDialog(this, message);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Istvan Nagy
        label1 = new JLabel();
        usernameField = new JTextField();
        logoutButton = new JButton();
        label2 = new JLabel();
        passwordField = new JPasswordField();
        employeeCheckbox = new JCheckBox();
        administratorCheckbox = new JCheckBox();
        createUser = new JButton();
        findButton = new JButton();
        updateButton = new JButton();
        deleteButton = new JButton();
        findAllButton = new JButton();
        scrollPane1 = new JScrollPane();
        userTable = new JTable();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("username");
        contentPane.add(label1, "cell 0 0");
        contentPane.add(usernameField, "cell 1 0,width 150:150:150");

        //---- logoutButton ----
        logoutButton.setText("Logout");
        contentPane.add(logoutButton, "cell 2 0");

        //---- label2 ----
        label2.setText("password");
        contentPane.add(label2, "cell 0 1");
        contentPane.add(passwordField, "cell 1 1,width 150:150:150");

        //---- employeeCheckbox ----
        employeeCheckbox.setText("employee");
        contentPane.add(employeeCheckbox, "cell 0 2");

        //---- administratorCheckbox ----
        administratorCheckbox.setText("administrator");
        contentPane.add(administratorCheckbox, "cell 0 3");

        //---- createUser ----
        createUser.setText("Create");
        contentPane.add(createUser, "cell 0 4");

        //---- findButton ----
        findButton.setText("Find");
        contentPane.add(findButton, "cell 0 5");

        //---- updateButton ----
        updateButton.setText("Update");
        contentPane.add(updateButton, "cell 0 6");

        //---- deleteButton ----
        deleteButton.setText("Delete");
        contentPane.add(deleteButton, "cell 0 7");

        //---- findAllButton ----
        findAllButton.setText("Find All");
        contentPane.add(findAllButton, "cell 0 8");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(userTable);
        }
        contentPane.add(scrollPane1, "cell 1 9");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Istvan Nagy
    private JLabel label1;
    private JTextField usernameField;
    private JButton logoutButton;
    private JLabel label2;
    private JPasswordField passwordField;
    private JCheckBox employeeCheckbox;
    private JCheckBox administratorCheckbox;
    private JButton createUser;
    private JButton findButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton findAllButton;
    private JScrollPane scrollPane1;
    private JTable userTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
