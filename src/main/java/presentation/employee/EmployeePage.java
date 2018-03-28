/*
 * Created by JFormDesigner on Tue Mar 27 09:26:26 EEST 2018
 */

package presentation.employee;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Istvan Nagy
 */
public class EmployeePage extends JFrame {
    public EmployeePage() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
    }

    public void setupButtons(ActionListener employeeListener, ActionListener loginListener) {
        addClientButton.setActionCommand("add_client");
        findClientButton.setActionCommand("find_client");
        updateClientButton.setActionCommand("update_client");
        findAllClientsButton.setActionCommand("findall_client");

        addAccountButton.setActionCommand("add_account");
        updateAccountButton.setActionCommand("update_account");
        viewAccountButton.setActionCommand("find_account");
        deleteAccountButton.setActionCommand("delete_account");

        transferButton.setActionCommand("transfer");
        transferFromButton.setActionCommand("trans-from");
        transferToButton.setActionCommand("trans-to");

        payBillButton.setActionCommand("pay_bill");

        logoutButton.setActionCommand("logout");

        addClientButton.addActionListener(employeeListener);
        findClientButton.addActionListener(employeeListener);
        updateClientButton.addActionListener(employeeListener);
        findAllClientsButton.addActionListener(employeeListener);
        logoutButton.addActionListener(employeeListener);
        addAccountButton.addActionListener(employeeListener);
        updateAccountButton.addActionListener(employeeListener);
        viewAccountButton.addActionListener(employeeListener);
        deleteAccountButton.addActionListener(employeeListener);
        transferToButton.addActionListener(employeeListener);
        transferFromButton.addActionListener(employeeListener);
        transferButton.addActionListener(employeeListener);
        payBillButton.addActionListener(employeeListener);

        logoutButton.addActionListener(loginListener);
    }

    public void setClientTable(JTable newTable) {
        this.clientTable = newTable;
        scrollPane1.setViewportView(clientTable);
        repaint();
        revalidate();
    }

    public void setAccountTable(JTable newTable) {
        this.accountTable = newTable;
        scrollPane2.setViewportView(accountTable);
        repaint();
        revalidate();
    }

    public JTable getClientTable() {
        return clientTable;
    }

    public JTable getAccountTable() {
        return accountTable;
    }

    public Long getSelectedClientId() {
        int selectedRow = clientTable.getSelectedRow();
        return Long.parseLong(clientTable.getValueAt(selectedRow, 0).toString());
    }

    public Long getSelectedAccountId() {
        int selectedRow = accountTable.getSelectedRow();
        return Long.parseLong(accountTable.getValueAt(selectedRow, 0).toString());
    }

    public void setUsername(String username) {
        usernameText.setText(username);
    }

    public String getName() {
        return name.getText();
    }

    public String getIdNumber() {
        return idNumber.getText();
    }

    public String getClientId() {
        return clientID.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getAccountBalance() {
        return accountBalance.getText();
    }

    public String getAccountType() {
        return accountType.getText();
    }

    public String getAmount() {
        return amountText.getText();
    }

    public void setTransferFromText(String text) {
        transferFromText.setText(text);
    }

    public void setTransferToText(String text) {
        transferToText.setText(text);
    }

    public String getBillDescription() {
        return billDescriptionText.getText();
    }

    public String getBillAmount() {
        return billAmountText.getText();
    }

    public void setTransferToId(Long id) {
        transferToId.setText(id + "");
    }

    public void setTransferFromId(Long id) {
        transferFromId.setText(id + "");
    }

    public String getTransferFromId() {
        return transferFromId.getText();
    }

    public String getTransferToId() {
        return transferToId.getText();
    }

    public void showMessage(String message) {
        if (message != null)
            JOptionPane.showMessageDialog(this, message);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Istvan Nagy
        label1 = new JLabel();
        usernameText = new JTextField();
        logoutButton = new JButton();
        label2 = new JLabel();
        name = new JTextField();
        label5 = new JLabel();
        accountBalance = new JTextField();
        label3 = new JLabel();
        idNumber = new JTextField();
        label6 = new JLabel();
        accountType = new JTextField();
        label4 = new JLabel();
        address = new JTextField();
        addClientButton = new JButton();
        addAccountButton = new JButton();
        findClientButton = new JButton();
        clientID = new JTextField();
        updateAccountButton = new JButton();
        updateClientButton = new JButton();
        deleteAccountButton = new JButton();
        findAllClientsButton = new JButton();
        viewAccountButton = new JButton();
        scrollPane1 = new JScrollPane();
        clientTable = new JTable();
        scrollPane2 = new JScrollPane();
        accountTable = new JTable();
        label7 = new JLabel();
        transferFromText = new JTextField();
        label10 = new JLabel();
        billDescriptionText = new JTextField();
        transferFromButton = new JButton();
        transferFromId = new JTextField();
        label11 = new JLabel();
        billAmountText = new JTextField();
        label8 = new JLabel();
        transferToText = new JTextField();
        transferToButton = new JButton();
        transferToId = new JTextField();
        label9 = new JLabel();
        amountText = new JTextField();
        transferButton = new JButton();
        payBillButton = new JButton();

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

        //---- usernameText ----
        usernameText.setEditable(false);
        contentPane.add(usernameText, "cell 1 0,width 200:200:200");

        //---- logoutButton ----
        logoutButton.setText("Logout");
        contentPane.add(logoutButton, "cell 4 0");

        //---- label2 ----
        label2.setText("name");
        contentPane.add(label2, "cell 0 2");
        contentPane.add(name, "cell 1 2,width 250:250:250");

        //---- label5 ----
        label5.setText("balance");
        contentPane.add(label5, "cell 2 2");
        contentPane.add(accountBalance, "cell 3 2,width 250:250:250");

        //---- label3 ----
        label3.setText("id number");
        contentPane.add(label3, "cell 0 3");
        contentPane.add(idNumber, "cell 1 3,width 250:250:250");

        //---- label6 ----
        label6.setText("type");
        contentPane.add(label6, "cell 2 3");
        contentPane.add(accountType, "cell 3 3,width 250:250:250");

        //---- label4 ----
        label4.setText("address");
        contentPane.add(label4, "cell 0 4");
        contentPane.add(address, "cell 1 4,width 250:250:250");

        //---- addClientButton ----
        addClientButton.setText("Add Client");
        contentPane.add(addClientButton, "cell 0 6");

        //---- addAccountButton ----
        addAccountButton.setText("Add Account");
        contentPane.add(addAccountButton, "cell 2 6");

        //---- findClientButton ----
        findClientButton.setText("Find Client");
        contentPane.add(findClientButton, "cell 0 7");
        contentPane.add(clientID, "cell 1 7,width 100:100:100");

        //---- updateAccountButton ----
        updateAccountButton.setText("Update Account");
        contentPane.add(updateAccountButton, "cell 2 7");

        //---- updateClientButton ----
        updateClientButton.setText("Update Client");
        contentPane.add(updateClientButton, "cell 0 8");

        //---- deleteAccountButton ----
        deleteAccountButton.setText("Delete Account");
        contentPane.add(deleteAccountButton, "cell 2 8");

        //---- findAllClientsButton ----
        findAllClientsButton.setText("Find All Clients");
        contentPane.add(findAllClientsButton, "cell 0 9");

        //---- viewAccountButton ----
        viewAccountButton.setText("View Account");
        contentPane.add(viewAccountButton, "cell 2 9");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(clientTable);
        }
        contentPane.add(scrollPane1, "cell 1 10");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(accountTable);
        }
        contentPane.add(scrollPane2, "cell 3 10");

        //---- label7 ----
        label7.setText("Transfer FROM");
        contentPane.add(label7, "cell 0 11");

        //---- transferFromText ----
        transferFromText.setEditable(false);
        contentPane.add(transferFromText, "cell 1 11");

        //---- label10 ----
        label10.setText("Bill description");
        contentPane.add(label10, "cell 2 11");
        contentPane.add(billDescriptionText, "cell 3 11");

        //---- transferFromButton ----
        transferFromButton.setText("Transfer FROM");
        contentPane.add(transferFromButton, "cell 0 12");

        //---- transferFromId ----
        transferFromId.setEditable(false);
        contentPane.add(transferFromId, "cell 1 12,width 100:100:100");

        //---- label11 ----
        label11.setText("Amount to pay");
        contentPane.add(label11, "cell 2 12");
        contentPane.add(billAmountText, "cell 3 12");

        //---- label8 ----
        label8.setText("Transfer TO");
        contentPane.add(label8, "cell 0 13");

        //---- transferToText ----
        transferToText.setEditable(false);
        contentPane.add(transferToText, "cell 1 13");

        //---- transferToButton ----
        transferToButton.setText("Transfer TO");
        contentPane.add(transferToButton, "cell 0 14");

        //---- transferToId ----
        transferToId.setEditable(false);
        contentPane.add(transferToId, "cell 1 14,width 100:100:100");

        //---- label9 ----
        label9.setText("Amount");
        contentPane.add(label9, "cell 0 15");
        contentPane.add(amountText, "cell 1 15,width 100:100:100");

        //---- transferButton ----
        transferButton.setText("Transfer");
        contentPane.add(transferButton, "cell 1 15");

        //---- payBillButton ----
        payBillButton.setText("Pay Bill");
        contentPane.add(payBillButton, "cell 2 15");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Istvan Nagy
    private JLabel label1;
    private JTextField usernameText;
    private JButton logoutButton;
    private JLabel label2;
    private JTextField name;
    private JLabel label5;
    private JTextField accountBalance;
    private JLabel label3;
    private JTextField idNumber;
    private JLabel label6;
    private JTextField accountType;
    private JLabel label4;
    private JTextField address;
    private JButton addClientButton;
    private JButton addAccountButton;
    private JButton findClientButton;
    private JTextField clientID;
    private JButton updateAccountButton;
    private JButton updateClientButton;
    private JButton deleteAccountButton;
    private JButton findAllClientsButton;
    private JButton viewAccountButton;
    private JScrollPane scrollPane1;
    private JTable clientTable;
    private JScrollPane scrollPane2;
    private JTable accountTable;
    private JLabel label7;
    private JTextField transferFromText;
    private JLabel label10;
    private JTextField billDescriptionText;
    private JButton transferFromButton;
    private JTextField transferFromId;
    private JLabel label11;
    private JTextField billAmountText;
    private JLabel label8;
    private JTextField transferToText;
    private JButton transferToButton;
    private JTextField transferToId;
    private JLabel label9;
    private JTextField amountText;
    private JButton transferButton;
    private JButton payBillButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
