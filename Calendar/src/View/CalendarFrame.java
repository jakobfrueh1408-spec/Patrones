package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalendarFrame extends JFrame {
    private Controller controller;

    private CardLayout cardLayout;
    private JPanel cardsPanel;

    private JPanel mainMenuPanel;
    private JPanel mainMenuButtonPanel;

    //logged in panel, you can see your calendars in a list on the side and you can observe one
    private JPanel loggedinPanel;
    private JPanel loggedInButtonPanel;

    //you are zoomed in and you can see a day in a detailed way
    private JPanel zoomedInPanel;
    private JPanel zoomedInButtonPanel;


    private JButton exitButton;
    private JButton signInButton;
    private JButton registerButton;
    private JButton addCalendarButton;
    private JButton removeCalendarButton;
    private JButton modifyCalendarButton;
    private JButton logoutButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton addNoteButton;
    private JButton removeNoteButton;
    private JButton addEventButton;
    private JButton removeEventButton;

    public CalendarFrame(Controller controller) {
        this.controller = controller;

        cardLayout = new CardLayout();
        cardsPanel = new JPanel();
        cardsPanel.setLayout(cardLayout);

        mainMenuPanel = new JPanel();
        mainMenuPanel.setBackground(Color.BLUE);

        loggedinPanel = new JPanel();
        loggedinPanel.setBackground(Color.GREEN);

        zoomedInPanel = new JPanel();
        zoomedInPanel.setBackground(Color.RED);

        exitButton = new JButton("Exit");
        signInButton = new JButton("Sign In");
        registerButton = new JButton("Register");
        addCalendarButton = new JButton("Add Calendar");
        removeCalendarButton = new JButton("Remove Calendar");
        modifyCalendarButton = new JButton("Modify Calendar");
        logoutButton = new JButton("Logout");
        zoomInButton = new JButton("Zoom In");
        zoomOutButton = new JButton("Zoom Out");
        addNoteButton = new JButton("Add Note");
        removeNoteButton = new JButton("Remove Note");
        addEventButton = new JButton("Add Event");
        removeEventButton = new JButton("Remove Event");


        mainMenuButtonPanel = new JPanel();
        mainMenuButtonPanel.setBackground(Color.GRAY);
        mainMenuButtonPanel.setLayout(new FlowLayout());
        mainMenuButtonPanel.setBounds(0, 0, this.getWidth(), 50);
        mainMenuButtonPanel.add(signInButton);
        mainMenuButtonPanel.add(registerButton);
        mainMenuButtonPanel.add(exitButton);

        loggedInButtonPanel = new JPanel();
        loggedInButtonPanel.setBackground(Color.GRAY);
        loggedInButtonPanel.setLayout(new FlowLayout());
        loggedInButtonPanel.add(zoomInButton);
        loggedInButtonPanel.add(addCalendarButton);
        loggedInButtonPanel.add(removeCalendarButton);
        loggedInButtonPanel.add(modifyCalendarButton);
        loggedInButtonPanel.add(logoutButton);

        zoomedInButtonPanel = new JPanel();
        zoomedInButtonPanel.setBackground(Color.GRAY);
        zoomedInButtonPanel.setLayout(new FlowLayout());
        zoomedInButtonPanel.add(zoomOutButton);
        zoomedInButtonPanel.add(addNoteButton);
        zoomedInButtonPanel.add(removeNoteButton);
        zoomedInButtonPanel.add(addEventButton);
        zoomedInButtonPanel.add(removeEventButton);

        //MAIN MENU
        //String[] users = controller.getUserNames();
        String[] users = {"andris", "jakob"};
        MainMenuView mainmenu = new MainMenuView(users);
        mainmenu.setPreferredSize(new Dimension(100, 100));
        mainMenuPanel.setLayout(new BorderLayout());
        mainMenuPanel.add(mainMenuButtonPanel, BorderLayout.NORTH);
        mainMenuPanel.add(mainmenu, BorderLayout.CENTER);

        //LOGGED IN
        CalendarView tmp = new CalendarView(28, 7);
        loggedinPanel.add(loggedInButtonPanel, BorderLayout.SOUTH);
        loggedinPanel.add(tmp, BorderLayout.NORTH);

        zoomedInPanel.add(zoomedInButtonPanel, BorderLayout.SOUTH);

        cardsPanel.add(mainMenuPanel);
        cardsPanel.add(loggedinPanel);
        cardsPanel.add(zoomedInPanel);


        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.loginClicked();
                cardLayout.next(cardsPanel);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.logoutClicked();
                cardLayout.previous(cardsPanel);
            }
        });

        setTitle("Model.Calendar");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().add(cardsPanel);
    }
}
