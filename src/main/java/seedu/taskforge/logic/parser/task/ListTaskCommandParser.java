package seedu.taskforge.logic.parser.task;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.taskforge.logic.commands.task.ListTaskCommand;
import seedu.taskforge.logic.parser.ArgumentMultimap;
import seedu.taskforge.logic.parser.ArgumentTokenizer;
import seedu.taskforge.logic.parser.Parser;
import seedu.taskforge.logic.parser.ParserUtil;
import seedu.taskforge.logic.parser.exceptions.ParseException;
import seedu.taskforge.model.project.Project;

/**
 * Parses input arguments and creates a new ListTaskCommand object.
 */
public class ListTaskCommandParser implements Parser<ListTaskCommand> {

    @Override
    public ListTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListTaskCommand.MESSAGE_USAGE_LIST));
        }

        List<String> projectNames = argMultimap.getAllValues(PREFIX_NAME);
        if (projectNames.size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListTaskCommand.MESSAGE_USAGE_LIST));
        }

        if (projectNames.get(0).trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListTaskCommand.MESSAGE_USAGE_LIST));
        }

        Project project = ParserUtil.parseProject(projectNames.get(0));
        return new ListTaskCommand(project);
    }
}
