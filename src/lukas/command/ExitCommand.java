package lukas.command;

import lukas.storage.Storage;
import lukas.task.TaskList;
import lukas.ui.Ui;

/**
 * Exits the program.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}