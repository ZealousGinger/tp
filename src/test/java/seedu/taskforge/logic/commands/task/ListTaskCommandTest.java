package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.project.Project;

public class ListTaskCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_projectHasTasks_success() {
        ListTaskCommand command = new ListTaskCommand(new Project(VALID_PROJECT_ALPHA));
        String expectedMessage = """
            Listed tasks for project Alpha:
            1. [refactor code]
            2. [fix error in tp project]""";

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_projectHasNoTasks_success() {
        ListTaskCommand command = new ListTaskCommand(new Project(VALID_PROJECT_X));
        String expectedMessage = "Listed tasks for project Project X: None";

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void execute_projectNotFound_failure() {
        ListTaskCommand command = new ListTaskCommand(new Project("Unknown Project"));
        assertCommandFailure(command, model, ListTaskCommand.MESSAGE_PROJECT_NOT_FOUND);
    }

    @Test
    public void equals() {
        ListTaskCommand firstCommand = new ListTaskCommand(new Project(VALID_PROJECT_ALPHA));
        ListTaskCommand secondCommand = new ListTaskCommand(new Project(VALID_PROJECT_X));

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(new ListTaskCommand(new Project(VALID_PROJECT_ALPHA))));
        assertFalse(firstCommand.equals(secondCommand));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals((Object) null));
    }

    @Test
    public void hashCodeMethod() {
        assertEquals(new Project(VALID_PROJECT_ALPHA).hashCode(),
                new ListTaskCommand(new Project(VALID_PROJECT_ALPHA)).hashCode());
    }
}
