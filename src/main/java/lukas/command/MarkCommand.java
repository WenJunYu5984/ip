package lukas.command;

import lukas.LukasException;
import lukas.storage.Storage;
import lukas.task.Task;
import lukas.task.TaskList;
import lukas.ui.Ui;

public class MarkCommand extends Command {
    private final String arguments;
    private final String commandWord;
    private final boolean isMark;

    public MarkCommand(String arguments, String commandWord, boolean isMark) {
        this.arguments = arguments;
        this.commandWord = commandWord;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LukasException {
        if (arguments.isEmpty()) {
            throw new LukasException(" Which task to " + commandWord + "? Try: " + commandWord + " (number)");
        }

        try {
            int idx = Integer.parseInt(arguments) - 1;

            if (idx < 0 || idx >= tasks.getSize()) {
                throw new LukasException(" That task number does not exist. You have " + tasks.getSize() + " tasks");
            }

            Task t = tasks.getTask(idx);
            if (isMark) {
                t.markAsDone();
            } else {
                t.unmarkAsDone();
            }

            ui.showMarkStatus(t, isMark);
        } catch (NumberFormatException error) {
            throw new LukasException(" Please use a number to represent the task. For example: " + commandWord + " 1");
        }
        storage.saveTasks(tasks.getAllTasks()); // Save state change to disk
    }

    private static String getSpaces() {
        return "    ";
    }
}
