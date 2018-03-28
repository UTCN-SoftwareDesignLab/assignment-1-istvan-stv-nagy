/*
 * Created by JFormDesigner on Sat Mar 24 22:36:10 EET 2018
 */

package presentation.login;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.miginfocom.swing.*;

/**
 * @author Istvan Nagy
 */
public class LoginPage extends JFrame {
    public LoginPage() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    public void setupButtons(ActionListener listener) {
        registerButton.setActionCommand("register");
        loginButton.setActionCommand("login");
        registerButton.addActionListener(listener);
        loginButton.addActionListener(listener);
    }

    public void resetPage() {
        username.setText("");
        password.setText("");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public String getUsername() {
        return username.getText();
    }

    public char[] getPassword() {
        return password.getPassword();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Istvan Nagy
        label3 = new JLabel();
        username = new JTextField();
        label4 = new JLabel();
        password = new JPasswordField();
        loginButton = new JButton();
        registerButton = new JButton();

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
            "[]"));

        //---- label3 ----
        label3.setText("username");
        contentPane.add(label3, "cell 0 2");
        contentPane.add(username, "cell 1 2 4 1,width 200:200:200");

        //---- label4 ----
        label4.setText("password");
        contentPane.add(label4, "cell 0 3");
        contentPane.add(password, "cell 1 3 4 1");

        //---- loginButton ----
        loginButton.setText("Login");
        contentPane.add(loginButton, "cell 0 7 4 1");

        //---- registerButton ----
        registerButton.setText("Register");
        contentPane.add(registerButton, "cell 4 7");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Istvan Nagy
    private JLabel label3;
    private JTextField username;
    private JLabel label4;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
