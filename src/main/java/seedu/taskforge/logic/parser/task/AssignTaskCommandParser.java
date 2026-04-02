package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AssignTaskCommand;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignTaskCommand object
 */
public class AssignTaskCommandParser implements Parser<AssignTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignTaskCommand
     * and returns an AssignTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INDEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE), pe);
        }

        AssignTaskDescriptor assignTaskDescriptor = new AssignTaskDescriptor();

        Collection<String> taskNames = argMultimap.getAllValues(PREFIX_NAME);
        Collection<String> taskIndexes = argMultimap.getAllValues(PREFIX_INDEX);
        boolean hasTaskNamesPrefix = !taskNames.isEmpty();
        boolean hasTaskIndexesPrefix = !taskIndexes.isEmpty();

        if (hasTaskNamesPrefix == hasTaskIndexesPrefix) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));
        }

        if (hasTaskNamesPrefix) {
            parseTasksForAdd(taskNames).ifPresent(assignTaskDescriptor::setTasks);
        } else {
            parseTasksIndexesForAdd(taskIndexes)
                    .ifPresent(assignTaskDescriptor::setTaskIndexes);
        }

        if (!assignTaskDescriptor.isTaskFieldEdited()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignTaskCommand.MESSAGE_USAGE));
        }

        return new AssignTaskCommand(index, assignTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tasks} into a {@code List<Task>} if {@code tasks} is non-empty.
     * If {@code tasks} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Task>} containing zero tasks.
     */
    private boolean hasValues(Collection<String> values) {
        return !(values.isEmpty() || (values.size() == 1 && values.contains("")));
    }

    private Optional<List<seedu.taskforge.model.task.Task>> parseTasksForAdd(Collection<String> tasks)
            throws ParseException {
        assert tasks != null;

        if (!hasValues(tasks) || tasks.contains("")) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseTasks(tasks));
    }

    private Optional<List<Index>> parseTasksIndexesForAdd(Collection<String> taskIndexes) throws ParseException {
        assert taskIndexes != null;

        if (!hasValues(taskIndexes) || taskIndexes.contains("")) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseTaskIndexes(taskIndexes));
    }
}
