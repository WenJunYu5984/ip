package lukas.storage;

import lukas.task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all file-related operations for the chatbot.
 * Manages the loading of task data from the hard disk and saving current
 * tasks back to a persistent text file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    //Loads tasks from the hard disk
    //Handles missing files/folders and corrupted lines
    public ArrayList<Task> load() {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return loadedTasks;
        }

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                try {
                    loadedTasks.add(parseTaskLine(line));
                } catch (Exception e) {
                    System.out.println(getSpaces() + "[System] Skipping corrupted line: " + line);
                }
            }
        } catch (IOException error) {
            System.out.println(getSpaces() + "Error loading file: " + error.getMessage());
        }
        return loadedTasks;
    }

    //Saves the current list of tasks to the hard disk.
    //Automatically creates the necessary folders if they are missing.
    public void saveTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            FileWriter fw = new FileWriter(file);
            for (Task task : tasks) {
                fw.write(task.toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException error) {
            System.out.println(getSpaces() + "Error saving tasks: " + error.getMessage());
        }
    }

    private Task parseTaskLine(String line) throws Exception {
        String[] p = line.split(" \\| ");
        String type = p[0];
        boolean isDone = p[1].equals("1");
        String desc = p[2];

        Task t;
        switch (type) {
        case "T":
            t = new ToDo(desc);
            break;
        case "D":
            t = new Deadline(desc, LocalDateTime.parse(p[3]));
            break;
        case "E":
            t = new Event(desc, LocalDateTime.parse(p[3]), LocalDateTime.parse(p[4]));
            break;
        default:
            throw new Exception("Invalid Task Type");
        }

        if (isDone) {
            t.markAsDone();
        }
        return t;
    }

    private static String getSpaces() {
        return "    ";
    }
}
