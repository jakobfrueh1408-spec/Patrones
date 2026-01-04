package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalendarFrame extends JFrame {
    private Controller controller;
    //not logged in

    private int currentCard = 1;

    private CardLayout cardLayout;

    private JPanel cardsPanel;

    private JPanel mainPanel;

    //logged in panel, you can see your calendars in a list on the side and you can observe one
    private JPanel loggedinPanel;

    //you are zoomed in and you can see a day in a detailed way
    private JPanel zoomedInPanel;

    private JPanel buttonPanel;

    private JButton nextButton;

    public CalendarFrame(Controller controller) {

        cardsPanel = new JPanel();

        cardLayout = new CardLayout();

        this.controller = controller;
        cardsPanel.setLayout(cardLayout);

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        loggedinPanel = new JPanel();
        loggedinPanel.setBackground(Color.BLACK);

        zoomedInPanel = new JPanel();
        zoomedInPanel.setBackground(Color.BLUE);

        nextButton = new JButton("Next");

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(nextButton);

        cardsPanel.add(mainPanel);
        cardsPanel.add(loggedinPanel);
        cardsPanel.add(zoomedInPanel);


        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(cardsPanel);
            }
        });

        setTitle("Model.Calendar");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().add(cardsPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}
