import java.util.ArrayList;
import java.util.Scanner;

import lukas.LukasException;
import lukas.command.Command;
import lukas.logic.Parser;
import lukas.storage.Storage;
import lukas.task.Task;
import lukas.task.TaskList;
import lukas.ui.Ui;

public class Lukas {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Lukas(String filePath){
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        ArrayList<Task> loadedTasks = storage.load();
        if(loadedTasks == null){
            this.tasks = new TaskList();
        } else {
            this.tasks = new TaskList(loadedTasks);
        }
    }

    public static void main(String[] args) {
        new Lukas("./data/lukas.txt").run(); // Added storage field
    }

    public void run() {
        ui.showWelcome();
        Scanner message = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            try {
                String input = message.nextLine().trim();
                Command command = Parser.parse(input);
                command.execute(tasks, ui, storage);

                isExit = command.isExit();

            } catch (LukasException error) {
                System.out.println(getSpaces() + "Oops!!" + error.getMessage());
                ui.showLine();
            }
        }
        message.close();
    }
   private static String getSpaces() {
        return "    ";
    }
}