
package duck;

import java.util.ArrayList;

import duck.exceptions.DuckException;
import duck.exceptions.IllegalDateFormatException;
import duck.task.Deadline;
import duck.task.Events;
import duck.task.Task;
import duck.task.TaskList;
import duck.task.ToDo;

/**
 * Deals in connecting with the user command.
 */
public class Parser {
    /**
     * Identifies the type of task.
     * @param command User's inputted text.
     * @return The type of task the user wants to do.
     */
    public String identify(String command) {
        String[] strings = command.split(" ");
        assert strings.length != 0 : "command should not be empty";
        return strings[0];
    }

    /**
     * Parses and executes user command to manage tasks.
     * @param str User's inputed String (Command).
     * @param io IoHandler object for input and output
     * @param taskList List containing Tasks.
     * @param storage Acts as a storage object for saving tasks.
     * @return boolean value depicting whether application should run or should be exited.
     */

    public String parse(String str, IoHandler io, TaskList taskList, Storage storage) {
        String command = identify(str);
        String result;
        try {
            switch (command) {
            case "deadline":
                Deadline tempDeadline = taskList.setDeadline(str.substring(8));
                taskList.addTask(tempDeadline);
                result = io.echoAdd(tempDeadline, taskList);
                storage.saveInFile(taskList);
                break;
            case "event":
                Events tempEvent = taskList.setEvent(str.substring(5));
                taskList.addTask(tempEvent);
                result = io.echoAdd(tempEvent, taskList);
                storage.saveInFile(taskList);
                break;
            case "todo":
                ToDo tempToDo = taskList.setToDo(str.substring(4));
                taskList.addTask(tempToDo);
                result = io.echoAdd(tempToDo, taskList);
                storage.saveInFile(taskList);
                break;
            case "delete":
                Task tempDelete = taskList.deleteTask(str, taskList);
                storage.saveInFile(taskList);
                StringBuilder deleteResult = new StringBuilder();
                deleteResult.append("Noted. I've removed this task:\n");
                deleteResult.append(" ").append(tempDelete.toString()).append("\n");
                deleteResult.append("Now you have ").append(taskList.size()).append(" tasks in the list.");
                result = deleteResult.toString();
                break;
            case "mark":
                Task tempMark = taskList.setDone(str, taskList);
                StringBuilder markResult = new StringBuilder();
                markResult.append("Nice! I've marked this task as done:\n");
                markResult.append("  ").append(tempMark);
                result = markResult.toString();
                break;
            case "unmark":
                Task tempUnmark = taskList.setUndone(str, taskList);
                StringBuilder unmarkResult = new StringBuilder();
                unmarkResult.append("I've marked this task as undone:\n");
                unmarkResult.append("  ").append(tempUnmark);
                result = unmarkResult.toString();
                break;
            case "list":
                result = io.display(taskList);
                break;
            case "bye":
                result = io.exit();
                break;
            case "find":
                ArrayList<Task> temp = taskList.find(str.substring(5));
                result = io.displaySearchResults(temp);
                break;
            default:
                throw new DuckException(" OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DuckException | IllegalDateFormatException e) {
            result = "ERROR : " + e.getMessage();
        }
        return result;
    }

}
