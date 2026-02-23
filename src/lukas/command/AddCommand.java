package lukas.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lukas.LukasException;
import lukas.storage.Storage;
import lukas.task.*;
import lukas.ui.Ui;

/**
 * Handles the creation and addition of new tasks (Todo, Deadline, Event)
 * to the task list.
 */
public class AddCommand extends Command {
    public static final String TODO = "todo";
    public static final String DEADLINE = "deadline";
    public static final String EVENT = "event";
    private final String taskType;
    private final String arguments;

    public AddCommand(String taskType, String arguments) {
        this.taskType = taskType;
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LukasException {
        Task newTask;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        switch (taskType) {
        case TODO:
            if (arguments.isEmpty()) {
                throw new LukasException(" The description of a todo cannot be empty. Please add a task after todo");
            }
            newTask = new ToDo(arguments);
            break;

        case DEADLINE:
            if (!arguments.contains(" /by ")) {
                throw new LukasException(" Using deadline command must be followed with a /by time. Use: deadline <task> /by <time>");
            }
            String[] deadlineParts = arguments.split(" /by ", 2);
            String deadlineDesc = deadlineParts[0].trim();
            if (deadlineDesc.isEmpty()) {
                throw new LukasException(" There must be a task when using deadline command. Try entering some task when using deadline");
            }
            if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
                throw new LukasException(" You missed the deadline time!");
            }
            try {
                LocalDateTime deadlineTime = LocalDateTime.parse(deadlineParts[1].trim(), inputFormatter);
                newTask = new Deadline(deadlineParts[0].trim(), deadlineTime);
            } catch (java.time.format.DateTimeParseException e) {
                throw new LukasException(" Invalid date format! Use: yyyy-mm-dd HHmm (e.g., 2019-12-02 1800)");
            }
            break;

        case EVENT:
            if (!arguments.contains(" /from ") || !arguments.contains(" /to ")) {
                throw new LukasException(" Format error! Use: event <task> /from <start> /to <end>");
            }
            String[] eventParts = arguments.split(" /from ", 2);
            String eventDesc = eventParts[0].trim();
            if (eventDesc.isEmpty()) {
                throw new LukasException(" There must be a task when using event command. Try entering some task after typing event");
            }
            String[] timeParts = eventParts[1].split(" /to ", 2);
            if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                throw new LukasException(" You missed the start or end time of the event!");
            }
            try {
                LocalDateTime fromTime = LocalDateTime.parse(timeParts[0].trim(), inputFormatter);
                LocalDateTime toTime = LocalDateTime.parse(timeParts[1].trim(), inputFormatter);

                if (toTime.isBefore(fromTime)) {
                    throw new LukasException(" The end time cannot be before the start time!");
                }

                newTask = new Event(eventDesc, fromTime, toTime);
            } catch (java.time.format.DateTimeParseException e) {
                throw new LukasException(" Invalid date format! Use: yyyy-mm-dd HHmm (e.g., 2026-02-22 1800)");
            }
            break;

        default:
            throw new LukasException(" Unknown task type!");
        }

        tasks.addTask(newTask);
        ui.showAdded(newTask, tasks.getSize());
        storage.saveTasks(tasks.getAllTasks());
    }
}