package seedu.taskforge.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;

import seedu.taskforge.commons.util.ToStringBuilder;
import seedu.taskforge.logic.commands.Command;
import seedu.taskforge.logic.commands.CommandResult;
import seedu.taskforge.model.Model;

/**
 * Lists all tags in the address book to the user.
 */
public class ViewAllTagCommand extends Command {

    public static final String COMMAND_WORD = "view-all-tag";

    public static final String MESSAGE_SUCCESS = "Listed all tags:";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String tagsList = model.getTagList().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
        String feedback = MESSAGE_SUCCESS + (tagsList.isEmpty() ? " None" : "\n" + tagsList);
        return new CommandResult(feedback);
    }


    @Override
    public boolean equals(Object other) {
        return other instanceof ViewAllTagCommand;
    }

    @Override
    public int hashCode() {
        return COMMAND_WORD.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("commandWord", COMMAND_WORD)
                .toString();
    }
}
