import java.util.ArrayList;
import java.util.Scanner;

import lukas.LukasException;
import lukas.command.Command;
import lukas.logic.Parser;
import lukas.storage.Storage;
//import lukas.task.Deadline;
//import lukas.task.Event;
//import lukas.task.Task;
import lukas.task.Task;
import lukas.task.TaskList;
//import lukas.task.ToDo;
import lukas.ui.Ui;

public class Lukas {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Lukas(String filePath){
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        ArrayList<Task> loadedTasks = storage.load();
        if(loadedTasks == null){
            this.tasks = new TaskList();
        } else {
            this.tasks = new TaskList(loadedTasks);
        }
    }

    public static void main(String[] args) {
        new Lukas("./data/lukas.txt").run(); // Added storage field
    }

    public void run() {
        ui.showWelcome();
        Scanner message = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
//            if (input.equals("bye")) {
//                ui.showGoodbye();
//                message.close();
//                return;
//            }

            try {
                String input = message.nextLine().trim();
                Command command = Parser.parse(input);
                command.execute(tasks, ui, storage);

                isExit = command.isExit();

//                commandLine(input);
            } catch (LukasException error) {
                System.out.println(getSpaces() + "Oops!!" + error.getMessage());
                ui.showLine();
            }
        }
        message.close();
    }

//    private void commandLine(String input) throws LukasException {
//        ui.showLine();
//        String[] inputParts = input.trim().split(" ", 2);
//        String command = inputParts[0].toLowerCase();
//        String arguments = getArguments(inputParts);
//        if (command.equals("list")) {
//            ui.showList(tasks.getAllTasks());
//        } else if (command.equals("mark") || command.equals("unmark")) {
//            handleMark(command, arguments);
//        } else if (command.equals("delete")) {
//            handleDelete(arguments);
//        } else if (input.startsWith("todo")) {
//            handleTodo(arguments);
//        } else if (input.startsWith("deadline")) {
//            handleDeadline(arguments);
//        } else if (input.startsWith("event")) {
//            handleEvent(arguments);
//        } else {
//            throw new LukasException(" I'm sorry, but I don't know what you mean :( Try todo, deadline, or event instead.");
//        }
//        ui.showLine();
//    }
//    private void handleDelete(String input) throws LukasException {
//        if (input.isEmpty()) {
//            throw new LukasException(" Which task to delete? Try: delete (number)");
//        }
//
//        try {
//            int idx = Integer.parseInt(input) - 1;
//
//            if (idx < 0 || idx >= tasks.getSize()) {
//                throw new LukasException(" That task number does not exist. You have " + tasks.getSize() + " tasks");
//            }
//
//            Task removedTask = tasks.deleteTask(idx);
//            //Sync to hard drive
//            storage.saveTasks(tasks.getAllTasks());
//            System.out.println(getSpaces() + "Noted. I've removed this task:");
//            System.out.println(getSpaces() + "  " + removedTask);
//            System.out.println(getSpaces() + "Now you have " + tasks.getSize() + " tasks in the list.");
//
//        } catch (NumberFormatException error) {
//            throw new LukasException(" Please use a number to delete. For example: delete 1");
//        }
//    }

//    private void handleEvent(String input) throws LukasException {
//        if (input.trim().equalsIgnoreCase("event")) {
//            throw new LukasException(" There must be a task when using event command.");
//        }
//        if (!input.contains(" /from ") || !input.contains(" /to ")) {
//            throw new LukasException(" Format error! Use: event <task> /from <start> and /to <finish>");
//        }
//        String[] parts = input.split(" /from ", 2);
//        String description = parts[0].trim();
//        if (description.isEmpty()) {
//            throw new LukasException(" There must be a task when using event command. Try entering some task after typing event");
//        }
//        String[] timeParts = parts[1].split(" /to ", 2);
//        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
//            throw new LukasException(" You missed the start or end time of the event!");
//        }
//        addTask(new Event(description, timeParts[0].trim(), timeParts[1].trim()));
//    }

//    private void handleDeadline(String input) throws LukasException {
//        if (!input.contains(" /by ")) {
//            throw new LukasException(" Using deadline command must be followed with a /by time. Use: deadline <task> /by <day>");
//        }
//
//        String[] parts = input.split(" /by ", 2);
//        String description = parts[0].trim();
//        String by = parts[1].trim();
//
//        if (description.isEmpty()) {
//            throw new LukasException(" There must be a task when using deadline command. Try entering some task when using deadline");
//        }
//        addTask(new Deadline(description, by));
//    }
//
//    private void handleTodo(String input) throws LukasException {
//        if (input.isEmpty()) {
//            throw new LukasException(" todo command cannot be empty. Please add a task after todo");
//        }
//        addTask(new ToDo(input));
//    }
//
//    private void addTask(Task task) {
//        tasks.addTask(task);
//        ui.showAdded(task, tasks.getSize());
//        storage.saveTasks(tasks.getAllTasks()); // Save after adding
//    }

//    private void handleMark(String command, String input) throws LukasException {
//        if (input.isEmpty()) {
//            throw new LukasException(" Which task to " + command + "? Try: " + command + " (number)");
//        }
//
//        try {
//            int idx = Integer.parseInt(input) - 1;
//
//            //check bounds early
//            if (idx < 0 || idx >= tasks.getSize()) {
//                throw new LukasException(" That task number does not exist. You have " + tasks.getSize() + " tasks");
//            }
//
//            Task t = tasks.getTask(idx);
//            if (command.equals("mark")) {
//                t.markAsDone();
//                System.out.println(getSpaces() + "Good Job on completing the task! Task is now marked as done:");
//            } else {
//                t.unmarkAsDone();
//                System.out.println(getSpaces() + "Oh no! Looks like you have 1 more task to do! This task is now marked as not done yet:");
//            }
//
//            System.out.println(getSpaces() + t);
//        } catch (NumberFormatException error) {
//            throw new LukasException(" Please use a number to represent the task. For example: " + command + " 1");
//        }
//        storage.saveTasks(tasks.getAllTasks()); // Save after state change
//    }

    private static String getSpaces() {
        return "    ";
    }

//    private static String getArguments(String[] inputParts) {
//        if (inputParts.length < 2) {
//            return "";
//        }
//        return inputParts[1].trim();
//    }
}