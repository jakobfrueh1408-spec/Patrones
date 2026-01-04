package Model;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.time.ZoneId;
public class Calendar {
    @Override
    public String toString() {
        return "Calendar [length=" + length + ", name=" + name + ", season=" + season + ", events=" + events
                + ", notes=" + notes + "]";
    }

    int length;
    String name;
    Season season;
    ArrayList <Event> events = new ArrayList<>();
    ArrayList <Note> notes = new ArrayList<>(); 
    public Calendar(int length, String name, Season season) {
        this.length = length;
        this.name = name;
        this.season = season;
    }

    //creates an Event which can be added to the Event List
    public Event createEvent(String title, String description, Date date ,Label label) {
        return new Event(title,description,date,label);
    }

    //removing the specified event from the Event List
    public void removeEvent(Event event) {
        int indexToDelete = events.indexOf(event);
        events.remove(indexToDelete);
    }
    //Only modify the description of the Event
    public void modifyEvent(Event event, String description){
        event.setDescription(description);
    }
    
    //Need to handle reocurring events as well 
    //frequency edscribes the interval of reocurrence in terms of weeks (f.e: frequency = 1 , for each week an event will be cloned until it hits the boundary)
    //boundary describes until when the events will reocur (also in weeks)
    public void addEvent(Event event, int frequency , int boundary){

        //add base event
        events.add(event);
        //retrieve the Date fo our baseEvent but not by reference but by value
        Date baseDate =  event.getDate();

        for (int i = 0; i< boundary; i++ ){

                //clone event , update Date according to i in weeks and add it into the events List
                Event reocurr =  event.clone();
                Date reocurrDate = addWeeks(baseDate, i+1); 
                reocurr.setDate(reocurrDate);
                events.add(reocurr);
            
        }
    }
    public void addEvent(Event event) {
        events.add(event);
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
  
    public Note createNote(String title, String text, Date date){
        return new Note(title,text,date);
    }

    public void removeNote(Note note){
        int indexToDelete = notes.indexOf(note);
        notes.remove(indexToDelete);
    }
    //Only modify the description of the NOte
    public void modifyNote(Note note, String description) {
        note.setText(description);
    }

    public void addNote(Note note) {
        notes.add(note);
    }
    

/**
 * 
 *     public static void main(String[]args) {
        Calendar cal = new Calendar(6, "Alcala", Season.Spring); 
        Date testDate = Date.from(LocalDate.of(2025, 9, 14)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant() );
        Event footbalEvent = cal.createEvent("Football", "Kicking with the lads", testDate, Label.sport); 
        cal.addEvent(footbalEvent, 1, 16);
        for (Event e : cal.events) {
            System.out.println(e.getDate().toString());
        }
        
    }
 * 
 */

}


