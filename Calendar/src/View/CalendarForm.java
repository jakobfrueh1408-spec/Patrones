package View;
import Controller.*;

import javax.swing.*;
import java.awt.*;

public class CalendarForm extends JFrame {
    private View view;
    private CardLayout cardLayout;


    private JPanel contentPane;
    private JPanel MainMenuPanel;
    private JPanel SignedInPanel;
    private JPanel EmptySignedInPanel;
    private JPanel ZoomedInPanel;

    private JButton registerButton;
    private JButton exitFromMainMenuButton;
    private JButton signInButton;
    private JTextField signintextField;
    private JPasswordField signinpasswordField;
    private JTextField registertextField;
    private JPasswordField registerpasswordField;
    private JLabel mainPageLabel;
    private JLabel signusernameLabel;
    private JLabel signpasswordLabel;
    private JLabel registerusernameLabel;
    private JLabel registerpasswordLabel;

    private JComboBox comboBox1;
    private JButton openButton;
    private JButton logOutButton;





    public CalendarForm(View view) {
        this.view = view;
        setTitle("Erasmus calendar");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setVisible(true);

        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);
        contentPane.add(MainMenuPanel, "MainMenuPanel");
        contentPane.add(EmptySignedInPanel, "EmptySignedInPanel");
        contentPane.add(SignedInPanel, "SignedInPanel");

        exitFromMainMenuButton.addActionListener(new CommandActionListener(new ExitCommand(this.view.getController())));
        signInButton.addActionListener(new CommandActionListener(new SignInCommand(this.view.getController())));
        registerButton.addActionListener(new CommandActionListener(new RegisterCommand(this.view.getController())));
    }

    public void refreshState(String stateToGo){
        switch (stateToGo){
            case "EmptySignedIn":
            {
                this.cardLayout.show(contentPane ,"EmptySignedInPanel");
                break;
            }
            case "SignedIn":
            {
                this.cardLayout.show(contentPane ,"SignedInPanel");
                break;
            }
            case "ZoomedIn":
            {
                this.cardLayout.show(contentPane ,"ZoomedInPanel");
                break;
            }
            default:
            {
                this.cardLayout.show(contentPane, "MainMenuPanel");
            }
        }
    }

    public String getSigninUsername(){
        return this.signintextField.getText();
    }
    public String getSigninPassword(){
        return this.signinpasswordField.getText();
    }
    public String getRegisterUsername(){
        return this.registertextField.getText();
    }
    public String getRegisterPassword(){
        return this.registerpasswordField.getText();
    }
}
