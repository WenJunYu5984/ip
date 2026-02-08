import java.util.Scanner;

public class Lukas {
    private static final int MAX_NUMBER = 100;
    private static final Task[] list = new Task[MAX_NUMBER];
    private static int listCount = 0;

    public static void main(String[] args) {
        Ui.showWelcome();
        Scanner message = new Scanner(System.in);

        while (true) {
            String input = message.nextLine().trim();
            Ui.showLine();

            if (input.equals("bye")) {
                Ui.showGoodbye();
                break;
            }
            try {
                commandLine(input);
            } catch (LukasException error) {
                Ui.showLine();
                System.out.println("    Oops!!" + error.getMessage());
                Ui.showLine();
            }
        }
        message.close();
    }

    private static void commandLine(String input) throws LukasException {
        Ui.showLine();
        if (input.equals("list")) {
            Ui.showList(list, listCount);
        } else if (input.startsWith("mark ")) {
            handleMark(input, true);
        } else if (input.startsWith("unmark ")) {
            handleMark(input, false);
        } else if (input.startsWith("todo ")) {
            addTask(new ToDo(input.substring(5)));
        } else if (input.startsWith("deadline ")) {
            String[] parts = input.substring(9).split(" /by ");
            addTask(new Deadline(parts[0], parts[1]));
        } else if (input.startsWith("event ")) {
            String[] parts = input.substring(6).split(" /from ");
            String[] timeParts = parts[1].split(" /to ");
            addTask(new Event(parts[0], timeParts[0], timeParts[1]));
        } else {
            //throw error message if receive unknown command
            throw new LukasException("I'm sorry, but I don't know what you mean :( Try todo, deadline, or event instead.");
        }
        Ui.showLine();
    }

    private static void addTask(Task task) {
        list[listCount++] = task;
        Ui.showAdded(task, listCount);
    }

    //handling marking of tasks
    private static void handleMark(String input, boolean isMark) {
        int idx = Integer.parseInt(input.split(" ")[1]) - 1;
        if (isMark) {
            list[idx].markAsDone();
            System.out.println("    Good Job on completing the task! Task is now marked as done:");
        } else {
            list[idx].unmarkAsDone();
            System.out.println("    Oh no! Looks like you have 1 more task to do! This task is now marked as not done yet:");
        }
        System.out.println("    " + list[idx]);
    }
}
