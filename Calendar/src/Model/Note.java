package Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Note {
    private String title;
    private String text;
    private LocalDate date;
    public Note(String title, String text, LocalDate  date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public LocalDate  getDate() {
        return date;
    }
    public void setDate(LocalDate  date) {
        this.date = date;
    }
    public int getCurrentDay(){
        return date.getDayOfMonth();
    }

}
