package View;
import Controller.*;
import Model.*;
import Model.Event;
import javax.swing.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The main graphical user interface for the Erasmus Calendar application.
 * This class extends {@link JFrame} and manages multiple views using a {@link CardLayout}.
 * It handles the transitions between the main menu, calendar creation, month view,
 * and detailed day view, while delegating user actions to command objects.
 */
public class CalendarForm extends JFrame {
    private View view;
    private CardLayout cardLayout;
    private LocalDate displayedMonth;
    private Calendar currentCalendar;

    private JPanel contentPane;
    private JPanel MainMenuPanel;
    private JPanel SignedInPanel;
    private JPanel CreateCalendarPanel;
    private JPanel ZoomedInPanel;

    //Main Menu Panel
    private JButton registerButton;
    private JButton exitFromMainMenuButton;
    private JButton signInButton;
    private JTextField signintextField;
    private JPasswordField signinpasswordField;
    private JTextField registertextField;
    private JPasswordField registerpasswordField;
    private JLabel birthDay;
    private JTextField birthDayTextField;
    private JLabel mainPageLabel;
    private JLabel signusernameLabel;
    private JLabel signpasswordLabel;
    private JLabel registerusernameLabel;
    private JLabel registerpasswordLabel;

    //Create Calendar Panel
    private JButton CreateCalendarButton;
    private JTextField TitleOfCalendar;


    private JComboBox LengthOfStay;
    private JComboBox StartOfSemester;
    private JTextField yearTextField;


    //Signed in Panel
    private JComboBox signedinCalendarlist;
    private JButton signedinZoomInButton;
    private JButton signedinSignOutButton;
    private JButton signedinDeleteButton;
    private JTextField zoomintextfield;
    private JButton nextMonthButton;
    private JButton previousMonthButton;
    private JButton openCalendar;

    //Zoomed in Panel
    private JPanel separator;
    private JPanel customDayPanel;
    private JButton addEventButton;
    private JTextField addEventTitleField;
    private JTextField addEventTextField;
    private JComboBox addEventLabelBox;
    private JComboBox addEventRecurring;
    private JComboBox removeEventBox;
    private JComboBox removeNoteBox;
    private JComboBox modifyEventBox;
    private JComboBox modifyNoteBox;
    private JButton addNoteButton;
    private JTextField addNoteTitleField;
    private JTextField addNoteTextField;
    private JButton removeEventButton;
    private JButton removeNoteButton;
    private JButton modifyEventButton;
    private JTextField modifyEventText;
    private JButton modifyNoteButton;
    private JTextField modifyNoteText;
    private JPanel customMonthPanel;
    private JButton zoomOutButton;
    private JTextField newtitletextField;
    private JButton modifyTitleButton;
    private JLabel newTitleLabel;
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JButton createNewCalendarButton;
    private JButton exitFromZoomedInButton;
    private JButton exitFromSignedInButton;


    /**
     * Constructs a new CalendarForm, initializes the UI components,
     * and sets up the action listeners for the various application states.
     * * @param view The View object that acts as a bridge to the Controller.
     */
    public CalendarForm(View view) {
        this.view = view;

        setTitle("Erasmus calendar");
        setContentPane(contentPane);
        contentPane.setBackground(new Color(245, 245, 245));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setVisible(true);

        stylePanel(MainMenuPanel);
        stylePanel(SignedInPanel);
        stylePanel(CreateCalendarPanel);
        stylePanel(ZoomedInPanel);

        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);
        contentPane.add(MainMenuPanel, "MainMenuPanel");
        contentPane.add(CreateCalendarPanel, "CreateCalendarPanel");
        contentPane.add(SignedInPanel, "SignedInPanel");
        contentPane.add(ZoomedInPanel, "ZoomedInPanel");


        //******************************************************** Main Menu State ******************************************************************//
        exitFromMainMenuButton.addActionListener(e -> {
            new ExitCommand(view.getController()).execute();
        });

