package seedu.taskforge.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HelpCommandTest {

    @Test
    void execute_showsHelp() {
        HelpCommand helpCommand = new HelpCommand();

        CommandResult commandResult = helpCommand.execute(null);

        assertEquals(HelpCommand.SHOWING_HELP_MESSAGE, commandResult.getFeedbackToUser());
        assertTrue(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
    }
}
