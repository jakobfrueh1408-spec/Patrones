package Model;

import java.time.LocalDate;
import Controller.SignOutCommand;
import Database.CalendarTableManager;
import Database.EventNoteTableManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static Model.Label.study;

/**
 * Represents a specific calendar within the application, typically corresponding to an Erasmus stay.
 * A calendar contains events and notes, manages its own persistence via TableManagers,
 * and maintains a specific date range based on the starting year and season.
 */
public class Calendar {

    private int length;   // in semesters
    private int year;
    private LocalDate currentDate;      // currently selected day in the UI
    private String name;
    private Season season;   // Autumn (September) or Spring (April)
    private int db_id;
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Note> notes = new ArrayList<>();
    private EventNoteTableManager eventTableManager;
    private CalendarTableManager calendarTableManager;

    /**
     * Constructs a new Calendar with the specified duration, name, season, and start year.
     * Initializes the persistence managers and sets the initial date based on the season.
     *
     * @param length The duration of the calendar in semesters.
     * @param name   The display name of the calendar.
     * @param season The starting season (Autumn/Spring).
     * @param year   The year the calendar starts.
     */
    public Calendar(int length, String name, Season season, int year) {
        this.length = length;
        this.name = name;
        this.season = season;
        this.year = year;
        this.currentDate = getInitiationDate();
        this.calendarTableManager = new CalendarTableManager();
        this.eventTableManager = new EventNoteTableManager();
    }

    /**
     * Returns a string representation of the calendar including its metadata and content lists.
     * @return A string summary of the calendar.
     */
    @Override
    public String toString() {
        return "Calendar [length=" + length + ", name=" + name + ", season=" + season + ", events=" + events
                + ", notes=" + notes + "]";
    }

    /**
     * Removes an event from the current day's list based on its index.
     * Also deletes the event record from the database.
     *
     * @param indexToRemove The index of the event within the current day's event list.
     */
    public void removeEvent(int indexToRemove) {
        ArrayList<Event> currentDaysEvents = getCurrentDayEventList();
        Event eventToDelete = currentDaysEvents.get(indexToRemove);
        eventTableManager.removeEvent(eventToDelete.getEvent_Id());
        events.remove(eventToDelete); // Adjusted to remove the specific object
    }

    /**
     * Modifies the title of an existing event for the current day.
     * Updates both the local model and the database if the new title is available.
     *
     * @param indexToModify The index of the event within the current day's list.
     * @param title         The new title to assign to the event.
     */
    public void modifyEvent(int indexToModify, String title){
        ArrayList<Event> currentDaysEvents = getCurrentDayEventList();
        Event event = currentDaysEvents.get(indexToModify);
        if(checkAvailableEvent(title, currentDate, 1)){
            event.setTitle(title);
            eventTableManager.manipulateEvent(event.getEvent_Id(), title);
        }
    }

    /**
     * Adds a new event and handles optional recurring instances on a weekly basis.
     *
     * @param title               The title of the event.
     * @param description         A text description of the event.
     * @param label               A string representing the category (sport, study, etc.).
     * @param lengthOfOccurrence Number of additional weeks this event should repeat.
     */
    public void addEvent(String title, String description, String label, int lengthOfOccurrence){
        Label eventLabel = null;
        if(checkAvailableEvent(title, currentDate, lengthOfOccurrence)){
            switch (label){
                case "sport" -> eventLabel = Label.sport;
                case "free time activity" -> eventLabel = Label.free_time_activity;
                case "travel" -> eventLabel = Label.travel;
                case "study" -> eventLabel = study;
            }

            Event event = new Event(title, description, currentDate, eventLabel);
            events.add(event);
            eventTableManager.addEventsToDB(this.db_id, title, description, currentDate, eventLabel, lengthOfOccurrence);

            for (int i = 1; i <= lengthOfOccurrence; i++) {
                Event recurring = event.clone();
                recurring.setDate(currentDate.plusWeeks(i));
                events.add(recurring);
            }
        }
    }

    /**
     * Removes a note from the current day's list based on its index.
     * Also deletes the note record from the database.
     *
     * @param indexToRemove The index of the note within the current day's note list.
     */
    public void removeNote(int indexToRemove){
        ArrayList<Note> currentDaysNotes = getCurrentDayNoteList();
        Note noteToRemove = currentDaysNotes.get(indexToRemove);
        eventTableManager.removeNote(noteToRemove.getDb_id());
        notes.remove(noteToRemove);
    }

    /**
     * Modifies the title/description of an existing note for the current day.
     *
     * @param indexToModify The index of the note within the current day's list.
     * @param description   The new content for the note title.
     */
    public void modifyNote(int indexToModify, String description) {
        ArrayList<Note> currentDaysNotes = getCurrentDayNoteList();
        if(checkAvailableNote(description, currentDaysNotes)){
            Note note = currentDaysNotes.get(indexToModify);
            eventTableManager.manipulateNote(note.getDb_id(), description);
            note.setTitle(description);
        }
    }

