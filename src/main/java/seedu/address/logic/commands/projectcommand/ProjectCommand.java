package seedu.address.logic.commands.projectcommand;

import seedu.address.logic.commands.Command;

public abstract class ProjectCommand extends Command {

    public static final String COMMAND_WORD = "project";

    public static final String MESSAGE_SUCCESS = "New project added: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " needs add or delete";

}
