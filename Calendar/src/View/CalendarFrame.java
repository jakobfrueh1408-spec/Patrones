package View;

import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalendarFrame extends JFrame {
    private View view;

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


    private SmartButton exitFromMenuButton;
    private SmartButton signInButton;
    private SmartButton registerButton;
    private SmartButton addCalendarButton;
    private SmartButton removeCalendarButton;
    private SmartButton modifyCalendarButton;
    private SmartButton logoutButton;
    private SmartButton zoomInButton;
    private SmartButton zoomOutButton;
    private SmartButton addNoteButton;
    private SmartButton removeNoteButton;
    private SmartButton modifyNoteButton;
    private SmartButton addEventButton;
    private SmartButton removeEventButton;
    private SmartButton modifyEventButton;

    public CalendarFrame(View view) {
        this.view = view;

        cardLayout = new CardLayout();
        cardsPanel = new JPanel();
        cardsPanel.setLayout(cardLayout);

        mainMenuPanel = new JPanel();
        mainMenuPanel.setBackground(Color.BLUE);

        loggedinPanel = new JPanel();
        loggedinPanel.setBackground(Color.GREEN);

        zoomedInPanel = new JPanel();
        zoomedInPanel.setBackground(Color.RED);

        //if a command does not have parameters, it can be attached to the ui element here
        //if a command does have parameters, the parameters have to be refreshed every time the ui element executes it
        exitFromMenuButton = new SmartButton(new ExitCommand(this.view.getController()), "Exit");
        signInButton = new SmartButton(new SignInCommand(this.view.getController()), "Log In");

        registerButton = new SmartButton(new SignInCommand(this.view.getController()), "Register");
        addCalendarButton = new SmartButton(new SignInCommand(this.view.getController()), "Add Calendar");
        removeCalendarButton = new SmartButton(new SignInCommand(this.view.getController()), "Remove Calendar");
        modifyCalendarButton = new SmartButton(new SignInCommand(this.view.getController()), "Modify Calendar");
        logoutButton = new SmartButton(new SignInCommand(this.view.getController()), "Log Out");
        zoomInButton = new SmartButton(new SignInCommand(this.view.getController()), "Zoom In");
        zoomOutButton = new SmartButton(new SignInCommand(this.view.getController()), "Zoom Out");
        addNoteButton = new SmartButton(new SignInCommand(this.view.getController()), "Add Note");
        removeNoteButton = new SmartButton(new SignInCommand(this.view.getController()), "Remove Note");
        modifyNoteButton = new SmartButton(new SignInCommand(this.view.getController()), "Modify Note");
        addEventButton = new SmartButton(new SignInCommand(this.view.getController()), "Add Event");
        removeEventButton = new SmartButton(new SignInCommand(this.view.getController()), "Remove Event");
        modifyEventButton = new SmartButton(new SignInCommand(this.view.getController()), "Modify Event");


        mainMenuButtonPanel = new JPanel();
        mainMenuButtonPanel.setBackground(Color.GRAY);
        mainMenuButtonPanel.setLayout(new FlowLayout());
        mainMenuButtonPanel.setBounds(0, 0, this.getWidth(), 50);
        mainMenuButtonPanel.add(signInButton);
        mainMenuButtonPanel.add(registerButton);
        mainMenuButtonPanel.add(exitFromMenuButton);

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


        exitFromMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitFromMenuButton.getCommand().execute();
            }
        });

        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signInButton.getCommand().fetchParameters();
                signInButton.getCommand().execute();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logoutButton.getCommand().execute();
            }
        });

        setTitle("Model.Calendar");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().add(cardsPanel);
    }
}
