import java.util.ArrayList;

public class Day {
    
    private String day;
    private ArrayList<Event> events;

    public Day (String day) {
        this.day = day;
        events = new ArrayList<Event>();
    }

    public Day (ArrayList<Event> events, String day) {
        this.events = events;
        this.day = day;
    }

    public void addEvent (double start, double end, String title) {
        events.add(new Event(start, end, title));
    }

    public ArrayList<Event> getEvents () {
        return events;
    }

    public void setEvents (ArrayList<Event> events) {
        this.events = events;
    }
    
    public String getDay() {
    	return day;
    }

    public int getSize () {
        return events.size();
    }
    
    public boolean addEvent(String eventName, double startTime, double endTime) {
    	events.add(new Event(startTime, endTime, eventName));
    	return true;
    }

    public String toString () {
        String s = "";
        for (int i = 0; i < events.size(); i++) {
            s += events.get(i).getTitle() + " " + events.get(i).getStart() + " " + events.get(i).getEnd(); 
        }
        return s;
    }

    
}
