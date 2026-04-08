package seedu.taskforge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_PROJECT_ALPHA;
import static seedu.taskforge.logic.commands.CommandTestUtil.VALID_TASK_REFACTOR;
import static seedu.taskforge.testutil.Assert.assertThrows;
import static seedu.taskforge.testutil.TypicalPersons.ALICE;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalTaskForge;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.person.exceptions.DuplicatePersonException;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.project.exceptions.DuplicateProjectException;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.testutil.PersonBuilder;

public class TaskForgeTest {

    private final TaskForge taskForge = new TaskForge();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskForge.getPersonList());
        assertEquals(Collections.emptyList(), taskForge.getProjectList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskForge.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTaskForge_replacesData() {
        TaskForge newData = getTypicalTaskForge();
        taskForge.resetData(newData);
        assertEquals(newData, taskForge);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE)
                .withProjects(VALID_PROJECT_ALPHA).withTasks(VALID_TASK_REFACTOR)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        TaskForgeStub newData = new TaskForgeStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> taskForge.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateProjects_throwsDuplicateProjectException() {
        Project alpha = new Project("alpha");
        List<Project> newProjects = Arrays.asList(alpha, alpha);
        TaskForgeStub newData = new TaskForgeStub(Collections.emptyList(), newProjects);

        assertThrows(DuplicateProjectException.class, () -> taskForge.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskForge.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInTaskForge_returnsFalse() {
        assertFalse(taskForge.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInTaskForge_returnsTrue() {
        taskForge.addPerson(ALICE);
        assertTrue(taskForge.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInTaskForge_returnsTrue() {
        taskForge.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE)
                .withProjects(VALID_PROJECT_ALPHA).withTasks(VALID_TASK_REFACTOR)
                .build();
        assertTrue(taskForge.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taskForge.getPersonList().remove(0));
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taskForge.hasProject(null));
    }

    @Test
    public void hasProject_projectNotInTaskForge_returnsFalse() {
        assertFalse(taskForge.hasProject(new Project("alpha")));
    }

    @Test
    public void hasProject_projectInTaskForge_returnsTrue() {
        Project alpha = new Project("alpha");
        taskForge.addProject(alpha);
        assertTrue(taskForge.hasProject(alpha));
    }

    @Test
    public void removeProject_existingProject_removesProjectFromAllPersons() {
        Project alpha = new Project("alpha");
        Project beta = new Project("beta");
        Person firstPerson = new PersonBuilder().withName("First Person")
                .withPhone("11111111").withEmail("first@example.com")
                .withProjects("alpha", "beta").build();
        Person secondPerson = new PersonBuilder().withName("Second Person")
                .withPhone("22222222").withEmail("second@example.com")
                .withProjects("alpha").build();
        Person thirdPerson = new PersonBuilder().withName("Third Person")
                .withPhone("33333333").withEmail("third@example.com")
                .withProjects("beta").build();

        taskForge.addProject(alpha);
        taskForge.addProject(beta);
        taskForge.addPerson(firstPerson);
        taskForge.addPerson(secondPerson);
        taskForge.addPerson(thirdPerson);

        taskForge.removeProject(alpha);

        assertFalse(taskForge.hasProject(alpha));
        assertEquals(Arrays.asList(new PersonProject(0)),
            taskForge.getPersonList().get(0).getProjects());
        assertEquals(Collections.emptyList(), taskForge.getPersonList().get(1).getProjects());
        assertEquals(Collections.emptyList(),
            taskForge.getPersonList().get(2).getProjects());
    }

    @Test
    public void removeProject_withMixedTaskReferences_reindexesAndRetainsExpectedTasks() {
        Project alpha = new Project("alpha", List.of(new Task("plan release")));
        Project beta = new Project("beta", List.of(new Task("fix bug")));
        Project gamma = new Project("gamma", List.of(new Task("write docs")));

        Person unchangedPerson = new Person(
                new Name("Unchanged Person"),
                new Phone("84444444"),
                new Email("unchanged@example.com"),
                List.of(new PersonProject(0)),
                List.of(new PersonTask(0, 0)));
        Person mixedPerson = new Person(
                new Name("Mixed Person"),
                new Phone("85555555"),
                new Email("mixed@example.com"),
                List.of(new PersonProject(0), new PersonProject(1), new PersonProject(2)),
                List.of(new PersonTask(1, 0), new PersonTask(2, 0), new PersonTask(0, 0)));

        taskForge.addProject(alpha);
        taskForge.addProject(beta);
        taskForge.addProject(gamma);
        taskForge.addPerson(unchangedPerson);
        taskForge.addPerson(mixedPerson);

        taskForge.removeProject(beta);

        Person updatedUnchangedPerson = taskForge.getPersonList().get(0);
        assertEquals(List.of(new PersonProject(0)), updatedUnchangedPerson.getProjects());
        assertEquals(List.of(new PersonTask(0, 0)), updatedUnchangedPerson.getTasks());

        Person updatedMixedPerson = taskForge.getPersonList().get(1);
        assertEquals(List.of(new PersonProject(0), new PersonProject(1)), updatedMixedPerson.getProjects());
        assertEquals(List.of(new PersonTask(1, 0), new PersonTask(0, 0)), updatedMixedPerson.getTasks());
    }

    @Test
    public void setProject_taskDeletedFromProject_cascadesAssignedTasks() {
        Project alpha = new Project("alpha", Arrays.asList(
            new Task("refactor code"),
            new Task("fix error in tp project")
        ));
        Person person = new Person(
            new Name("Task Owner"),
            new Phone("99998888"),
            new Email("owner@example.com"),
            Arrays.asList(new PersonProject(0)),
            Arrays.asList(
                new PersonTask(0, 0),
                new PersonTask(0, 1)
            )
        );

        taskForge.addProject(alpha);
        taskForge.addPerson(person);

        Project editedAlpha = new Project("alpha", Arrays.asList(new Task("refactor code")));
        taskForge.setProject(alpha, editedAlpha);

        List<PersonTask> updatedTasks = taskForge.getPersonList().get(0).getTasks();
        assertEquals(1, updatedTasks.size());
        assertEquals(new PersonTask(0, 0), updatedTasks.get(0));
    }

    @Test
    public void setProject_taskDeleted_keepsTasksFromOtherProjects() {
        Project alpha = new Project("alpha", List.of(new Task("remove me")));
        Project beta = new Project("beta", List.of(new Task("keep me")));
        Person person = new Person(
                new Name("Cross Project Owner"),
                new Phone("96666666"),
                new Email("cross@example.com"),
                List.of(new PersonProject(0), new PersonProject(1)),
                List.of(new PersonTask(0, 0), new PersonTask(1, 0)));

        taskForge.addProject(alpha);
        taskForge.addProject(beta);
        taskForge.addPerson(person);

        Project editedAlpha = new Project("alpha", Collections.emptyList());
        taskForge.setProject(alpha, editedAlpha);

        List<PersonTask> updatedTasks = taskForge.getPersonList().get(0).getTasks();
        assertEquals(List.of(new PersonTask(1, 0)), updatedTasks);
    }

    @Test
    public void getProjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taskForge.getProjectList().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        TaskForge taskForgeCopy = new TaskForge(taskForge);
        assertTrue(taskForge.equals(taskForgeCopy));

        // same object -> returns true
        assertTrue(taskForge.equals(taskForge));

        // null -> returns false
        assertFalse(taskForge.equals((Object) null));

        // different types -> returns false
        assertFalse(taskForge.equals(5));

        // different data -> returns false
        TaskForge differentTaskForge = new TaskForge();
        differentTaskForge.addProject(new Project("alpha"));
        assertFalse(taskForge.equals(differentTaskForge));
    }

    @Test
    public void hashCodeMethod() {
        TaskForge taskForgeCopy = new TaskForge(taskForge);
        assertEquals(taskForge.hashCode(), taskForgeCopy.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = TaskForge.class.getCanonicalName() + "{persons=" + taskForge.getPersonList()
            + ", projects=" + taskForge.getProjectList() + "}";
        assertEquals(expected, taskForge.toString());
    }

    /**
     * A stub ReadOnlyTaskForge whose persons list can violate interface constraints.
     */
    private static class TaskForgeStub implements ReadOnlyTaskForge {
        private final javafx.collections.ObservableList<Person> persons = FXCollections.observableArrayList();
        private final javafx.collections.ObservableList<Project> projects = FXCollections.observableArrayList();

        TaskForgeStub(Collection<Person> persons) {
            this(persons, Collections.emptyList());
        }

        TaskForgeStub(Collection<Person> persons, Collection<Project> projects) {
            this.persons.setAll(persons);
            this.projects.setAll(projects);
        }

        @Override
        public javafx.collections.ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public javafx.collections.ObservableList<Project> getProjectList() {
            return projects;
        }
    }

}
