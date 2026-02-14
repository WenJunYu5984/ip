import java.util.Scanner;
import java.util.ArrayList;

import lukas.storage.Storage;
import lukas.task.Deadline;
import lukas.task.Event;
import lukas.task.Task;
import lukas.task.ToDo;
import lukas.ui.Ui;

public class Lukas {
    private static final Storage storage = new Storage("./data/lukas.txt"); // Added storage field
    private static final ArrayList<Task> taskList = storage.load();

    public static void main(String[] args) {
        Ui.showWelcome();
        Scanner message = new Scanner(System.in);

        while (true) {
            String input = message.nextLine().trim();
            if (input.equals("bye")) {
                Ui.showGoodbye();
                message.close();
                return;
            }

            try {
                commandLine(input);
            } catch (LukasException error) {
                System.out.println(getSpaces() + "Oops!!" + error.getMessage());
                Ui.showLine();
            }
        }
    }

    private static void commandLine(String input) throws LukasException {
        Ui.showLine();
        String[] inputParts = input.trim().split(" ", 2);
        String command = inputParts[0].toLowerCase();
        String arguments = getArguments(inputParts);
        if (command.equals("list")) {
            Ui.showList(taskList);
        } else if (command.equals("mark") || command.equals("unmark")) {
            handleMark(command, arguments);
        } else if (command.equals("delete")) {
            handleDelete(arguments);
        } else if (input.startsWith("todo")) {
            handleTodo(arguments);
        } else if (input.startsWith("deadline")) {
            handleDeadline(arguments);
        } else if (input.startsWith("event")) {
            handleEvent(arguments);
        } else {
            throw new LukasException(" I'm sorry, but I don't know what you mean :( Try todo, deadline, or event instead.");
        }
        Ui.showLine();
    }
    private static void handleDelete(String input) throws LukasException {
        if (input.isEmpty()) {
            throw new LukasException(" Which task to delete? Try: delete (number)");
        }

        try {
            int idx = Integer.parseInt(input) - 1;

            if (idx < 0 || idx >= taskList.size()) {
                throw new LukasException(" That task number does not exist. You have " + taskList.size() + " tasks");
            }

            Task removedTask = taskList.remove(idx);
            //Sync to hard drive
            storage.saveTasks(taskList);
            System.out.println(getSpaces() + "Noted. I've removed this task:");
            System.out.println(getSpaces() + "  " + removedTask);
            System.out.println(getSpaces() + "Now you have " + taskList.size() + " tasks in the list.");

        } catch (NumberFormatException error) {
            throw new LukasException(" Please use a number to delete. For example: delete 1");
        }
    }

    private static void handleEvent(String input) throws LukasException {
        if (input.trim().equalsIgnoreCase("event")) {
            throw new LukasException(" There must be a task when using event command.");
        }
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new LukasException(" Format error! Use: event <task> /from <start> and /to <finish>");
        }
        String[] parts = input.split(" /from ", 2);
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new LukasException(" There must be a task when using event command. Try entering some task after typing event");
        }
        String[] timeParts = parts[1].split(" /to ", 2);
        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new LukasException(" You missed the start or end time of the event!");
        }
        addTask(new Event(description, timeParts[0].trim(), timeParts[1].trim()));
    }

    private static void handleDeadline(String input) throws LukasException {
        if (!input.contains(" /by ")) {
            throw new LukasException(" Using deadline command must be followed with a /by time. Use: deadline <task> /by <day>");
        }

        String[] parts = input.split(" /by ", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new LukasException(" There must be a task when using deadline command. Try entering some task when using deadline");
        }
        addTask(new Deadline(description, by));
    }

    private static void handleTodo(String input) throws LukasException {
        if (input.isEmpty()) {
            throw new LukasException(" todo command cannot be empty. Please add a task after todo");
        }
        addTask(new ToDo(input));
    }

    private static void addTask(Task task) {
        taskList.add(task);
        Ui.showAdded(task, taskList.size());
        storage.saveTasks(taskList); // Save after adding
    }

    private static void handleMark(String command, String input) throws LukasException {
        if (input.isEmpty()) {
            throw new LukasException(" Which task to " + command + "? Try: " + command + " (number)");
        }

        try {
            int idx = Integer.parseInt(input) - 1;

            //check bounds early
            if (idx < 0 || idx >= taskList.size()) {
                throw new LukasException(" That task number does not exist. You have " + taskList.size() + " tasks");
            }

            Task t = taskList.get(idx);
            if (command.equals("mark")) {
                t.markAsDone();
                System.out.println(getSpaces() + "Good Job on completing the task! Task is now marked as done:");
            } else {
                t.unmarkAsDone();
                System.out.println(getSpaces() + "Oh no! Looks like you have 1 more task to do! This task is now marked as not done yet:");
            }

            System.out.println(getSpaces() + t);
        } catch (NumberFormatException error) {
            throw new LukasException(" Please use a number to represent the task. For example: " + command + " 1");
        }
        storage.saveTasks(taskList); // Save after state change
    }

    private static String getSpaces() {
        return "    ";
    }

    private static String getArguments(String[] inputParts) {
        if (inputParts.length < 2) {
            return "";
        }
        return inputParts[1].trim();
    }
}