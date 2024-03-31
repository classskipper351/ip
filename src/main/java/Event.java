/**
 * The {@code Event} class represents a event item.
 * it has a start time and an end time
 *
 */
import java.time.LocalDate;
public class Event extends Deadline{
    LocalDate starttime;
    String Icon="E";
    /**
     * Constructs a new event item.
     *
     * @param isDone      Indicates whether the deadline item is done
     * @param description The description of the deadline item
     * @param endtime     A String indicating the end time of this event
     * @param starttime   A String indicating the start time of this event
     */
    public Event(boolean isDone, String description, LocalDate endtime, LocalDate starttime) {

        super(isDone, description, endtime);
        this.starttime = starttime;
    }
    /**
     * similar to the one in Todo class
     * @return The string representation of the event item
     */
    @Override
    public String toString(){
        String DoneIcon = this.isDone? "x":" ";
        return "["+Icon+"]"+"["+DoneIcon+"]"+ " "+ this.description + "(from: "+formatDate(starttime)+ ") (to: "+formatDate(this.endtime)+")" ;
    }
}