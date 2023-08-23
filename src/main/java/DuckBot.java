import java.util.ArrayList;
import java.util.Scanner;

public class DuckBot {

    private final String name = "Duck";
    private final String exitMessage = "bye";

    protected ArrayList<Task> list = new ArrayList<>();
    private Task task = new Task();

    public void welcomeMessage() {
        divider();
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        divider();
    }

    public void exit() {
        System.out.println("Bye. Hope to see you again soon !");
        divider();
    }

    public boolean isBye(String str) {
        return str.equalsIgnoreCase(exitMessage);
    }

    public boolean checkList(String str) {
        return str.equalsIgnoreCase("list");
    }

    public boolean checkMark(String str) { return str.contains("mark");}

    public boolean checkUnMark(String str) { return str.contains("unmark");}

    public boolean checkDeadline(String str) {return str.contains("deadline");}

    public boolean checkEvent(String str) {return str.contains("event");}

    public boolean checkToDo(String str) {return str.contains("todo");}

    public boolean checkDelete(String str) {return str.contains("delete");}

    public void typeMessage() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        while (!isBye(str)){
            //echo(str);
            try {
                if (checkList(str)) {
                    display();
                } else if (checkUnMark(str)) {
                    setUndone(str);
                } else if (checkMark(str)) {
                    setDone(str);
                } else if (checkDeadline(str)) {
                    setDeadline(str.substring(8));
                } else if (checkEvent(str)) {
                    setEvent(str.substring(5));
                } else if(checkToDo(str)) {
                    setToDo(str.substring(4));
                } else if (checkDelete(str)) {
                    deleteTask(str);
                } else {
                    throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (DukeException e) {
                divider();
                System.out.println(e.getMessage());
                divider();
            }
            str = sc.nextLine();
        }
        if (isBye(str)) {
            exit();
        }
    }

    public void display() {
        int count = 0;
        int serial = 1;
        divider();
        while (count < list.size()) {
            System.out.println(serial + "." + list.get(count));
            count++;
            serial++;
        }
        divider();
    }

    public void setDone(String str) {
        int index = task.getIndexOfMark(str);
        list.get(index).markAsDone();
        divider();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + list.get(index));
        divider();
    }

    public void setUndone(String str) {
        int index = task.getIndexOfUnmark(str);
        list.get(index).markUndone();
        divider();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + list.get(index));
        divider();
    }

    public void divider() {
        System.out.println("____________________________________________________________");
    }
    public void numberOfTasks() {
        System.out.println("Now you have " + list.size() +" tasks in the list.");
    }

    public void echoAdd(Task t) {
        divider();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        numberOfTasks();
        divider();
    }

    public void setDeadline(String str) {
        String[] arr = str.split("/by ");
        Deadline temp = new Deadline(arr[0], arr[1]);
        list.add(temp);
        echoAdd(temp);
    }

    public void setEvent(String str) {
        String[] event =  str.split("/from | /to ");
        Events tmp = new Events(event[1], event[2], event[0]);
        list.add(tmp);
        echoAdd(tmp);
    }

    public void setToDo(String str)
    throws DukeException{
        String[] todo = str.split(" ");
        try {
            if (todo.length == 2) {
                ToDo toDo = new ToDo(todo[1]);
                list.add(toDo);
                echoAdd(toDo);
            } else {
                throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
            }
        } catch (DukeException e) {
            divider();
            System.out.println(e.getMessage());
            divider();
        }
    }

    public void deleteTask(String str) {
        String[] string = str.split(" ");
        int index = Integer.parseInt(string[1]);
        Task t = list.remove(index);
        divider();
        System.out.println("Noted. I've removed this task:");
        System.out.println(" " + t.toString());
        numberOfTasks();
        divider();
    }
    public static void main(String[] args) {
        DuckBot duck = new DuckBot();
        duck.welcomeMessage();
        duck.typeMessage();
    }
}