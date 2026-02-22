package lukas.command;

import lukas.LukasException;
import lukas.storage.Storage;
import lukas.task.TaskList;
import lukas.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LukasException {
        if (tasks.getSize() == 0) {
            throw new LukasException(" Your list is empty! Add something first.");
        }
        ui.showList(tasks.getAllTasks());
    }
}