package seedu.taskforge.logic.commands;

import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalTaskForge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.taskforge.logic.commands.person.ListCommand;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaskForge(), new UserPrefs());
        expectedModel = new ModelManager(model.getTaskForge(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
