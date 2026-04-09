package seedu.taskforge.logic.commands.task;

import java.util.List;

import seedu.taskforge.logic.commands.Command;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.task.Task;

/**
 * Represents a command that handles task-related operations.
 * This is an abstract base class for all specific task commands.
 */
public abstract class TaskCommand extends Command {

    public static final String COMMAND_WORD = "task";
    public static final String MESSAGE_USAGE = "task should be followed by "
            + "add/"
            + "delete/"
            + "edit/"
            + "list/"
            + "assign/"
            + "unassign/"
            + "mark/"
            + "unmark/"
            + "view/"
            + "find";
    public static final String MESSAGE_TASK_NOT_IN_ASSIGNED_PROJECTS =
            "The task must exist in at least one project assigned to the person.";
    public static final String MESSAGE_INVALID_TASK_REFERENCE = "This task reference is invalid.";

    protected static Task resolveTask(Model model, PersonTask personTask) throws CommandException {
        int projectIndex = personTask.getProjectIndex();
        int taskIndex = personTask.getTaskIndex();
        if (projectIndex < 0 || projectIndex >= model.getProjectList().size()) {
            throw new CommandException(MESSAGE_INVALID_TASK_REFERENCE);
        }
        List<Task> projectTasks = model.getProjectList().get(projectIndex).getTasks();
        if (taskIndex < 0 || taskIndex >= projectTasks.size()) {
            throw new CommandException(MESSAGE_INVALID_TASK_REFERENCE);
        }
        return projectTasks.get(taskIndex);
    }
}
