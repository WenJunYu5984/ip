import java.util.Scanner;

public class Lukas {
    public static void main(String[] args) {
        String line = "    _________________________________________";

        //String[] list = new String[100];
        //int listCount = 0;

        System.out.println(line);
        System.out.println("    Hello I'm Lukas\n");
        System.out.println("    What can I do for you?");
        System.out.println(line);

        Scanner message = new Scanner(System.in);

        while (true){
            String input = message.nextLine();
            System.out.println(line);

            if(input.equals("bye")){
                System.out.println("    Bye! See you later, alligator!");
                System.out.println(line);
                break;
            }/* else if(input.equals("list")){
                for (int i = 0; i < listCount; i ++){
                    System.out.println("    " + (i+1) + ". " + list[i]);
                }
            } else{
                list[listCount] = input;
                listCount++;
                System.out.println("    added:" + input);
            }

            System.out.println(line);*/

            System.out.println("    " + input);
            System.out.println("    Hey! That's not a valid command! Try again.");
            System.out.println(line);
        }

        message.close();
    }
}
