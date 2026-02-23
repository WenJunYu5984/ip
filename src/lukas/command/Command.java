package lukas.command;

import lukas.LukasException;
import lukas.storage.Storage;
import lukas.task.TaskList;
import lukas.ui.Ui;

/**
 * Represents an executable command within the application.
 * Defines the contract for all specific command implementations.
 */
public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws LukasException;

    public boolean isExit() {
        return false;
    }
}
