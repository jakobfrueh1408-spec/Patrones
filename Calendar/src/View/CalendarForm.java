package View;
import Controller.*;
import Model.Season;

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

    //Main Menu Panel
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

    //Empty Signed In Panel
    private JButton emptyCreateCalendarButton;
    private JButton emptySignOutButton;
    private JTextField emptyTitleOfCalendar;
    private JComboBox emptyLengthOfStay;
    private JComboBox emptyStartOfSemester;


    //Signed in Panel
    private JComboBox signedinCalendarlist;
    private JButton signedinZoomInButton;
    private JButton signedinSignOutButton;
    private JButton signedinDeleteButton;

    private JLabel birthDay;
    private JTextField birthDayTextField;
    private JPanel separator;

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
        contentPane.add(ZoomedInPanel, "ZoomedInPanel");

        exitFromMainMenuButton.addActionListener(e -> {
            new ExitCommand(view.getController()).execute();
        });
        signInButton.addActionListener(e -> {
            String username = signintextField.getText();
            String password = signinpasswordField.getText();
            new SignInCommand(view.getController(), username, password).execute();
        });
        registerButton.addActionListener( e -> {
            String username = registertextField.getText();
            String password = registerpasswordField.getText();
            String birthday = birthDayTextField.getText();
            new RegisterCommand(view.getController(), username, password,  birthday).execute();
        });

        emptySignOutButton.addActionListener(e -> {
            new SignOutCommand(view.getController()).execute();
        });

        emptyCreateCalendarButton.addActionListener( e -> {
            String name = emptyTitleOfCalendar.getText();
            int length =  Integer.parseInt(emptyLengthOfStay.getSelectedItem().toString());
            String start = emptyStartOfSemester.getSelectedItem().toString();
            Season season;
            if(start.equals("Autumn"))
                season = Season.Autumn;
            else
                season = Season.Spring;
            new AddCalendarCommand(view.getController(), name, length, season).execute();
        });

        signedinSignOutButton.addActionListener(e -> {
            new SignOutCommand(view.getController()).execute();
        });

        signedinDeleteButton.addActionListener(e -> {
            int index = signedinCalendarlist.getSelectedIndex();
            new RemoveCalendarCommand(view.getController(), index).execute();
        });

        signedinZoomInButton.addActionListener(e -> {
           int index = signedinCalendarlist.getSelectedIndex();
           new ZoomInCommand(view.getController(), index).execute();
        });
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
    public CardLayout getCardLayout(){
        return this.cardLayout;
    }
    public JPanel getContentPane(){
        return contentPane;
    }
}
