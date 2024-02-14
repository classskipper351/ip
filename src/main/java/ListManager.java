import java.util.Scanner;

public class Todolist {

    String Icon = "T";
    private static final int MAX_LIST_LENGTH = 100;
    static String[] todolist = new String[MAX_LIST_LENGTH];
    static boolean[] isDoneList = new boolean[MAX_LIST_LENGTH];

    static int ListCount= 0;
    static Scanner in = new Scanner(System.in);

    public static void AddToList(){
        String Task= in.nextLine();
        todolist[ListCount] = Task;
        isDoneList[ListCount]= false;
        ListCount++;
        return;
    }



    static void HandleList(){
        PrintList();
        String command ="";

        while(!command.equals("quit")){
            command = in.nextLine();
            switch (command){
            case"add":
                AddToList();
                System.out.println("Task added");
                PrintList();
                break;
            case"quit":
                System.out.println("Task change complete");
                break;
            case"help":
                System.out.print("supported commands: add , quit , help , mark,unmark ,clear, list\n");
                break;
            case "clear":
                ClearList();
                ClearisDoneList();
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
            default:
                System.out.println("Sorry, I can not understand this command, enter \"help\" for help\n");
                break;
            }

        }

    }

    public static void PrintList(){
        int i=1;
        System.out.print("---------------------------------------------\n");
        System.out.println("Here are your current tasks in your list:");
        for (String s :todolist ){
            if(s == null){
                continue;
            }
            System.out.printf("%d."+ GetIcon(i)+s+"\n",i);
            i++;
        }
        System.out.print("---------------------------------------------\n");
    }
    static void ClearList(){
        int i;
        for (i=0;i<MAX_LIST_LENGTH;i++){
           todolist[i]=null;
        }
        ListCount = 0;
    }

    static void ClearisDoneList(){
        int i;
        for (i=0;i<MAX_LIST_LENGTH;i++){
            isDoneList[i]=false;
        }
    }

    static String GetIcon(int i){
        String s= isDoneList[i-1]?"x":" ";
        return "["+s+"]";
    }

    static void SetDone(){
        System.out.println("please type the task number you want to set as done \n");
        PrintList();
        int number= in.nextInt();

        while(number>ListCount){
            System.out.println("task do not exist, pleas enter a valid task number ");
            number= in.nextInt();
        }

        isDoneList[number-1]=true;
        System.out.print("I've marked this task as done:");
        System.out.printf(GetIcon(number)+todolist[number-1]+"\n");
    }

    static void SetUndone(){
        System.out.println("please type the task number you want to set as not done \n");
        PrintList();
        int number= in.nextInt();

        while(number>ListCount){
            System.out.println("task do not exist, pleas enter a valid task number ");
            number= in.nextInt();
        }

        isDoneList[number-1]=false;
        System.out.print("I've marked this task as not done:");
        System.out.printf(GetIcon(number)+todolist[number-1]+"\n");

    }


}


