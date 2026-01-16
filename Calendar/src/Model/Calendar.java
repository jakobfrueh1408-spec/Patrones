package Model;

import java.time.LocalDate;
import Database.CalendarTableManager;
import Database.EventNoteTableManager;

import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static Model.Label.study;

public class Calendar {

    private int length;   //in semesters
    private int year;
    private LocalDate currentDate;
    private String name;
    private Season season;   //either september or april
    private int db_id;
    private ArrayList <Event> events = new ArrayList<>();
    private ArrayList <Note> notes = new ArrayList<>();
    EventNoteTableManager eventTableManager;
    CalendarTableManager calendarTableManager;
    public Calendar(int length, String name, Season season, int year) {
        this.length = length;
        this.name = name;
        this.season = season;
        this.year = year;
        this.currentDate = getInitiationDate();
        this.calendarTableManager = new CalendarTableManager();
        this.eventTableManager = new EventNoteTableManager();
    }

    //to String method
    @Override
    public String toString() {
        return "Calendar [length=" + length + ", name=" + name + ", season=" + season + ", events=" + events
                + ", notes=" + notes + "]";
    }

    // getters and setters
    public LocalDate getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate(LocalDate currentDate) {this.currentDate = currentDate;}
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
    public int getDb_id() {return db_id;}
    public void setDb_id(int db_id) {this.db_id = db_id;}
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
    public void removeEvent(int indexToRemove) {
        //getting the current Days Events
        ArrayList <Event> currentDaysEvents = getCurrentDayEventList();
        //getting the event to delete
        Event eventToDelete = currentDaysEvents.get(indexToRemove);
        //removing the corresponsing Event from Model and DB
        eventTableManager.removeEvent(eventToDelete.getEvent_Id());
        currentDaysEvents.remove(indexToRemove);
    }

    //Only modify the description of the Event
    public void modifyEvent(int indexToModify, String description){
        //getting the current Days Events
        ArrayList <Event> currentDaysEvents = getCurrentDayEventList();
        //getting the corresponding event
        Event event = currentDaysEvents.get(indexToModify);
        //setting the new Description
        event.setDescription(description);
        //setting the change into the DB
        eventTableManager.manipulateEvent(event.getEvent_Id(),description);
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
        for (int i = 1; i <= lengthOfOccurrence; i++) {
            Event recurring = event.clone();
            recurring.setDate(currentDate.plusWeeks(i));
            events.add(recurring);
            eventTableManager.addEventWithClones(this.db_id,recurring,lengthOfOccurrence);

        }


    }
    //functions for Note insertion , manipulation adn deletion
    //removing the specified event from the Event List
    public void removeNote(int indexToRemove){
        //getting all the notes of the day
        ArrayList <Note> currentDaysNotes = getCurrentDayNoteList();
        //removing the note from notes
        Note noteToRemove = currentDaysNotes.get(indexToRemove);
        //removing Note from Database
        eventTableManager.removeNote(noteToRemove.getDb_id());
        //remove Note from the Model
        notes.remove(indexToRemove);


    }
    //Only modify the description of the Note
    public void modifyNote(int indexToModify, String description) {
        //getting all the notes of the day
        ArrayList <Note> currentDaysNotes = getCurrentDayNoteList();
        //getting the note with the corresponding title
        Note note = currentDaysNotes.get(indexToModify);
        //setting the notes description int the DB
        eventTableManager.manipulateNote(note.getDb_id(),description);
        note.setText(description);
    }
    public void addNote(String title,String text) {
        Note note = new Note(title,text,currentDate);
        //adding note to the model and to the database
        eventTableManager.addNote(this.db_id,title,text,  this.currentDate);
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
    public ArrayList<Event> getCurrentDayEventList(){
        ArrayList <Event> currentDayEventList = new ArrayList<>();
        for (Event event : events){
            if (event.getDate().equals(currentDate)) {
                currentDayEventList.add(event);
            }
        }
        return currentDayEventList;
    }
    //for getting all the today Events
    public ArrayList<Note> getCurrentDayNoteList(){
        ArrayList <Note> currentDayNoteList = new ArrayList<>();
        for (Note note : notes){
            if (note.getDate().equals(currentDate)) {
                currentDayNoteList.add(note);
            }
        }
        return currentDayNoteList;
    }
    public ArrayList<String> dayEventTitles(LocalDate date){
        ArrayList<String> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getDate().equals(date)) {
                result.add(e.getTitle());
            }
        }
        return result;
    }

    public ArrayList<String> dayNoteTitles(LocalDate date) {
        ArrayList<String> result = new ArrayList<>();
        for (Note note : notes) {
            if (note.getDate().equals(date)) {
                result.add(note.getTitle());
            }
        }
        return result;
    }
    public ArrayList<String> dayLabelTitles(LocalDate date) {
        ArrayList<String> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getDate().equals(date)) {
                result.add(e.getLabel().toString());
            }
        }
        return result;
    }
    public LocalDate getInitiationDate() {
        return switch (season) {
            case Autumn -> LocalDate.of(year, 9, 1);
            case Spring -> LocalDate.of(year, 4, 1);
        };
    }
    public LocalDate getStartDate() {
        return switch (season) {
            case Autumn -> LocalDate.of(year, 9, 1);
            case Spring -> LocalDate.of(year, 4, 1);
        };
    }

    public LocalDate getEndDate() {
        int months = length == 1 ? 6 : 12;
        return getStartDate().plusMonths(months).minusDays(1);
    }
    public List<Event> getEventsForDate(LocalDate date) {
        // Return only events that match the given date
        return events.stream()
                .filter(e -> e.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Note> getNotesForDate(LocalDate date) {
        // Return only notes that match the given date
        return notes.stream()
                .filter(n -> n.getDate().equals(date))
                .collect(Collectors.toList());
    }

}


