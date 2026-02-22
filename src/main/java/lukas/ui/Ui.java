package lukas.ui;

import lukas.task.Task;

import java.util.ArrayList;

public class Ui {
    private final String LINE = getSpaces() + "_________________________________________";

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        showLine();
        System.out.println(getSpaces() + "Hello I'm Lukas");
        System.out.println(getSpaces()+ "What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        System.out.println(getSpaces() + "Bye! See you later, alligator!");
        showLine();
    }

    public void showAdded(Task task, int count) {
        System.out.println(getSpaces() + "Got it. I've added this task:");
        System.out.println(getSpaces() + task);
        System.out.println(getSpaces() + "Now you have " + count + " tasks in the list.");
    }

    public void showList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(getSpaces() + "Your list is currently empty!");
            return;
        }

        System.out.println(getSpaces() + "Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(getSpaces() + (i + 1) + "." + tasks.get(i));
        }
    }

    private String getSpaces() {
        return "    ";
    }
}