import javax.swing.*;
import java.util.Scanner;

public class Qchat {

   static final String  WELCOME_GREETING =
           "Qchat ,A truly humanized intelligent voice assistant \n"
                   +"knows better about life and better about you\n"
                   +"What can I do for you?\n"
                   +"----------------------------------------------------------------\n" ;
   static final String GOODBYE_GREETING =
           "--------------------------------------------------------\n" +
            "goodbye\n"+ "Qchat, your life-long trusted companion\n";


   static String CommandReader(){
       Scanner in = new Scanner(System.in);
       String command ;
       command = in.nextLine();
           switch (command) {
           case "Bye":
               System.out.print(GOODBYE_GREETING);
               break;
           case "list":
               ListManager.HandleList();
               break;
           default:
               echo(command);
               break;
           }
       //in.close();
       return command;

   }

    private static void echo(String command) {
        System.out.print("---------------------------------------------\n");
        System.out.print(command +"\n");
        System.out.print("---------------------------------------------\n");
    }

    public static void main(String[] args) {
       System.out.print(WELCOME_GREETING);
       String command = "" ;
       while(!command.equals("Bye")){
           command = CommandReader();
       }

   }

}
