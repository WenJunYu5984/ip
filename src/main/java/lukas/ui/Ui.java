package lukas.ui;

import lukas.task.Task;

public class Ui {
    private static final String LINE = "    _________________________________________";

    public static void showLine() {
        System.out.println(LINE);
    }

    public static void showWelcome() {
        showLine();
        System.out.println("    Hello I'm Lukas");
        System.out.println("    What can I do for you?");
        showLine();
    }

    public static void showGoodbye() {
        System.out.println("    Bye! See you later, alligator!");
        showLine();
    }

    public static void showAdded(Task task, int count) {
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + task);
        System.out.println("    Now you have " + count + " tasks in the list.");
    }

    public static void showList(Task[] list, int count) {
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < count; i++) {
            System.out.println("    " + (i + 1) + "." + list[i]);
        }
    }
}