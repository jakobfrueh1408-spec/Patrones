package Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents an occurrence or task within a specific calendar.
 * An Event contains details such as title, description, date, and a category label.
 * It supports cloning to facilitate the creation of recurring events.
 */
public class Event {
    private String title;
    private String description;
    private LocalDate date;
    private Label label;
    private int event_Id;

    /**
     * Constructs a new Event with specified details.
     * * @param title       The summary or name of the event.
     * @param description A detailed explanation of the event.
     * @param date        The {@link LocalDate} on which the event occurs.
     * @param label       The category {@link Label} (e.g., sport, study).
     */
    public Event(String title, String description, LocalDate date, Label label) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.label = label;
    }

    /**
     * Copy constructor for creating a new Event based on an existing one.
     * * @param event The Event instance to copy.
     */
    public Event(Event event) {
        this.title = event.title;
        this.description = event.description;
        this.date = event.date;
        this.label = event.label;
    }

    /**
     * Creates and returns a deep copy of this Event.
     * Useful for generating recurring events where the date changes but other details remain the same.
     * * @return A new Event instance identical to this one.
     */
    public Event clone() {
        return new Event(this);
    }

    /**
     * Retrieves the title of the event.
     * @return The title string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the description of the event.
     * @return The description string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the date of the event.
     * @return The {@link LocalDate} instance.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Retrieves the category label of the event.
     * @return The {@link Label} enum value.
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Returns a string representation of the Event object.
     * @return A formatted string containing event attributes.
     */
    @Override
    public String toString() {
        return "Event [title=" + title + ", description=" + description + ", date=" + date + ", label=" + label
                + ", getTitle()=" + getTitle() + ", getDescription()=" + getDescription() + ", getDate()=" + getDate()
                + ", getLabel()=" + getLabel() + "]";
    }

    /**
     * Sets a new title for the event.
     * @param title The title string to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets a new date for the event.
     * @param date The {@link LocalDate} to set.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets a new category label for the event.
     * @param label The {@link Label} to set.
     */
    public void setLabel(Label label) {
        this.label = label;
    }

    /**
     * Retrieves the database ID of the event.
     * @return The integer ID.
     */
    public int getEvent_Id() {
        return event_Id;
    }

    /**
     * Sets the database ID for the event.
     * @param event_Id The integer ID to set.
     */
    public void setEvent_Id(int event_Id) {
        this.event_Id = event_Id;
    }
}