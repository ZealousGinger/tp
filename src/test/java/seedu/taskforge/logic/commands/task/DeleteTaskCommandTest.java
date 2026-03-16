package seedu.taskforge.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.taskforge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.taskforge.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.taskforge.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.Messages;
import seedu.taskforge.logic.commands.task.DeleteTaskCommand.DeleteTaskDescriptor;
import seedu.taskforge.model.AddressBook;
import seedu.taskforge.model.Model;
import seedu.taskforge.model.ModelManager;
import seedu.taskforge.model.UserPrefs;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.testutil.DeleteTaskDescriptorBuilder;
import seedu.taskforge.testutil.PersonBuilder;

public class DeleteTaskCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteOneTaskUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks().build();

        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("1").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteOneTaskFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks().build();

        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("1").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteOneTaskOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("2").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_deleteOneTaskOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("2").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_deleteMultipleTasksOutOfBoundUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("1", "2").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(indexFirstPerson, descriptor);

        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_deleteMultipleTasksOutOfBoundFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("1", "2").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_INDEX_OUT_OF_BOUND);
    }

    @Test
    public void execute_deleteMultipleTasksUnfilteredList_success() {
        Index indexSecondPerson = Index.fromOneBased(2);
        Person firstPerson = model.getFilteredPersonList().get(indexSecondPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks().build();

        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("1", "2").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(indexSecondPerson, descriptor);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteMultipleTasksFilteredList_success() {
        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withTasks().build();

        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("1", "2").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noTaskSpecifiedUnfilteredList_errorThrown() {
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON, new DeleteTaskDescriptor());
        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noTaskSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_FIRST_PERSON, new DeleteTaskDescriptor());
        assertCommandFailure(deleteTaskCommand, model, DeleteTaskCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteTaskDescriptor descriptor = new DeleteTaskDescriptorBuilder()
                .withTasks("1").build();
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds task to a person of a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(outOfBoundIndex,
                new DeleteTaskDescriptorBuilder().withTasks("1").build());

        assertCommandFailure(deleteTaskCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
