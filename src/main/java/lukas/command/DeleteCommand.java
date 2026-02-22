package lukas.command;

import lukas.LukasException;
import lukas.storage.Storage;
import lukas.task.TaskList;
import lukas.ui.Ui;
import lukas.task.Task;

public class DeleteCommand extends Command {
    private final String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LukasException {
        if (arguments.isEmpty()) {
            throw new LukasException(" Which task to delete? Try: delete (number)");
        }

        try {
            int idx = Integer.parseInt(arguments) - 1;

            if (idx < 0 || idx >= tasks.getSize()) {
                throw new LukasException(" That task number does not exist. You have " + tasks.getSize() + " tasks");
            }

            Task removedTask = tasks.deleteTask(idx);
            //sync to hard drive
            storage.saveTasks(tasks.getAllTasks());

            ui.showDeleted(removedTask, tasks.getSize());
        } catch (NumberFormatException error) {
            throw new LukasException(" Please use a number to delete.");
        }
    }
}

