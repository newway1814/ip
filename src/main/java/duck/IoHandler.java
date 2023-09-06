package duck;
import java.util.ArrayList;
import java.util.Scanner;

import duck.task.Task;
import duck.task.TaskList;

/**
 * Responsible for handling Input Output tasks in the application.
 */
public class IoHandler {

    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructs IoHandler Object.
     */
    public IoHandler() {
    }

    /**
     * Displays welcome Message.
     */
    public String welcomeMessage() {
        String result = "Hello! I'm Duckbot\n\n";
        result += "What can I do for you?";
        return result;
    }
    /**
     * Divides the paragraphs.
     */
    public void divider() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads the input from the user
     * @return User's inputted String.
     */
    public String typeMessage() {
        return sc.nextLine();
    }
    /**
     * Displays a list of tasks to the user.
     *
     * @param taskList The TaskList containing tasks to be displayed.
     */
    public String display(TaskList taskList) {
        int count = 0;
        int serial = 1;
        StringBuilder result = new StringBuilder();
        if (taskList.size() == 0) {
            return "There are no tasks in your lists.";
        }
        result.append("Here are all the tasks in your list: \n");
        while (count < taskList.size()) {
            result.append(serial).append(".").append(taskList.get(count)).append("\n");
            count++;
            serial++;
        }
        return result.toString();
    }
    /**
     * Displays an exit message.
     */
    public String exit() {
        return "Bye. Hope to see you again soon !";
    }

    /**
     * Displays a confirmation message when some task gets added.
     * @param t
     * @param taskList
     */
    public String echoAdd(Task t, TaskList taskList) {
        StringBuilder result = new StringBuilder();
        result.append("Got it. I've added this task:\n");
        result.append("  ").append(t).append("\n");
        result.append("Now you have ").append(taskList.size()).append(" tasks in the list.\n");
        return result.toString();
    }

    public String displaySearchResults(ArrayList<Task> list) {
        StringBuilder result = new StringBuilder();
        if (list.isEmpty()) {
            return "I couldn't find any matching tasks in your lists.";
        }
        result.append("Here are the matching tasks in your list: \n");
        for (int i = 0; i < list.size(); i++) {
            result.append(i + 1).append(".").append(list.get(i)).append("\n");
        }
        return result.toString();
    }

}
