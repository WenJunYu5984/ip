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
        showLine();
        System.out.println(getSpaces() + "Got it. I've added this task:");
        System.out.println(getSpaces() + task);
        System.out.println(getSpaces() + "Now you have " + count + " tasks in the list.");
        showLine();
    }

    public void showDeleted(Task removedTask, int remainingSize) {
        showLine();
        System.out.println(getSpaces() + "Noted. I've removed this task:");
        System.out.println(getSpaces() + removedTask);
        System.out.println(getSpaces() + "Now you have " + remainingSize + " tasks in the list.");
        showLine();
    }

    public void showMarkStatus(Task task, boolean isMark) {
        showLine();
        if (isMark) {
            System.out.println(getSpaces() + "Good Job on completing the task! Task is now marked as done:");
        } else {
            System.out.println(getSpaces() + "Oh no! Looks like you have 1 more task to do! This task is now marked as not done yet:");
        }
        System.out.println(getSpaces() + task);
        showLine();
    }

    public void showList(ArrayList<Task> tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println(getSpaces() + "Your list is currently empty!");
            return;
        }

        System.out.println(getSpaces() + "Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(getSpaces() + (i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    public void showSearchResults(ArrayList<Task> results, String keyword) {
        showLine();
        if (results.isEmpty()) {
            System.out.println(getSpaces() + "I couldn't find any matching tasks for: " + keyword);
        } else {
            System.out.println(getSpaces() + "Here are the matching tasks in your list:");
            for (int i = 0; i < results.size(); i++) {
                System.out.println(getSpaces() + (i + 1) + "." + results.get(i));
            }
        }
        showLine();
    }

    private String getSpaces() {
        return "    ";
    }
}