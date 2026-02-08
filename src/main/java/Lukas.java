import java.util.Scanner;

import lukas.task.Deadline;
import lukas.task.Event;
import lukas.task.Task;
import lukas.task.ToDo;
import lukas.ui.Ui;

public class Lukas {
    private static final int MAX_NUMBER = 100;
    private static final Task[] list = new Task[100];
    private static int listCount = 0;

    public static void main(String[] args) {
        Ui.showWelcome();
        Scanner message = new Scanner(System.in);

        while(true) {
            String input = message.nextLine().trim();
            if (input.equals("bye")) {
                Ui.showGoodbye();
                message.close();
                return;
            }

            try {
                commandLine(input);
            } catch (LukasException error) {
                System.out.println("    Oops!!" + error.getMessage());
                Ui.showLine();
            }
        }
    }

    private static void commandLine(String input) throws LukasException {
        Ui.showLine();
        if (input.equals("list")) {
            Ui.showList(list, listCount);
        } else if (!input.startsWith("mark") && !input.startsWith("unmark")) {
            if (input.startsWith("todo")) {
                handleTodo(input);
            } else if (input.startsWith("deadline")) {
                handleDeadline(input);
            } else {
                if (!input.startsWith("event")) {
                    throw new LukasException(" I'm sorry, but I don't know what you mean :( Try todo, deadline, or event instead.");
                }

                handleEvent(input);
            }
        } else {
            handleMark(input);
        }

        Ui.showLine();
    }

    private static void handleEvent(String input) throws LukasException {
        if (input.trim().equalsIgnoreCase("event")) {
            throw new LukasException(" There must be a task when using event command.");
        } else if (input.contains(" /from ") && input.contains(" /to ")) {
            String content = input.substring(6).trim();
            String[] parts = input.substring(6).split(" /from ", 2);
            String description = parts[0].trim();
            if (description.isEmpty()) {
                throw new LukasException(" There must be a task when using event command. Try entering some task after typing event");
            } else {
                String[] timeParts = parts[1].split(" /to ", 2);
                if (timeParts.length >= 2 && !timeParts[0].trim().isEmpty() && !timeParts[1].trim().isEmpty()) {
                    addTask(new Event(description, timeParts[0].trim(), timeParts[1].trim()));
                } else {
                    throw new LukasException(" You missed the start or end time of the event!");
                }
            }
        } else {
            throw new LukasException(" Format error! Use: event <task> /from <start> and /to <finish>");
        }
    }

    private static void handleDeadline(String input) throws LukasException {
        if (!input.contains(" /by ")) {
            throw new LukasException(" Using deadline command must be followed with a /by time. Use: deadline  <task> /by <day>");
        } else {
            String[] parts = input.substring(9).split(" /by ", 2);
            if (parts[0].trim().isEmpty()) {
                throw new LukasException(" There must be a task when using deadline command. Try entering some task when using deadline");
            } else {
                addTask(new Deadline(parts[0].trim(), parts[1].trim()));
            }
        }
    }

    private static void handleTodo(String input) throws LukasException {
        String description = input.replaceFirst("todo", "").trim();
        if (description.isEmpty()) {
            throw new LukasException(" todo command cannot be empty. Please add a task after todo");
        } else {
            addTask(new ToDo(description));
        }
    }

    private static void addTask(Task task) {
        list[listCount++] = task;
        Ui.showAdded(task, listCount);
    }

    private static void handleMark(String input) throws LukasException {
        try {
            boolean isMark = input.startsWith("mark");
            String[] parts = input.split(" ");
            if (parts.length < 2) {
                throw new LukasException(" Which task to mark? Try: mark (number)");
            } else {
                int idx = Integer.parseInt(parts[1]) - 1;
                if (idx >= 0 && idx < listCount) {
                    if (isMark) {
                        list[idx].markAsDone();
                        System.out.println("    Good Job on completing the task! Task is now marked as done:");
                    } else {
                        list[idx].unmarkAsDone();
                        System.out.println("    Oh no! Looks like you have 1 more task to do! This task is now marked as not done yet:");
                    }

                    Task var10001 = list[idx];
                    System.out.println("    " + String.valueOf(var10001));
                } else {
                    throw new LukasException(" That task number does not exist. You have " + listCount + " tasks");
                }
            }
        } catch (NumberFormatException var4) {
            throw new LukasException(" Please use a number to represent the task. For example: mark 1");
        }
    }
}