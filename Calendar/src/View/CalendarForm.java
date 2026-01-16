package View;
import Controller.*;
import Model.*;

import javax.swing.*;
import java.awt.*;

public class CalendarForm extends JFrame {
    private View view;
    private CardLayout cardLayout;
    private int currentyear;
    private int currentmonth;
    private int currentday;
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
        contentPane.add(CreateCalendarPanel, "CreateCalendarPanel");
        contentPane.add(SignedInPanel, "SignedInPanel");
        contentPane.add(ZoomedInPanel, "ZoomedInPanel");

        //initializing the customcalendar
        customMonthPanel.add(new MonthView(1, 1));
        customDayPanel.add(new DayView());

        //******************************************************** Main Menu State ******************************************************************//
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

        //******************************************************** Create Calendar State ******************************************************************//

        CreateCalendarButton.addActionListener(e -> {
            String name = TitleOfCalendar.getText();
            String start = StartOfSemester.getSelectedItem().toString();
            int length =  Integer.parseInt(LengthOfStay.getSelectedItem().toString());
            int year = Integer.parseInt(yearTextField.getText().toString());
            new AddCalendarCommand(view.getController(), name, length, start, year).execute();
        });

        //******************************************************** Signed In State ******************************************************************//
        signedinSignOutButton.addActionListener(e -> {
            new SignOutCommand(view.getController()).execute();
        });

        openCalendar.addActionListener(e -> {
            int indexOfCalendar = signedinCalendarlist.getSelectedIndex();
            Calendar calendar = view.getController().getCalendar(indexOfCalendar);
            currentCalendar = calendar;
            currentmonth = 1;
            currentyear = calendar.getYear();
            repaintMonthView(calendar);
        });

        signedinDeleteButton.addActionListener(e -> {
            int index = signedinCalendarlist.getSelectedIndex();
            //if this was the last calendar, the model goes to createcalendar state
            new RemoveCalendarCommand(view.getController(), index).execute();

        });

        signedinZoomInButton.addActionListener(e -> {
           int day = Integer.parseInt(zoomintextfield.getText().toString());
           currentday = day;
           new ZoomInCommand(view.getController(), currentyear, currentmonth, currentday).execute();
        });

        previousMonthButton.addActionListener(e -> {
            if(currentmonth > 1){
                currentmonth--;
            }
            //if the new month is december
            if((currentCalendar.getSeason() == "Autumn" && currentmonth == 4) || (currentCalendar.getSeason() == "Spring" && currentmonth == 10)){
                currentyear--;
            }
            repaintMonthView(currentCalendar);
        });

        nextMonthButton.addActionListener(e -> {
            if(currentmonth < currentCalendar.getLength()){
                currentmonth++;
            }
            //if the new month is january
            if((currentCalendar.getSeason() == "Autumn" && currentmonth == 5) || (currentCalendar.getSeason() == "Spring" && currentmonth == 11)){
                currentyear++;
            }
            repaintMonthView(currentCalendar);
        });

        modifyTitleButton.addActionListener(e -> {
            String newtitle = newtitletextField.getText();
            new ModifyCalendarCommand(view.getController(), newtitle);
        });

        //******************************************************** Zoomed In State ******************************************************************//
        addEventButton.addActionListener(e -> {
            String text = addEventTextField.getText();
            String title = addEventTitleField.getText();
            int length = Integer.parseInt(addEventRecurring.getSelectedItem().toString());
            String label =  addEventLabelBox.getSelectedItem().toString();
            new AddEventCommand(view.getController(), title, text, label, length);
        });
        removeEventButton.addActionListener(e -> {
            int index = removeEventBox.getSelectedIndex();
            new RemoveEventCommand(view.getController(), index);
        });
        modifyEventButton.addActionListener(e -> {
            int index = modifyEventBox.getSelectedIndex();
            String text = modifyEventText.getText();
            new ModifyEventCommand(view.getController(), index, text);
        });
        addNoteButton.addActionListener(e -> {
            String title = addNoteTitleField.getText();
            String text  = addNoteTextField.getText();
            new AddNoteCommand(view.getController(), title, text);
        });
        removeNoteButton.addActionListener(e -> {
            int index = removeNoteBox.getSelectedIndex();
            new RemoveNoteCommand(view.getController(), index);
        });
        modifyNoteButton.addActionListener(e -> {
            int index = modifyNoteBox.getSelectedIndex();
            String text = modifyNoteText.getText();
            new ModifyNoteCommand(view.getController(), index, text);
        });
        zoomOutButton.addActionListener(e -> {
           new  ZoomOutCommand(view.getController()).execute();
        });
    }


    //******************************************************** Helping Functions ******************************************************************//
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
    public JPanel getCustomCalendarPanel(){
        return customMonthPanel;
    }
    public Calendar getCurrentCalendar(){
        return currentCalendar;
    }

    public void repaintMonthView(Calendar calendar){
        customMonthPanel.removeAll();
        MonthView monthView = new MonthView(currentCalendar.getYear(), currentmonth);
        customMonthPanel.add(monthView, new BorderLayout());
    }
}