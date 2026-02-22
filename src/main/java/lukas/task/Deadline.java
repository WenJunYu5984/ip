package lukas.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[D]" + super.toString() + " (by: " + by.format(displayFormatter) + ")";
    }
    @Override
    public String toFileFormat() {
        int statusAsNumber = 0;
        if(isDone){
            statusAsNumber = 1;
        }
        return String.format("D | %d | %s | %s", statusAsNumber, description, by.toString());
    }
}