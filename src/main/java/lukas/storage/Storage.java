package lukas.storage;

import lukas.task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    //Loads tasks from the hard disk
    //Handles missing files/folders and corrupted lines
    public int loadTasks(Task[] list){
        // ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return 0; // Return 0 tasks if file doesn't exist
        }

        int count = 0;
        try (Scanner s = new Scanner(file)){
            while (s.hasNext() && count < 100) {
                String line = s.nextLine();
                try {
                    list[count++] = parseTaskLine(line);
                } catch (Exception e) {
                    System.out.println("    [Warning] Skipping corrupted line: " + line);
                }
            }
        } catch (IOException error) {
            System.out.println("     Error loading file: " + error.getMessage());
        }
        return count;
    }


    //Saves the current list of tasks to the hard disk.
    public void saveTasks(Task[] list, int count) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create folders if they don't exist

            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < count; i++) {
                fw.write(list[i].toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException error) {
            System.out.println("     Error saving tasks: " + error.getMessage());
        }
    }

    private Task parseTaskLine(String line) throws Exception {
        String[] p = line.split(" \\| ");
        String type = p[0];
        boolean isDone = p[1].equals("1");
        String desc = p[2];

        Task t;
        switch (type) {
        case "T": t = new ToDo(desc); break;
        case "D": t = new Deadline(desc, p[3]); break;
        case "E": t = new Event(desc, p[3], p[4]); break;
        default: throw new Exception("Unknown type");
        }

        if (isDone) t.markAsDone();
        return t;
    }
}
