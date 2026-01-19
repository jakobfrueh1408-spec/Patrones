package Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Event implements CloneableEvent {
    private String title;
    private String description;
    private LocalDate date;
    private Label label;
    private int event_Id;

    public Event(String title, String description, LocalDate date, Label label) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.label = label;
    }
    public Event(Event event) {
        this.title = event.title;
        this.description = event.description;
        this.date = event.date;
        this.label = event.label;
    }
    public Event clone() {
        return new Event(this);
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public LocalDate getDate() {
        return date;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Event [title=" + title + ", description=" + description + ", date=" + date + ", label=" + label
                + ", getTitle()=" + getTitle() + ", getDescription()=" + getDescription() + ", getDate()=" + getDate()
                + ", getLabel()=" + getLabel() + "]";
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setLabel(Label label) {
        this.label = label;
    }
    public int getEvent_Id() {
        return event_Id;
    }
    public void setEvent_Id(int event_Id) {
        this.event_Id = event_Id;
    }
}
