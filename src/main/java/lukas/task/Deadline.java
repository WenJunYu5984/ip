package lukas.task;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
    @Override
    public String toFileFormat() {
        int statusAsNumber = 0;
        if(isDone){
            statusAsNumber = 1;
        }
        return "D | " + statusAsNumber + " | " + description + " | " + by;
    }
}