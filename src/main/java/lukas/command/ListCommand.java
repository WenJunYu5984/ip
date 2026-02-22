package lukas.command;

import lukas.LukasException;
import lukas.storage.Storage;
import lukas.task.TaskList;
import lukas.ui.Ui;

/**
 * Prints all saved task into a list.
 * Tasks are printed in order in which they are added.
 */
public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LukasException {
        if (tasks.isEmpty()){
            throw new LukasException(" Your list is empty! Add something first.");
        } else {
        ui.showList(tasks.getAllTasks());
        }
    }
}