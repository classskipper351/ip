import java.io.*;
import java.time.DateTimeException;
import java.util.ArrayList;

/**
 * The {@code Storage} class handles the reading and writing of to-do lists from and to a file.
 */
public class Storage {

    /**
     * The list of to-do items.
     */
    static ArrayList<Todo> todolist = new ArrayList<>();

    /**
     * Reads the to-do list from the file "list.txt".
     * will create a file if it does not exist
     * it read evey line and use parser to handle it
     * @return The list of to-do items read from the file
     * @throws FileNotFoundException If the file "list.txt" does not exist
     * @throws IndexOutOfBoundsException If the file "list.txt" is broken or not readable
     */
    static ArrayList<Todo> ReadFile() throws FileNotFoundException {
        File file = new File("list.txt");
        if (!file.exists()) {
            return todolist;
        } else {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            System.out.println("loading saved list from saved file");
            try {
                String line;
                while ((line = reader.readLine()) != null) { // Process every line

                    try{
                        Parser.ProcessSavedLine(line, todolist);
                        }
                    catch (IndexOutOfBoundsException e){
                        System.out.println(e.getMessage());
                        System.out.println("unexpected format , skipping");
                    }catch (DateTimeException e){
                        System.out.println("unexpected format , skipping");
                    }

                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return todolist;
        }
    }

    /**
     * Saves the list of to-do items to the file "list.txt".
     * will overwrite existing file
     * @param todolist The list of to-do items to be saved
     * @throws IOException If an I/O error occurs while writing to the file
     */
    static void SaveList(ArrayList todolist) throws IOException {
        File file = new File("list.txt");

        if (file.exists()) {
            System.out.println("File exists, overwriting file");
        } else {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter("list.txt");
        for (Object t : todolist) {
            if (t == null) {
                continue;
            }
            String content = (todolist.indexOf(t) + 1) + t.toString() + "\n";
            fw.write(content.replace("[", "|").replace("]", "|"));
        }
        fw.close();
    }
}