        signInButton.addActionListener(e -> {
            String username = signintextField.getText();
            String password = signinpasswordField.getText();
            try {
                new SignInCommand(view.getController(), username, password).execute();
                signintextField.setText("");
                signinpasswordField.setText("");

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        });

        registerButton.addActionListener(e -> {
            String username = registertextField.getText();
            String password = registerpasswordField.getText();
            String birthday = birthDayTextField.getText();
            try {
                new RegisterCommand(view.getController(), username, password, birthday).execute();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            birthDayTextField.setText("");
            registertextField.setText("");
            registerpasswordField.setText("");
        });

        //******************************************************** Create Calendar State ******************************************************************//

        CreateCalendarButton.addActionListener(e -> {
            String name = TitleOfCalendar.getText().toString();
            String start = StartOfSemester.getSelectedItem().toString();
            int length = Integer.parseInt(LengthOfStay.getSelectedItem().toString());
            int year = Integer.parseInt(yearTextField.getText().toString());
            new AddCalendarCommand(view.getController(), name, length, start, year).execute();
            TitleOfCalendar.setText("");
            StartOfSemester.setSelectedIndex(0);
            yearTextField.setText("");
            LengthOfStay.setSelectedIndex(0);
        });

        //******************************************************** Signed In State ******************************************************************//
        signedinSignOutButton.addActionListener(e -> {
            new SignOutCommand(view.getController()).execute();
        });

        openCalendar.addActionListener(e -> {
            int indexOfCalendar = signedinCalendarlist.getSelectedIndex();
            Calendar calendar = view.getController().getCalendar(indexOfCalendar);
            view.getController().setCurrentCalendar(currentCalendar);

            currentCalendar = calendar;
            displayedMonth = currentCalendar.getStartDate().withDayOfMonth(1);
            refreshYearMonthLabels();
            repaintMonthView();
        });

        signedinDeleteButton.addActionListener(e -> {
            int index = signedinCalendarlist.getSelectedIndex();
            //if this was the last calendar, the model goes to createcalendar state
            new RemoveCalendarCommand(view.getController(), index).execute();
        });

        signedinZoomInButton.addActionListener(e -> {
            try {
                int day = Integer.parseInt(zoomintextfield.getText());
                LocalDate clickedDate = displayedMonth.withDayOfMonth(day);
                currentCalendar.setCurrentDate(clickedDate);

                customDayPanel.removeAll();
                customDayPanel.add(new DayView(currentCalendar, clickedDate));
                customDayPanel.revalidate();
                customDayPanel.repaint();

                new ZoomInCommand(
                        view.getController(),
                        clickedDate.getYear(),
                        clickedDate.getMonthValue(),
                        clickedDate.getDayOfMonth()
                ).execute();
                zoomintextfield.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid day input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        previousMonthButton.addActionListener(e -> {
            LocalDate prevMonth = displayedMonth.minusMonths(1);

            if (!prevMonth.isBefore(currentCalendar.getStartDate())) {
                displayedMonth = prevMonth;
                refreshYearMonthLabels();
                repaintMonthView();
            }
        });

        nextMonthButton.addActionListener(e -> {
            LocalDate nextMonth = displayedMonth.plusMonths(1);
            if (!nextMonth.isAfter(currentCalendar.getEndDate())) {
                displayedMonth = nextMonth;
                refreshYearMonthLabels();
                repaintMonthView();
            }
        });

        modifyTitleButton.addActionListener(e -> {
            String newtitle = newtitletextField.getText();
            new ModifyCalendarCommand(view.getController(), newtitle).execute();
            newtitletextField.setText("");
        });

        createNewCalendarButton.addActionListener(e -> {
            new SwitchToCalendarCreationCommand(view.getController()).execute();
        });
        exitFromSignedInButton.addActionListener(e -> {
            new ExitCommand(view.getController()).execute();
        });

        //******************************************************** Zoomed In State ******************************************************************//
        addEventButton.addActionListener(e -> {
            String text = addEventTextField.getText();
            String title = addEventTitleField.getText();
            int length = Integer.parseInt(addEventRecurring.getSelectedItem().toString());
            String label = addEventLabelBox.getSelectedItem().toString();
            new AddEventCommand(view.getController(), title, text, label, length).execute();
            addEventTextField.setText("");
            addEventTitleField.setText("");
        });
        removeEventButton.addActionListener(e -> {
            int index = removeEventBox.getSelectedIndex();
            new RemoveEventCommand(view.getController(), index).execute();
        });
        modifyEventButton.addActionListener(e -> {
            int index = modifyEventBox.getSelectedIndex();
            String text = modifyEventText.getText();
            new ModifyEventCommand(view.getController(), index, text).execute();
            modifyEventText.setText("");
        });
        addNoteButton.addActionListener(e -> {
            String title = addNoteTitleField.getText();
            String text = addNoteTextField.getText();
            new AddNoteCommand(view.getController(), title, text).execute();
            addNoteTextField.setText("");
            addNoteTextField.setText("");
        });
        removeNoteButton.addActionListener(e -> {
            int index = removeNoteBox.getSelectedIndex();
            new RemoveNoteCommand(view.getController(), index).execute();
        });
        modifyNoteButton.addActionListener(e -> {
            int index = modifyNoteBox.getSelectedIndex();
            String text = modifyNoteText.getText();
            new ModifyNoteCommand(view.getController(), index, text).execute();
            modifyNoteText.setText("");
        });
        zoomOutButton.addActionListener(e -> {
            new ZoomOutCommand(view.getController()).execute();
        });
        exitFromZoomedInButton.addActionListener(e -> {
            new ExitCommand(view.getController()).execute();
        });
    }


    //******************************************************** Helping Functions ******************************************************************//


    /**
     * Gets the CardLayout manager used for switching between panels.
     * * @return The CardLayout instance.
     */
    public CardLayout getCardLayout() {
        return this.cardLayout;
    }

    /**
     * Gets the main content pane that holds all application panels.
     * * @return The content pane JPanel.
     */
    public JPanel getContentPane() {
        return contentPane;
    }


    /**
     * Refreshes the MonthView component based on the current displayedMonth
     * and currentCalendar data.
     */
    public void repaintMonthView() {
        customMonthPanel.removeAll();
        MonthView monthView = new MonthView(displayedMonth, currentCalendar);
        customMonthPanel.setLayout(new BorderLayout());
        customMonthPanel.add(monthView, BorderLayout.CENTER);
        customMonthPanel.revalidate();
        customMonthPanel.repaint();
    }

    /**
     * Initializes custom UI components. This is used by the GUI builder
     * for custom layout components like month and day containers.
     */
    private void createUIComponents() {
        customMonthPanel = new JPanel(new BorderLayout());
        customDayPanel = new JPanel(new BorderLayout());
    }


    /**
     * Replaces the current items in the calendar selection combo box
     * with names from the provided list.
     * * @param calendarList The list of calendars to display.
     */
    public void refreshCalendarList(ArrayList<Calendar> calendarList) {
        signedinCalendarlist.removeAllItems();
        //System.out.println(signedinCalendarlist.getModel().getSize
        for (Calendar calendar : calendarList) {
            signedinCalendarlist.addItem(calendar.getName());
        }
    }

    /**
     * Refreshes the combo boxes used for removing and modifying notes.
     * * @param notesList The current list of notes for the selected day.
     */
    public void refreshNotesList(ArrayList<Note> notesList){
        removeNoteBox.removeAllItems();
        modifyNoteBox.removeAllItems();
        for(Note note : notesList){
            removeNoteBox.addItem(note.getTitle());
            modifyNoteBox.addItem(note.getTitle());
        }
    }

    /**
     * Refreshes the combo boxes used for removing and modifying events.
     * * @param eventsList The current list of events for the selected day.
     */
    public void refreshEventsList(ArrayList<Event> eventsList){
        removeEventBox.removeAllItems();
        modifyEventBox.removeAllItems();
        for(Event event : eventsList){
            removeEventBox.addItem(event.getTitle());
            modifyEventBox.addItem(event.getTitle());
        }
    }

    /**
     * Updates the text of labels showing the currently displayed year and month.
     */
    public void refreshYearMonthLabels() {
        yearLabel.setText("Year: " + displayedMonth.getYear());
        monthLabel.setText("Month: " + displayedMonth.getMonthValue());
    }

    /**
     * Updates the detailed day view panel with the events and notes
     * of the currently selected date.
     */
    public void refreshDayPanel(){
        customDayPanel.removeAll();
        customDayPanel.add(new DayView(currentCalendar, currentCalendar.getCurrentDate()));
        customDayPanel.revalidate();
        customDayPanel.repaint();
    }

    /**
     * Sets the month that should currently be displayed in the calendar view.
     * * @param displayedMonth The LocalDate representing the month to view.
     */
    public void setDisplayedMonth(LocalDate displayedMonth) {
        this.displayedMonth = displayedMonth;
    }

    /**
     * Sets the calendar object currently being used by the application.
     * * @param currentCalendar The Calendar model instance.
     */
    public void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }

    /**
     * Switches the CardLayout to display the Main Menu panel.
     */
    public void showMainMenuPanel(){
        cardLayout.show(contentPane, "MainMenuPanel");
    }

    /**
     * Switches the CardLayout to display the Calendar Creation panel.
     */
    public void showCreateCalendarPanel(){
        cardLayout.show(contentPane, "CreateCalendarPanel");
    }

    /**
     * Switches the CardLayout to display the main Signed In view (Month View).
     */
    public void showSignedInPanel(){
        cardLayout.show(contentPane, "SignedInPanel");
    }

    /**
     * Switches the CardLayout to display the Zoomed In view (Day View).
     */
    public void showZoomedInPanel(){
        cardLayout.show(contentPane, "ZoomedInPanel");
    }

    private void stylePanel(JPanel panel) {
        if (panel == null) return;
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Component comp : panel.getComponents()) {
            // Style Buttons
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setFocusPainted(false);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.setFont(new Font("SansSerif", Font.BOLD, 12));
            }
            // Style Text Fields
            if (comp instanceof JTextField) {
                ((JTextField) comp).setMargin(new Insets(2, 5, 2, 5));
            }
            // Style Labels
            if (comp instanceof JLabel) {
                comp.setFont(new Font("SansSerif", Font.PLAIN, 13));
            }
        }
    }
}