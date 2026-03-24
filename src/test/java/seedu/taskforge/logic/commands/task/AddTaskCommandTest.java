package seedu.taskforge.logic.commands.task;

import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_FIX_ERROR;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_IMPLEMENT_X;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class AddTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addOneTask_success() {
        Project firstProject = model.getProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedProject = new Project(firstProject.title, Arrays.asList(new Task(VALID_TASK_IMPLEMENT_X)));

        AddTaskCommand.AddTaskDescriptor descriptor = new AddTaskCommand.AddTaskDescriptor();
        descriptor.setTasks(Arrays.asList(new Task(VALID_TASK_IMPLEMENT_X)));
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PROJECT, descriptor);

        String expectedMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, editedProject);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setProject(firstProject, editedProject);

        assertCommandSuccess(addTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateTask_failure() {
        Project firstProject = model.getProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project projectWithTask = new Project(firstProject.title, Arrays.asList(new Task(VALID_TASK_FIX_ERROR)));
        model.setProject(firstProject, projectWithTask);

        AddTaskCommand.AddTaskDescriptor descriptor = new AddTaskCommand.AddTaskDescriptor();
        descriptor.setTasks(Arrays.asList(new Task(VALID_TASK_FIX_ERROR)));
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PROJECT, descriptor);

        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_noTaskSpecified_failure() {
        AddTaskCommand addTaskCommand = new AddTaskCommand(INDEX_FIRST_PROJECT, new AddTaskCommand.AddTaskDescriptor());
        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidProjectIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getProjectList().size() + 1);

        AddTaskCommand.AddTaskDescriptor descriptor = new AddTaskCommand.AddTaskDescriptor();
        descriptor.setTasks(Arrays.asList(new Task(VALID_TASK_IMPLEMENT_X)));
        AddTaskCommand addTaskCommand = new AddTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addTaskCommand, model, AddTaskCommand.MESSAGE_INVALID_PROJECT_INDEX);
    }
}
