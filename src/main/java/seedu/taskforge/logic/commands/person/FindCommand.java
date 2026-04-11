package seedu.taskforge.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_PROJECT_TITLE;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.List;

import seedu.taskforge.commons.util.ToStringBuilder;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.Command;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonContainsKeywordsPredicate;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * Finds and lists all persons in TaskForge whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TASK + "TASK]...\n"
            + "[" + PREFIX_PROJECT_TITLE + "PROJECT]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob " + PREFIX_PHONE + "91234567";

    private final PersonContainsKeywordsPredicate predicate;

    public FindCommand(PersonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Project> projectList = model.getProjectList();
        predicate.setTaskFieldMapper(person -> getTaskSearchFields(person, projectList))
                .setProjectFieldMapper(person -> getProjectSearchFields(person, projectList));

        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    private static List<String> getTaskSearchFields(Person person, List<Project> projects) {
        List<String> fields = new ArrayList<>();
        for (PersonTask personTask : person.getTasks()) {
            int projectIndex = personTask.getProjectIndex();
            assert projectIndex >= 0 && projectIndex < projects.size() : "Stale project reference in PersonTask";

            Project project = projects.get(projectIndex);
            List<Task> tasks = project.getTasks();
            int taskIndex = personTask.getTaskIndex();
            assert taskIndex >= 0 && taskIndex < tasks.size() : "Stale task reference in PersonTask";

            Task task = tasks.get(taskIndex);
            fields.add(task.description);
        }
        return fields;
    }

    private static List<String> getProjectSearchFields(Person person, List<Project> projects) {
        List<String> fields = new ArrayList<>();
        for (PersonProject personProject : person.getProjects()) {
            int projectIndex = personProject.getProjectIndex();
            assert projectIndex >= 0 && projectIndex < projects.size() : "Stale project reference in PersonProject";
            fields.add(projects.get(projectIndex).title);
        }
        return fields;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
