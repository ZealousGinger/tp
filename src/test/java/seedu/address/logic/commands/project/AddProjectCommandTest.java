package seedu.address.logic.commands.project;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_X;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_Y;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_Z;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddProjectDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddProjectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addOneProjectUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA).build();

        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AddProjectCommand.MESSAGE_ADD_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addOneProjectFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA).build();

        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddProjectCommand.MESSAGE_ADD_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addOneProjectDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_ALPHA).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(addProjectCommand, model, AddProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_addOneProjectDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_ALPHA).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(addProjectCommand, model, AddProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_addMultipleProjectsDuplicateUnfilteredList_exceptionThrown() {
        Index indexFirstPerson = Index.fromOneBased(1);
        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA, VALID_PROJECT_X, VALID_PROJECT_Y,
                VALID_PROJECT_Z, VALID_PROJECT_ALPHA).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(indexFirstPerson, descriptor);

        assertCommandFailure(addProjectCommand, model, AddProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_addMultipleProjectsDuplicateFilteredList_exceptionThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA, VALID_PROJECT_X, VALID_PROJECT_Y,
                VALID_PROJECT_Z, VALID_PROJECT_ALPHA).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(addProjectCommand, model, AddProjectCommand.MESSAGE_DUPLICATE_PROJECT);
    }

    @Test
    public void execute_addMultipleProjectsUnfilteredList_success() {
        Index indexFirstPerson = Index.fromOneBased(1);
        Person firstPerson = model.getFilteredPersonList().get(indexFirstPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA,
                VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z).build();

        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA, VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(indexFirstPerson, descriptor);

        String expectedMessage = String.format(AddProjectCommand.MESSAGE_ADD_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultipleProjectsFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(firstPerson);
        Person editedPerson = personInList.withProjects(VALID_PROJECT_ALPHA, VALID_PROJECT_BETA,
                VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z).build();

        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_BETA, VALID_PROJECT_X, VALID_PROJECT_Y, VALID_PROJECT_Z).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddProjectCommand.MESSAGE_ADD_PROJECT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addProjectCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noProjectSpecifiedUnfilteredList_errorThrown() {
        AddProjectCommand addProjectCommand = new AddProjectCommand(
                INDEX_FIRST_PERSON, new AddProjectCommand.AddProjectDescriptor()
        );
        assertCommandFailure(addProjectCommand, model, AddProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_noProjectSpecifiedFilteredList_errorThrown() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddProjectCommand addProjectCommand = new AddProjectCommand(
                INDEX_FIRST_PERSON, new AddProjectCommand.AddProjectDescriptor()
        );
        assertCommandFailure(addProjectCommand, model, AddProjectCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddProjectCommand.AddProjectDescriptor descriptor = new AddProjectDescriptorBuilder()
                .withProjects(VALID_PROJECT_X).build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Adds project to a person of a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddProjectCommand addProjectCommand = new AddProjectCommand(outOfBoundIndex,
                new AddProjectDescriptorBuilder().withProjects(VALID_PROJECT_X).build());

        assertCommandFailure(addProjectCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
