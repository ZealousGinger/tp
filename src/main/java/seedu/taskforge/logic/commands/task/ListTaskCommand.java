package seedu.taskforge.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.taskforge.commons.util.ToStringBuilder;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.logic.commands.exceptions.CommandException;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Lists all tasks in a project.
 */
public class ListTaskCommand extends TaskCommand {

    public static final String SUBCOMMAND_WORD = "list";

    public static final String MESSAGE_USAGE_LIST = COMMAND_WORD + " " + SUBCOMMAND_WORD
            + " -n PROJECT_NAME";
    public static final String MESSAGE_PROJECT_NOT_FOUND = "The project was not found in the address book.";
    public static final String MESSAGE_SUCCESS = "Listed tasks for project %1$s:";

    private final Project targetProject;

    /**
     * Creates a ListTaskCommand to list all tasks in a specified project.
     */
    public ListTaskCommand(Project targetProject) {
        requireNonNull(targetProject);
        this.targetProject = targetProject;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Project> maybeProject = model.getProjectList().stream()
                .filter(project -> project.equals(targetProject))
                .findFirst();

        if (maybeProject.isEmpty()) {
            throw new CommandException(MESSAGE_PROJECT_NOT_FOUND);
        }

        Project project = maybeProject.get();
        List<Task> tasks = project.getTasks();
        String taskList = IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + ". " + tasks.get(i).toString())
                .collect(Collectors.joining("\n"));

        String feedback = String.format(MESSAGE_SUCCESS, project.title)
                + (taskList.isEmpty() ? " None" : "\n" + taskList);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ListTaskCommand
                && targetProject.equals(((ListTaskCommand) other).targetProject));
    }

    @Override
    public int hashCode() {
        return targetProject.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subcommandWord", SUBCOMMAND_WORD)
                .add("project", targetProject)
                .toString();
    }
}
