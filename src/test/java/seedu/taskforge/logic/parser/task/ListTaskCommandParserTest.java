package seedu.taskforge.logic.parser.task;

import static seedu.taskforge.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskforge.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.task.ListTaskCommand;
import seedu.taskforge.model.project.Project;

public class ListTaskCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE_LIST);

    private final ListTaskCommandParser parser = new ListTaskCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " -n alpha", new ListTaskCommand(new Project("alpha")));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "alpha", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " -n", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " -n alpha -n beta", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }
}
