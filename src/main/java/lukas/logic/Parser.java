package lukas.logic;

import lukas.LukasException;
import lukas.command.*;

/**
 * Handles the interpretation of user input strings.
 * Responsible for breaking down raw input into a command word and its arguments,
 * then returning the appropriate Command object.
 */
public class Parser {

    public static final String DELETE = "delete";
    public static final String TODO = "todo";
    public static final String DEADLINE = "deadline";
    public static final String EVENT = "event";
    public static final String MARK = "mark";
    public static final String UNMARK = "unmark";
    public static final String LIST = "list";
    public static final String BYE = "bye";
    public static final String FIND = "find";

    public static Command parse(String input) throws LukasException {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
        case DELETE:
            return new DeleteCommand(args);
        case TODO:
            return new AddCommand("todo", args);
        case DEADLINE:
            return new AddCommand("deadline", args);
        case EVENT:
            return new AddCommand("event", args);
        case MARK:
            return new MarkCommand(args,"mark", true);
        case UNMARK:
            return new MarkCommand(args,"unmark",false);
        case LIST:
            return new ListCommand();
        case BYE:
            return new ExitCommand();
        case FIND:
            return new FindCommand(args);
        default:
            throw new LukasException(" I'm sorry, but I don't know what you mean :( Try list, mark/unmark, todo, deadline, event or delete instead.");
        }
    }
}