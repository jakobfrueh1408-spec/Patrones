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
    public Event clone() {
        return new Event(this);
    }
     public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Date getDate() {
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
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setLabel(Label label) {
        this.label = label;
    }
}
