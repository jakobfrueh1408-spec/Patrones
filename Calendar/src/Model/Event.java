package Model;

import java.util.Date;

public class Event implements CloneableEvent {
    private String title;
    private String description;
    private Date date;
    private Label label;
    public Event(String title, String description, Date date, Label label) {
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
    public CloneableEvent clone() {
        return new Event(this);
    }
}
