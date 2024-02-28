import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ListManager {

    static ArrayList<Todo> todolist = new ArrayList<>();
    //static boolean[] isDoneList = new boolean[MAX_LIST_LENGTH];

    static Scanner in = new Scanner(System.in);

    private static final String LIST_FORM=
            "please enter in one of the following form:\n"+
                    "todo 'description' \n" +
                    "deadline 'description' /by 'end time'\n"+
                    "event 'description' /from 'start time' /to 'end time'\n";
    public static String AddToList(String description) throws InvalidTimeForm {


        String[] part = (description.trim()).split(" ");
        String suffix = description.replace(part[0],"");

        if (part[0].startsWith("todo")){
            return (AddTodo(suffix));
        }
        else if(part[0].startsWith("deadline")){
            return(AddDeadline(suffix));
        }
        else if(part[0].startsWith("event") ){
            return(AddEvent(suffix));
        }
        else {
            return "";
        }

    }

    private static String AddTodo(String description) {
        Todo todo= new Todo(false , description);
        todolist.add(todo);
        return "Todo added\n" ;
    }
    private static String AddDeadline(String description) throws InvalidTimeForm {
        String[] part = (description.trim()).split("/by");

        if (part.length > 2){
            throw new InvalidTimeForm();// two or more "/by"
        }

        Deadline deadline = new Deadline(false, part[0].trim(),part[1].trim());
        todolist.add(deadline)  ;
        return "deadline added\n" ;

    }

    private static String AddEvent(String description) throws InvalidTimeForm {
        String[] part = (description.trim()).split("/from");

        if (part.length > 2){
            throw new InvalidTimeForm();
        }

        String EventDescription = part[0].trim();

        part = (part[1].trim()).split("/to"); // reuse of part

        if (part.length > 2){
            throw new InvalidTimeForm();
        }

        String  end = part[1].trim();
        String start = part[0].trim();

        Event event = new Event(false, EventDescription,end,start);
        todolist.add(event)  ;
        return "event added\n" ;

    }





    static void HandleList()  {

        String command ="";
        try {
            ReadFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PrintList();

        while(!command.equals("quit")){
            command = in.nextLine();
            switch (command.trim()){
            case"add":
                System.out.println(LIST_FORM);
                String description = in.nextLine();
                try {
                    System.out.println(AddToList(description));
                } catch (InvalidTimeForm e) {
                    System.out.println("wrong time form, please add again");
                }
                PrintList();
                break;
            case"quit":
                System.out.println("Task change complete\n");
                break;
            case"help":
                System.out.print("supported commands: add , quit , help , mark, unmark ,clear, list, delete \n");
                break;
            case"clear":
                ClearList();
                System.out.print("list cleared\n");
                break;
            case"mark":
                SetDone();
                break;
            case"unmark":
                SetUndone();
                break;
            case"list":
                PrintList();
                break;
            case"":
                break;
            case"delete":
                Deletelist();
                break ;
            case"save":
                try {
                    SaveList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Sorry, I can not understand this command, enter \"help\" for help\n");
                break;
            }

        }

    }

    private static void ReadFile() throws FileNotFoundException {
        File file = new File("list.txt");
        if (!file.exists()) {
            return;
        }
        else {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            try {
                String line;
                while ((line = reader.readLine()) != null) {// process every line
                    ProcessSavedLine(line);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private static void SaveList() throws IOException {
        File file = new File("list.txt");

        if (file.exists()) {
            System.out.println("file exists, overwriting file");
        }
        else {
            file.createNewFile() ;
        }

        FileWriter fw = new FileWriter("list.txt");
        for (Todo t :todolist ){
            if(t == null){
                continue;
            }
            String content =(todolist.indexOf(t)+1) + t.toString() +"\n";
            fw.write(content.replace("[","|").replace("]","|") );
        }
        fw.close();



    }

    private static void Deletelist() {

        if(todolist.isEmpty()){
            System.out.println("no available tasks\n");
            return;
        }
        int number = in.nextInt();

        while(number> todolist.size()){
            System.out.println("index out of bound, please retype\n");
            number = in.nextInt();
        }
        todolist.remove(number-1);

        System.out.println("task deleted\n");
        PrintList();
    }


    public static void PrintList(){
        int i=1;
        System.out.print("---------------------------------------------\n");
        System.out.println("Here are your current tasks in your list:");
        for (Todo t :todolist ){
            if(t == null){
                continue;
            }
            System.out.printf("%d. " + t.toString() +"\n",i);
            i++;
        }
        System.out.print("---------------------------------------------\n");
    }

    static void ClearList(){
        todolist.clear();
    }




    private static void SetDone() {
        if(todolist.isEmpty()){
            System.out.println("No available task! \n");
            return;
        }

        System.out.println("please type the task number you want to set as done \n");
        PrintList();
        int number= in.nextInt();

        while(number> todolist.size()){
            System.out.println("task do not exist, pleas enter a valid task number ");
            number= in.nextInt();
        }

        Todo task= todolist.get(number-1);

        task.SetisDone(true);

        System.out.print("I've marked this task as  done:");
        System.out.println(task.toString());

    }




    static void SetUndone(){
        if(todolist.isEmpty()){
            System.out.println("No available task! \n");
            return;
        }

        System.out.println("please type the task number you want to set as not done \n");
        PrintList();
        int number= in.nextInt();



        while(number>todolist.size()){
            System.out.println("task do not exist, pleas enter a valid task number ");
            number= in.nextInt();
        }

        Todo task= todolist.get(number-1);

        task.SetisDone(false);

        System.out.print("I've marked this task as not done:");
        System.out.println(task.toString());


    }




    private static void ProcessSavedLine(String line) {

        String Done = "|"+"([x ])"+"|";

        Boolean isDone ;
        if(line.contains("| |")){
            isDone = false;
        }else if(line.contains("|x|")){

            isDone = true;
        }else{
            System.out.println("unexpected format");
            return;
        }
        String description;
        String starttime;
        String endtime;

        if(line.contains("|T|")){
            String parts = line.substring(7);
            description = parts;
            Todo task = new Todo(isDone,description.trim());
            todolist.add(task);
        }else if(line.contains("|D|")){

            //String[] parts = line.split(Done,2);
            String parts = line.substring(7);
            String[] contents = parts.split("" +
                    "by:",2);
            description = contents[0].replace("(","");
            endtime = contents[1].replace(")","");
            Deadline task= new Deadline(isDone,description.trim(),endtime.trim());
            todolist.add(task);

        }else if(line.contains("|E|")){
            String parts = line.substring(7);
            String[] contents = parts.split( "from:",2);
            description = contents[0].replace("(","");
            String[] time= contents[1].split("by:",2 );
            starttime = time[0].replace("(","").replace(")","");
            endtime = time[1].replace("(","").replace(")","");
            Event task = new Event(isDone,description.trim(),endtime.trim(),starttime.trim());
            todolist.add(task);
        }else{
            System.out.println("unexpected format");
            return;
        }

    }
}


