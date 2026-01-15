package Model;

import java.util.Date;
import java.util.HexFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
public class User {
    //defining attributes of the USer
    private String userName;
    private String password;
    private String birthDate;
    private String idNumber;
    private Calendar currentCalendar; 

    private CalendarPool calendars;
    
    public User(String userName, String password, String birthDate) {
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.idNumber = hash(userName);
        this.calendars = new  CalendarPool();
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
    public CalendarPool getCalendars() {
        return calendars;
    }
     public void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }
    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName + ", password=" + password + ", birthDate=" + birthDate + ", idNumber="
                + idNumber + ", calendars=" + calendars + ", getUserName()=" + getUserName() + ", getPassword()="
                + getPassword() + ", getBirthDate()=" + getBirthDate() + ", getIdNumber()=" + getIdNumber()
                + ", getCalendars()=" + getCalendars() + "]";
    }

}


