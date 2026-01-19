package Model;
import Database.CalendarTableManager;

import java.util.Date;

import java.util.HexFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a user within the system.
 * This class stores user credentials and personal information, manages the user's
 * unique identifier via hashing, and acts as a factory for creating various
 * calendar types (Short/Long, Autumn/Spring).
 */
public class User {
    //defining attributes of the User
    private String userName;
    private String password;
    private String birthDate;
    private String idNumber;
    private Calendar currentCalendar;
    private CalendarPool calendars;
    private CalendarTableManager calendarTableManager;

    /**
     * Constructs a new User and generates a unique ID based on the username.
     * * @param userName  The unique username for the account.
     * @param password  The account password.
     * @param birthDate The birth date of the user.
     */
    public User(String userName, String password, String birthDate) {
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.idNumber = hash(userName);
        this.calendars = new  CalendarPool();
        this.calendarTableManager = new CalendarTableManager();
    }

    /**
     * Creates a new calendar and adds it to both the local {@link CalendarPool}
     * and the database. The specific subclass of Calendar is determined by
     * the length and season parameters.
     * * @param name   The title of the calendar.
     * @param length The duration (1 for short, 2 for long).
     * @param season The starting season ("Autumn" or "Spring").
     * @param year   The year the calendar starts.
     */
    public void createCalendar(String name, int length, String season, int year) {
        if(calendars.nameAvailable(name)){
            if (season.equals("Autumn")) {
                calendars.addCalendar(new Calendar(length, name, Season.Autumn, year));
            } else {
                calendars.addCalendar(new Calendar(length, name, Season.Spring, year));
            }
            //add the new calendar to the database
            this.calendarTableManager.addCalendar(name,length,season,year,idNumber);
        }
    }

    /**
     * Generates an MD5 hash of the provided string to create a unique identifier.
     * * @param toHash The string to be hashed (typically the username).
     * @return A hexadecimal string representing the MD5 hash, or null if the algorithm is unavailable.
     */
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

    /** @return The username. */
    public String getUserName() {
        return userName;
    }

    /** @return The user's password. */
    public String getPassword() {
        return password;
    }

    /** @return The user's birth date string. */
    public String getBirthDate() {
        return birthDate;
    }

    /** @return The hashed unique ID number. */
    public String getIdNumber() {
        return idNumber;
    }

    /** @return The {@link CalendarPool} associated with this user. */
    public CalendarPool getCalendarPool() {
        return calendars;
    }

    /** @return The currently active {@link Calendar}. */
    public Calendar getCurrentCalendar() {
        return currentCalendar;
    }

    /** @param currentCalendar The {@link Calendar} to set as active. */
    public void setCurrentCalendar(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }

    /** @param userName The username to set. */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** @param password The password to set. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @param birthDate The birth date to set. */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /** @param idNumber The unique ID to set. */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /** @param calendars The {@link CalendarPool} to associate with the user. */
    public void setCalendars(CalendarPool calendars) {
        this.calendars = calendars;
    }

    /**
     * Returns a string representation of the User and their associated calendars.
     * @return Formatted string with user details.
     */
    @Override
    public String toString() {
        return "User [userName=" + userName + ", password=" + password + ", birthDate=" + birthDate + ", idNumber="
                + idNumber + ", calendars=" + calendars + ", getUserName()=" + getUserName() + ", getPassword()="
                + getPassword() + ", getBirthDate()=" + getBirthDate() + ", getIdNumber()=" + getIdNumber()
                + ", getCalendars()=" + getCalendarPool() + "]";
    }
}