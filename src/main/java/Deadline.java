/**
 * The {@code Deadline} class represents a deadline item.
 * comparing with todo , it has a end time
 *
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Todo{
    LocalDate endtime;
    String Icon="D";
    /**
     * Constructs a new deadline item.
     *
     * @param isDone      Indicates whether the deadline item is done
     * @param description The description of the deadline item
     * @param endtime     A String indicating the end time of this deadline
     */
    public Deadline(boolean isDone, String description, LocalDate endtime) {
        super(isDone, description);
        this.endtime = endtime;
    }

    /**
     * similar to the one in Todo class
     * @return The string representation of the to-do item
     */
    @Override
    public String toString(){
        String DoneIcon = isDone? "x":" ";
        return "["+Icon+"]"+"["+DoneIcon+"] "+ description + "(by:"+ formatDate(endtime)+")";
    }

    public static String formatDate(LocalDate date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
        return date.format(formatter);
    }

}