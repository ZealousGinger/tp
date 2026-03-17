package seedu.taskforge.logic.commands.project;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;

import seedu.taskforge.commons.util.ToStringBuilder;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;

/**
 * Lists all projects in the address book to the user.
 */
public class ListProjectCommand extends ProjectCommand {

    public static final String SUBCOMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all projects:";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String projectsList = model.getProjectList().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
        String feedback = MESSAGE_SUCCESS + (projectsList.isEmpty() ? " None" : "\n" + projectsList);
        return new CommandResult(feedback);
    }


    @Override
    public boolean equals(Object other) {
        return other instanceof ListProjectCommand;
    }

    @Override
    public int hashCode() {
        return SUBCOMMAND_WORD.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subcommandWord", SUBCOMMAND_WORD)
                .toString();
    }
}
