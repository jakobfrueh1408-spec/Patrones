package Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents a personal note associated with a specific date in the calendar.
 * Unlike {@link Event}, a Note is intended for general information or reminders
 * that do not require a category label or duration.
 */
public class Note {
    private String title;
    private String text;
    private LocalDate date;
    private int db_id;

    /**
     * Constructs a new Note with a title, content, and an associated date.
     * * @param title The summary or heading of the note.
     * @param text  The detailed content of the note.
     * @param date  The {@link LocalDate} this note is assigned to.
     */
    public Note(String title, String text, LocalDate date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }

    /**
     * Retrieves the title of the note.
     * @return The note title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title of the note.
     * @param title The new title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the body text of the note.
     * @return The note content.
     */
    public String getText() {
        return text;
    }

    /**
     * Updates the body text of the note.
     * @param text The new text content to set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Retrieves the date associated with this note.
     * @return The {@link LocalDate} of the note.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Updates the date associated with this note.
     * @param date The new {@link LocalDate} to set.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Retrieves the unique database identifier for this note.
     * @return The database ID.
     */
    public int getDb_id() {
        return db_id;
    }

    /**
     * Sets the unique database identifier for this note.
     * @param db_id The database ID as stored in the persistence layer.
     */
    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }
}