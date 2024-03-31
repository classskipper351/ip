import java.io.*;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
public class ListManager {
    static UI ui = new UI();
    static Parser parser = new Parser();
    static Storage storage = new Storage();
    static ArrayList<Todo> todolist = new ArrayList<>();
    //static boolean[] isDoneList = new boolean[MAX_LIST_LENGTH];
    static final String ALL_TASKS = "Here are your current tasks in your list:" ;
    static final String MATCHED_TASKS = "Here are the matching tasks in your list:" ;
    static Scanner in = new Scanner(System.in);


    public static String AddToList(String description) throws InvalidTimeForm {

        String[] part = (description.trim()).split(" ");
        String suffix = description.replace(part[0], "");
        try{
            if (part[0].startsWith("todo")) {
                return (Parser.AddTodo(suffix));
            } else if (part[0].startsWith("deadline")) {
                return (Parser.AddDeadline(suffix));
            } else if (part[0].startsWith("event")) {
                return (Parser.AddEvent(suffix));
            } else {
                System.out.println("unexpected task from , please add again");
                return "";
            }
        }catch (DateTimeException e){
            System.out.println("unexpected date form , please type the date as yyyy-mm-dd format");
            return "";
        }

    }


    static void HandleList() {

        String command = "";
        try {
            todolist = storage.ReadFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        UI.PrintList(todolist,ALL_TASKS);
        ;

        while (!command.equals("quit")) {
            command = in.nextLine();
            switch (command.trim()) {
            case "add":
                ui.PrintListForm();
                String description = in.nextLine();
                try {
                    System.out.println(AddToList(description));
                } catch (InvalidTimeForm e) {
                    System.out.println("wrong time form, please add again");
                }
                UI.PrintList(todolist,ALL_TASKS);
                ;
                break;
            case "quit":
                System.out.println("Task change complete\n");
                break;
            case "help":
                System.out.print("supported commands: add , quit , help , mark, unmark ,clear, list, delete, find ,save \n");
                break;
            case "clear":
                ClearList();
                System.out.print("list cleared\n");
                break;
            case "mark":
                SetDone();
                break;
            case "unmark":
                SetUndone();
                break;
            case "list":
                UI.PrintList(todolist,ALL_TASKS);
                break;
            case "":
                break;
            case "find":
                SearchList();
                break;
            case "delete":
                Deletelist();
                break;
            case "save":
                try {
                    storage.SaveList(todolist);
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

    private static void SearchList() {
        System.out.print("please type the key words you want to find\n");
        String description = in.nextLine();
        ArrayList<Todo> temp_todolist = new ArrayList<>();
        for(Todo t:todolist){
            if(t.description.contains(description)){
                temp_todolist.add(t);
            }

        }
        if(temp_todolist.isEmpty()){
            System.out.print("no matched task found!\n");
        }
        else{
            UI.PrintList(temp_todolist,MATCHED_TASKS);
            temp_todolist.clear();
        }

    }


    private static void Deletelist() {

        if (todolist.isEmpty()) {
            System.out.println("no available tasks\n");
            return;
        }
        int number = in.nextInt();

        while (number > todolist.size()) {
            System.out.println("index out of bound, please retype\n");
            number = in.nextInt();
        }
        todolist.remove(number - 1);

        System.out.println("task deleted\n");
        UI.PrintList(todolist,ALL_TASKS);
        ;
    }


    static void ClearList() {
        todolist.clear();
    }


    private static void SetDone() {
        if (todolist.isEmpty()) {
            System.out.println("No available task! \n");
            return;
        }

        System.out.println("please type the task number you want to set as done \n");
        UI.PrintList(todolist,ALL_TASKS);
        int number = in.nextInt();

        while (number > todolist.size()) {
            System.out.println("task do not exist, pleas enter a valid task number ");
            number = in.nextInt();
        }

        Todo task = todolist.get(number - 1);

        task.SetisDone(true);

        System.out.print("I've marked this task as  done:");
        System.out.println(task.toString());

    }


    static void SetUndone() {
        if (todolist.isEmpty()) {
            System.out.println("No available task! \n");
            return;
        }

        System.out.println("please type the task number you want to set as not done \n");
        UI.PrintList(todolist,ALL_TASKS);

        int number = in.nextInt();


        while (number > todolist.size()) {
            System.out.println("task do not exist, pleas enter a valid task number ");
            number = in.nextInt();
        }

        Todo task = todolist.get(number - 1);

        task.SetisDone(false);

        System.out.print("I've marked this task as not done:");
        System.out.println(task.toString());


    }
}







