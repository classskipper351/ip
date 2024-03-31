import java.util.ArrayList;

/**
 * The {@code UI} class handles user interface interactions and displays messages.
 */
public class UI {

    /**
     * A welcome greeting message displayed when the program starts.
     */
    static final String WELCOME_GREETING = new String(
            "Qchat, A truly humanized intelligent voice assistant\n" +
                    "knows better about life and better about you\n" +
                    "What can I do for you?\n" +
                    "you can enter 'help' for help\n"+
                    "----------------------------------------------------------------\n");

    /**
     * A goodbye message displayed when the program ends.
     */
    static final String GOODBYE_GREETING = new String(
            "--------------------------------------------------------\n" +
                    "goodbye\n" + "Qchat, your life-long trusted companion\n");

    /**
     * The format of the list command.
     */
    private static final String LIST_FORM =
            "please enter in one of the following forms:\n" +
                    "todo 'description'\n" +
                    "deadline 'description' /by \"yyyy-MM-dd\" \n" +
                    "event 'description' /from \"yyyy-MM-dd\" /to \"yyyy-MM-dd\" \n";

    /**
     * Displays the welcome greeting message.
     */
    public void Greeting() {
        System.out.print(WELCOME_GREETING);
    }

    /**
     * Displays the goodbye message.
     */
    public void Goodbye() {
        System.out.print(GOODBYE_GREETING);
    }

    /**
     * Displays the list command format.
     */
    public void PrintListForm() {
        System.out.println(LIST_FORM);
    }

    /**
     * Prints the list of items along with their sequence number and description of printed items
     * if used for print every to-do in the list, description would be "Here are your current tasks in your list:"
     * if used for print matching tasks ,description would be "Here are the matching tasks in your list:"
     *
     * @param todolist    The list of items to print
     * @param description The description of the list
     */
    public static void PrintList(ArrayList todolist, String description) {
        int i = 1;
        System.out.print("---------------------------------------------\n");
        System.out.println(description);
        for (Object t : todolist) {
            if (t == null) {
                continue;
            }
            System.out.printf("%d. " + t.toString() + "\n", i);
            i++;
        }
        System.out.print("---------------------------------------------\n");
    }
}
