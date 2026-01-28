import java.util.Scanner;

public class Lukas {
    public static void main(String[] args) {
        String line = "    _________________________________________";

        Task[] list = new Task[100];
        int listCount = 0;

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
            } else if(input.equals("list")){
                System.out.println("    This is your list of tasks:");
                for (int i = 0; i < listCount; i ++){
                    System.out.println("    " + (i+1) + ". " + list[i].getTaskDetails());
                }
            }else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5)) - 1;
                list[taskNumber].markAsDone();
                System.out.println("    Good Job on completing this task! This task is now marked as done:");
                System.out.println("    " + list[taskNumber].getTaskDetails());
            } else if (input.startsWith("unmark ")){
                int taskNumber = Integer.parseInt(input.substring(7)) - 1;
                list[taskNumber].unmarkAsDone();
                System.out.println("    Oh no! Looks like you have 1 more task to do! This task is now marked as not done yet:");
                System.out.println("    " + list[taskNumber].getTaskDetails());
            } else{
                list[listCount] = new Task(input);
                listCount++;
                System.out.println("    added:" + input);
            }

            System.out.println(line);
        }

        message.close();
    }
}
