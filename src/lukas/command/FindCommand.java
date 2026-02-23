package lukas.command;

import lukas.LukasException;
import lukas.storage.Storage;
import lukas.task.Task;
import lukas.task.TaskList;
import lukas.ui.Ui;

import java.util.ArrayList;

/**
 * Performs a keyword search across the task list and displays
 * matching tasks to the user.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LukasException {
        if (keyword.isEmpty()) {
            throw new LukasException(" What are you looking for? Try: find <keyword>");
        }

        ArrayList<Task> matchingTasks = new ArrayList<>();

        // Search logic
        for (int i = 0; i < tasks.getSize(); i++) {
            Task task = tasks.getTask(i);
            // .contains() is case-sensitive; use .toLowerCase() for a broader search
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        ui.showSearchResults(matchingTasks, keyword);
    }
}