package lukas.command;

import lukas.LukasException;
import lukas.storage.Storage;
import lukas.task.*;
import lukas.ui.Ui;

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
            newTask = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
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
            newTask = new Event(eventDesc, timeParts[0].trim(), timeParts[1].trim());
            break;

        default:
            throw new LukasException(" Unknown task type!");
        }

        tasks.addTask(newTask);
        ui.showAdded(newTask, tasks.getSize());
        storage.saveTasks(tasks.getAllTasks());
    }
}