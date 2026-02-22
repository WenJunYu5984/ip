package lukas.task;

/**
 * Adds a task to the list.
 * No specific timing required.
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
    @Override
    public String toFileFormat() {
        int statusAsNumber = 0;
        if(isDone){
            statusAsNumber = 1;
        }
        return "T | " + statusAsNumber + " | " + description;
    }
}