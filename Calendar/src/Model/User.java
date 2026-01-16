package Model;
import Database.CalendarTableManager;

import java.util.Date;

import java.util.HexFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    //defining attributes of the USer
    private String userName;
    private String password;
    private String birthDate;
    private String idNumber;
    private Calendar currentCalendar;
    private CalendarPool calendars;
    private CalendarTableManager calendarTableManager;
    public User(String userName, String password, String birthDate) {
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.idNumber = hash(userName);
        this.calendars = new  CalendarPool();
        this.calendarTableManager = new CalendarTableManager();
    }

    public void createCalendar(String name, int length, String season, int year) {
       if(calendars.nameAvailable(name)){
           if(length == 1){
               if(season == Season.Autumn.toString()){
                   //add the new calendar to the list
                   calendars.addCalendar(new ShortAutumnCalendar(name,year));

               } else {
                   calendars.addCalendar(new ShortSpringCalendar(name,year));
               }
           } else {
               if(season == Season.Autumn.toString()){
                   calendars.addCalendar(new LongAutumnCalendar(name,year));
               } else {
                   calendars.addCalendar(new LongSpringCalendar(name,year));
               }
           }
           //add the new calendar to the database
           this.calendarTableManager.addCalendar(name,length,season,year,idNumber);
       }
    }

    public String hash (String toHash) {
        String result = ""; 
        String toHashCopy = new String(toHash);
        try{
            MessageDigest hasher = MessageDigest.getInstance("MD5");
            hasher.update(toHashCopy.getBytes());
            byte[] digest = hasher.digest(); 
            result = HexFormat.of().formatHex(digest).toUpperCase();
        }
        catch(NoSuchAlgorithmException e){
            return null;
        }
        System.out.println(result);
       return result; 
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public CalendarPool getCalendarPool() {
        return calendars;
    }
    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }
    public void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public void setCalendars(CalendarPool calendars) {
        this.calendars = calendars;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", password=" + password + ", birthDate=" + birthDate + ", idNumber="
                + idNumber + ", calendars=" + calendars + ", getUserName()=" + getUserName() + ", getPassword()="
                + getPassword() + ", getBirthDate()=" + getBirthDate() + ", getIdNumber()=" + getIdNumber()
                + ", getCalendars()=" + getCalendarPool() + "]";
    }

}


