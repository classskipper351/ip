import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Locale;

/**
 * The {@code Parser} class handles the parsing of input lines and creating corresponding to-do items.
 */
public class Parser {

    public static final int BEGIN_INDEX = 7;

    /**
     * Processes a saved line from the file and creates a corresponding to-do item.
     * deals with 3 types and store them in todolist
     * first the function decide the type ,the it extracts description , start time and end time according to the
     * format
     *
     * @param line     The saved line to be processed
     * @param todolist The list of to-do items to which the created item will be added
     */
    public static void ProcessSavedLine(String line, ArrayList todolist) {
        String Done = "|" + "([x ])" + "|";

        Boolean isDone;
        if (line.contains("| |")) {
            isDone = false;
        } else if (line.contains("|x|")) {
            isDone = true;
        } else {
            System.out.println("Unexpected format , skipping");
            return;
        }
        String description;
        String starttime;
        String endtime;

        if (line.contains("|T|")) {
            String parts = line.substring(BEGIN_INDEX);
            description = parts;
            Todo task = new Todo(isDone, description.trim());
            todolist.add(task);
        } else if (line.contains("|D|")) {
            String parts = line.substring(BEGIN_INDEX);
            String[] contents = parts.split("by:", 2);
            description = contents[0].replace("(", "");
            endtime = contents[1].replace(")", "");
            LocalDate endDate = parseDateFile(endtime);

            Deadline task = new Deadline(isDone, description.trim(), endDate);
            todolist.add(task);
        } else if (line.contains("|E|")) {
            String parts = line.substring(BEGIN_INDEX);
            String[] contents = parts.split("from:", 2);
            description = contents[0].replace("(", "");
            String[] time = contents[1].split("to:", 2);
            starttime = time[0].replace("(", "").replace(")", "");
            endtime = time[1].replace("(", "").replace(")", "");
            Event task = new Event(isDone, description.trim(), parseDateFile(endtime.trim()), parseDateFile(starttime.trim()) );
            todolist.add(task);
        } else {
            System.out.println("Unexpected format, skipping");
        }
    }

    /**
     * Adds a to-do item to the list with the given description.
     *
     * @param description The description of the to-do item to be added
     * @return A message indicating that the to-do item has been added
     */
    static String AddTodo(String description) {
        Todo todo = new Todo(false, description);
        ListManager.todolist.add(todo);
        return "Todo added\n";
    }

    /**
     * Adds a deadline item to the list with the given description and end time.
     *
     * @param description The description of the deadline item to be added
     * @return A message indicating that the deadline item has been added
     * @throws InvalidTimeForm If the time format is invalid
     */
    static String AddDeadline(String description) throws InvalidTimeForm {
        if(!description.contains("/by")){
            throw new InvalidTimeForm();
        }
        String[] part = (description.trim()).split("/by");

        if (part.length > 2) {
            throw new InvalidTimeForm(); // two or more "/by"
        }
        LocalDate endtime = parseDateInput(part[1].trim());

        Deadline deadline = new Deadline(false, part[0].trim(), endtime);
        ListManager.todolist.add(deadline);
        return "Deadline added\n";
    }

    /**
     * Adds an event item to the list with the given description, start time, and end time.
     *
     * @param description The description of the event item to be added
     * @return A message indicating that the event item has been added
     * @throws InvalidTimeForm If the time format is invalid
     */
    static String AddEvent(String description) throws InvalidTimeForm {

        if(!description.contains("/from")||!description.contains("/to")){
            throw new InvalidTimeForm();
        }
        if(description.indexOf("/from")>description.indexOf("/to")){
            throw new InvalidTimeForm();
        }

        String[] part = (description.trim()).split("/from");
        if (part.length > 2) {
            throw new InvalidTimeForm();
        }
        String EventDescription = part[0].trim();
        String[] content = (part[1].trim()).split("/to");

        if (content.length > 2) {
            throw new InvalidTimeForm();
        }
        String end = content[1].trim();
        String start = content[0].trim();
        Event event = new Event(false, EventDescription,parseDateInput(end), parseDateInput(start));
        ListManager.todolist.add(event);
        return "Event added\n";
    }

    public static LocalDate parseDateFile(String timeString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy",Locale.ENGLISH);
        return LocalDate.parse(timeString, formatter);
    }
    public static LocalDate parseDateInput(String timeString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        return LocalDate.parse(timeString, formatter);
    }
}
