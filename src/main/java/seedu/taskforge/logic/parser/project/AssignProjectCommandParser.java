package seedu.taskforge.logic.parser.project;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.AssignProjectCommand;
import seedu.taskforge.logic.commands.project.AssignProjectCommand.AssignProjectDescriptor;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignProjectCommand object.
 */
public class AssignProjectCommandParser implements Parser<AssignProjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignProjectCommand
     * and returns a AssignProjectCommand object for execution.
     *
     * @param args The arguments string to be parsed.
     * @return A AssignProjectCommand object containing the parsed index and project.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AssignProjectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INDEX);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProjectCommand.MESSAGE_USAGE), pe);
        }

        AssignProjectDescriptor assignProjectDescriptor = new AssignProjectDescriptor();

        Collection<String> projectNames = argMultimap.getAllValues(PREFIX_NAME);
        Collection<String> projectIndexes = argMultimap.getAllValues(PREFIX_INDEX);
        boolean hasProjectNamesPrefix = !projectNames.isEmpty();
        boolean hasProjectIndexesPrefix = !projectIndexes.isEmpty();

        if (hasProjectNamesPrefix == hasProjectIndexesPrefix) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProjectCommand.MESSAGE_USAGE));
        }

        if (hasProjectNamesPrefix) {
            parseProjectsForAdd(projectNames).ifPresent(assignProjectDescriptor::setProjects);
        } else {
            parseProjectIndexesForAdd(projectIndexes)
                    .ifPresent(assignProjectDescriptor::setProjectIndexes);
        }

        if (!assignProjectDescriptor.isProjectFieldEdited()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProjectCommand.MESSAGE_USAGE));
        }

        return new AssignProjectCommand(index, assignProjectDescriptor);
    }

    /**
     * Parses {@code Collection<String> projects} into a {@code List<Project>} if {@code projects} is non-empty.
     * If {@code projects} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Project>} containing zero projects.
     */
    private boolean hasValues(Collection<String> values) {
        return !(values.isEmpty() || (values.size() == 1 && values.contains("")));
    }

    private Optional<List<seedu.taskforge.model.project.Project>> parseProjectsForAdd(Collection<String> projects)
            throws ParseException {
        assert projects != null;

        if (!hasValues(projects) || projects.contains("")) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseProjects(projects));
    }

    private Optional<List<Index>> parseProjectIndexesForAdd(Collection<String> projectIndexes) throws ParseException {
        assert projectIndexes != null;

        if (!hasValues(projectIndexes) || projectIndexes.contains("")) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseProjectIndexes(projectIndexes));
    }
}
