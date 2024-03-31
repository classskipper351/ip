import javax.swing.*;
import java.util.Scanner;

public class Qchat {
    static UI ui = new UI();
   static String CommandReader(){

       Scanner in = new Scanner(System.in);
       String command ;
       command = in.nextLine();

       switch (command){
       case "Bye":
           ui.Goodbye();
           break;
       case "list":
           ListManager Listmanager = null;
           ListManager.HandleList();
           break;


       default:
           echo(command);
           break;

       }
       return command;



   }

    private static void echo(String command) {
        System.out.print("---------------------------------------------\n");
        System.out.print(command +"\n");
        System.out.print("---------------------------------------------\n");
    }

    public static void main(String[] args) {

        ui.Greeting();
        ListManager Listmanager = null;
        ListManager.HandleList();
        ui.Goodbye();

   }

}