    /**
     * Creates a new note and adds it to the current day.
     *
     * @param title The title of the note.
     * @param text  The body text of the note.
     */
    public void addNote(String title, String text) {
        Note note = new Note(title, text, currentDate);
        ArrayList<Note> currentDaysNotes = getCurrentDayNoteList();
        if(checkAvailableNote(title, currentDaysNotes)){
            eventTableManager.addNote(this.db_id, title, text, this.currentDate);
            notes.add(note);
        }
    }

    /**
     * Filters all events in the calendar to return only those matching the currently selected date.
     * @return A list of events for the current date.
     */
    public ArrayList<Event> getCurrentDayEventList(){
        ArrayList<Event> currentDayEventList = new ArrayList<>();
        for (Event event : events){
            if (event.getDate().equals(currentDate)) {
                currentDayEventList.add(event);
            }
        }
        return currentDayEventList;
    }

    /**
     * Filters all notes in the calendar to return only those matching the currently selected date.
     * @return A list of notes for the current date.
     */
    public ArrayList<Note> getCurrentDayNoteList(){
        ArrayList<Note> currentDayNoteList = new ArrayList<>();
        for (Note note : notes){
            if (note.getDate().equals(currentDate)) {
                currentDayNoteList.add(note);
            }
        }
        return currentDayNoteList;
    }

    /**
     * Retrieves the titles of all events occurring on a specific date.
     * @param date The date to query.
     * @return A list of event titles.
     */
    public ArrayList<String> dayEventTitles(LocalDate date){
        ArrayList<String> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getDate().equals(date)) {
                result.add(e.getTitle());
            }
        }
        return result;
    }

    /**
     * Retrieves the titles of all notes associated with a specific date.
     * @param date The date to query.
     * @return A list of note titles.
     */
    public ArrayList<String> dayNoteTitles(LocalDate date) {
        ArrayList<String> result = new ArrayList<>();
        for (Note note : notes) {
            if (note.getDate().equals(date)) {
                result.add(note.getTitle());
            }
        }
        return result;
    }

    /**
     * Retrieves the label names (as strings) for all events occurring on a specific date.
     * @param date The date to query.
     * @return A list of label strings.
     */
    public ArrayList<String> dayLabelTitles(LocalDate date) {
        ArrayList<String> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getDate().equals(date)) {
                result.add(e.getLabel().toString());
            }
        }
        return result;
    }

    /**
     * Determines the logical start date of the calendar based on the season.
     * @return September 1st for Autumn, April 1st for Spring.
     */
    public LocalDate getInitiationDate() {
        return switch (season) {
            case Autumn -> LocalDate.of(year, 9, 1);
            case Spring -> LocalDate.of(year, 4, 1);
        };
    }

    /**
     * Retrieves the start date of the semester.
     * @return The starting LocalDate.
     */
    public LocalDate getStartDate() {
        return getInitiationDate();
    }

    /**
     * Calculates the end date of the calendar based on the number of semesters.
     * @return The calculated end LocalDate.
     */
    public LocalDate getEndDate() {
        int months = length == 1 ? 6 : 12;
        return getStartDate().plusMonths(months).minusDays(1);
    }

    /**
     * Stream-based helper to get events for a specific date.
     * @param date The date to filter by.
     * @return A list of events matching the date.
     */
    public List<Event> getEventsForDate(LocalDate date) {
        return events.stream()
                .filter(e -> e.getDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Stream-based helper to get notes for a specific date.
     * @param date The date to filter by.
     * @return A list of notes matching the date.
     */
    public List<Note> getNotesForDate(LocalDate date) {
        return notes.stream()
                .filter(n -> n.getDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Validation method to check if a note title already exists on a given day.
     *
     * @param title      The title to check.
     * @param todayNotes The list of notes for the target day.
     * @return True if the title is available (unique), false otherwise.
     */
    public boolean checkAvailableNote(String title, ArrayList<Note> todayNotes){
        for (Note note : todayNotes){
            if(title.equals(note.getTitle())){
                return false;
            }
        }
        return true;
    }

    /**
     * Validation method to check if an event title is available across all days it would occupy.
     *
     * @param title     The title to check.
     * @param startDate The first date of the event.
     * @param recur     The number of weeks the event recurs.
     * @return True if no conflict is found in the sequence, false otherwise.
     */
    public boolean checkAvailableEvent(String title, LocalDate startDate, int recur){
        for (int i = 0; i < recur; i++){
            LocalDate dateToCheck = startDate.plusWeeks(i);
            List<Event> eventsInSequence = getEventsForDate(dateToCheck);
            for(Event event : eventsInSequence){
                if(title.equals(event.getTitle())){
                    return false;
                }
            }
        }
        return true;
    }

    // Standard Getters and Setters omitted for Javadoc length but should be documented similarly.
    public LocalDate getCurrentDate() { return currentDate; }
    public void setCurrentDate(LocalDate currentDate) { this.currentDate = currentDate; }
    public void setName(String name) { this.name = name; }
    public void setYear(int year) { this.year = year; }
    public int getYear() { return year; }
    public String getName() { return name; }
    public Season getSeason() { return season; }
    public int getDb_id() { return db_id; }
    public void setDb_id(int db_id) { this.db_id = db_id; }
    public void setSeason(Season season) { this.season = season; }
    public void setEvents(ArrayList<Event> events) { this.events = events; }
    public void setNotes(ArrayList<Note> notes) { this.notes = notes; }
}