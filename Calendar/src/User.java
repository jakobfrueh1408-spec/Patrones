import java.util.Date;

public class User {
    private String userName;
    private String password;
    private Date birthDate;
    private int idNumber;
    private CalendarPool calendars;
    public User(String userName, String password, Date birthDate, int idNumber, CalendarPool calendars) {
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.idNumber = idNumber;
        this.calendars = calendars;
    }
    public void createCalendar(int length, String name, Season season) {
        if(length == 1){
            if(season == Season.Autumn){
                calendars.addCalendar(new ShortAutumnCalendar(name));
            } else {
                calendars.addCalendar(new ShortSpringCalendar(name));
            }
        } else {
            if(season == Season.Autumn){
                calendars.addCalendar(new LongAutumnCalendar(name));
            } else {
                calendars.addCalendar(new LongSpringCalendar(name));
            }
        }
    }
}

