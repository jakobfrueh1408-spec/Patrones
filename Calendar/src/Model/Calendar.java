package Model;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.time.ZoneId;

import static Model.Label.study;

public class Calendar {

    private int length;
    private int year;
    private Date currentDate;
    private String name;
    private Season season;
    private ArrayList <Event> events = new ArrayList<>();
    private ArrayList <Note> notes = new ArrayList<>();

    public Calendar(int length, String name, Season season, int year) {
        this.length = length;
        this.name = name;
        this.season = season;
        this.year = year;
    }

    //to String method
    @Override
    public String toString() {
        return "Calendar [length=" + length + ", name=" + name + ", season=" + season + ", events=" + events
                + ", notes=" + notes + "]";
    }

    // getters and setters
    public Date getCurrentDate() {
        return currentDate;
    }
    public int getCurrentYear(){
        int year = currentDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .getYear();
        return year;
    }
    public void setCurrentDate(Date currentDate) {this.currentDate = currentDate;}
    public void setName(String name) {
        this.name = name;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }
    public int getLength() {
        return length;
    }
    public String getName() {
        return name;
    }
    public Season getSeason() {
        return season;
    }
    public ArrayList<Event> getEvents() {
        return events;
    }
    public ArrayList<Note> getNotes() {
        return notes;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setSeason(Season season) {
        this.season = season;
    }
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    //functions for Event insertion , manipulation adn deletion
    //removing the specified event from the Event List
    public void removeEvent(String title) {
        //getting the current Days Events
        ArrayList <Event> currentDaysEvents = getCurrentDayEventList();
        //getting the corresponding event
        Event event = extractEventsByTitle(currentDaysEvents,title);
        //removing event
        events.remove(event);
    }
    //Only modify the description of the Event
    public void modifyEvent(String title, String description){
        //getting the current Days Events
        ArrayList <Event> currentDaysEvents = getCurrentDayEventList();
        //getting the corresponding event
        Event event = extractEventsByTitle(currentDaysEvents,title);
        //setting the new Description
        event.setDescription(description);
    }
    public void addEvent(String  title, String description, String label, int lengthOfOccurrence){
        //converting string to Enum
        Label eventLabel = null;
        switch (label){
            case "sport" : eventLabel = Label.sport; break;
            case "free time activity": eventLabel = Label.free_time_activity; break;
            case "travel": eventLabel = Label.travel; break;
            case "study": eventLabel = study; break;
        }
        //create new Event
        Event event = new Event(title,description,currentDate,eventLabel);
        //add base event
        events.add(event);
        //retrieve the Date fo our baseEvent but not by reference but by value
        Date baseDate =  event.getDate();
        System.out.println(baseDate.toString());
        for (int i = 0; i < lengthOfOccurrence; i++ ){
                //clone event , update Date according to i in weeks and add it into the events List
                Event reocurr =  event.clone();
                Date reocurrDate = addWeeks(baseDate, i+1); 
                reocurr.setDate(reocurrDate);
                events.add(reocurr);
        }
    }

    //functions for Note insertion , manipulation adn deletion
    //removing the specified event from the Event List
    public void removeNote(String title){
        //getting all the notes of the day
        ArrayList <Note> currentDaysNotes = getCurrentDayNoteList();
        //getting the note with the corresponding title
        Note note = extractNotesByTitle(currentDaysNotes,title);
        //removing the note from notes
        notes.remove(note);
    }
    //Only modify the description of the Note
    public void modifyNote(String title, String description) {
        //getting all the notes of the day
        ArrayList <Note> currentDaysNotes = getCurrentDayNoteList();
        //getting the note with the corresponding title
        Note note = extractNotesByTitle(currentDaysNotes,title);
        //setting the notes description
        note.setText(description);
    }
    public void addNote(String title,String text) {
        Note note = new Note(title,text,currentDate);
        notes.add(note);
    }


    /**
     * AUXILIARY METHODS
    **/

    public Event extractEventsByTitle(ArrayList <Event> toExtract,String title){
        Event filteredByTitle = null;
        for (Event event : toExtract){
            if(title.equals(event.getTitle())){
                filteredByTitle = event;
                return filteredByTitle;
            }
        }
        return filteredByTitle;
    }

    public Note extractNotesByTitle(ArrayList <Note> toExtract,String title){
        Note filteredByTitle = null;
        for (Note note : toExtract){
            if(title.equals(note.getTitle())){
                filteredByTitle = note;
                return filteredByTitle;
            }
        }
        return filteredByTitle;
    }

    //for getting all the today Events
    public ArrayList <Event> getCurrentDayEventList(){
        ArrayList <Event> currentDayEventList = new ArrayList<>();
        for (Event event : events){
            Date dayOfEvent = event.getDate();
            //compare dates if they are the same
            if(isSameDay(dayOfEvent,currentDate)) {
                currentDayEventList.add(event);
            }
        }
        return currentDayEventList;
    }

    //for getting all the today Events
    public ArrayList <Note> getCurrentDayNoteList(){
        ArrayList <Note> currentDayNoteList = new ArrayList<>();
        for (Note note : notes){
            Date dayOfEvent = note.getDate();
            //compare dates if they are the same
            if(isSameDay(dayOfEvent,currentDate)) {
                currentDayNoteList.add(note);
            }
        }
        return currentDayNoteList;
    }

    public Date addWeeks(Date date,int amount) {
        // adding weeks onto the Dates
        Date result = Date.from(date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .plusWeeks(amount)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        return result;
    }

    //checking whether two dates are the same day
    public boolean isSameDay(Date d1, Date d2) {
        return d1.getYear() == d2.getYear() &&
                d1.getMonth() == d2.getMonth() &&
                d1.getDate() == d2.getDate(); // .getDate() actually returns the day of the month
    }

    public static Date dateCreator(int day , int month, int year) {
        Date date = new Date(day, month-1,year-1900);
        return date;
    }

}


