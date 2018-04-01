/*
 * Created by JFormDesigner on Wed Mar 28 19:13:54 EEST 2018
 */

package presentation.administration;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import net.miginfocom.swing.*;
import presentation.CommandType;

/**
 * @author Istvan Nagy
 */
public class AdministratorPage extends JFrame {

    public AdministratorPage() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
    }

    public String getDateFrom() {
        return dateFromTextField.getText();
    }

    public String getDateTo() {
        return dateToTextField.getText();
    }

    public String getUserId() {
        return userIdTextField.getText();
    }

    public void setupButtons(ActionListener administratorListener, ActionListener loginPageListener) {
        createUser.setActionCommand(CommandType.CREATE_USER.toString());
        findAllButton.setActionCommand(CommandType.FINDALL_USERS.toString());
        generateButton.setActionCommand(CommandType.GENERATE_REPORT.toString());
        deleteButton.setActionCommand(CommandType.DELETE_USER.toString());
        findButton.setActionCommand(CommandType.FIND_USER.toString());
        updateButton.setActionCommand(CommandType.UPDATE_USER.toString());

        logoutButton.setActionCommand(CommandType.LOGOUT.toString());

        createUser.addActionListener(administratorListener);
        findAllButton.addActionListener(administratorListener);
        generateButton.addActionListener(administratorListener);
        deleteButton.addActionListener(administratorListener);
        findButton.addActionListener(administratorListener);
        updateButton.addActionListener(administratorListener);
        logoutButton.addActionListener(administratorListener);

        logoutButton.addActionListener(loginPageListener);
    }

    public void setUsersTable(JTable newTable) {
        this.userTable = newTable;
        scrollPane1.setViewportView(userTable);
        repaint();
        revalidate();
    }

    public JTable getUserTable() {
        return userTable;
    }

    public void setReportTable(JTable newTable) {
        this.reportTable = newTable;
        scrollPane2.setViewportView(reportTable);
        repaint();
        revalidate();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return String.copyValueOf(passwordField.getPassword());
    }

    public Long getSelectedUserId() {
        int selectedRow = userTable.getSelectedRow();
        return Long.parseLong(userTable.getValueAt(selectedRow, 0).toString());
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
        label3 = new JLabel();
        dateFromTextField = new JTextField();
        findButton = new JButton();
        userIdTextField = new JTextField();
        label4 = new JLabel();
        dateToTextField = new JTextField();
        updateButton = new JButton();
        deleteButton = new JButton();
        generateButton = new JButton();
        findAllButton = new JButton();
        scrollPane1 = new JScrollPane();
        userTable = new JTable();
        scrollPane2 = new JScrollPane();
        reportTable = new JTable();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
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
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("username");
        contentPane.add(label1, "cell 0 0");
        contentPane.add(usernameField, "cell 1 0,width 150:150:150");

        //---- logoutButton ----
        logoutButton.setText("Logout");
        contentPane.add(logoutButton, "cell 5 0");

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

        //---- label3 ----
        label3.setText("date from");
        contentPane.add(label3, "cell 2 4");
        contentPane.add(dateFromTextField, "cell 3 4,width 100:100:100");

        //---- findButton ----
        findButton.setText("Find");
        contentPane.add(findButton, "cell 0 5");
        contentPane.add(userIdTextField, "cell 1 5,width 100:100:100");

        //---- label4 ----
        label4.setText("date to");
        contentPane.add(label4, "cell 2 5");
        contentPane.add(dateToTextField, "cell 3 5");

        //---- updateButton ----
        updateButton.setText("Update");
        contentPane.add(updateButton, "cell 0 6");

        //---- deleteButton ----
        deleteButton.setText("Delete");
        contentPane.add(deleteButton, "cell 0 7");

        //---- generateButton ----
        generateButton.setText("Generate Report");
        contentPane.add(generateButton, "cell 2 7");

        //---- findAllButton ----
        findAllButton.setText("Find All");
        contentPane.add(findAllButton, "cell 0 8");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(userTable);
        }
        contentPane.add(scrollPane1, "cell 1 9");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(reportTable);
        }
        contentPane.add(scrollPane2, "cell 2 9");
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
    private JLabel label3;
    private JTextField dateFromTextField;
    private JButton findButton;
    private JTextField userIdTextField;
    private JLabel label4;
    private JTextField dateToTextField;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton generateButton;
    private JButton findAllButton;
    private JScrollPane scrollPane1;
    private JTable userTable;
    private JScrollPane scrollPane2;
    private JTable reportTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
