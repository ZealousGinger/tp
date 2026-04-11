package seedu.taskforge.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.model.person.Email;
import seedu.taskforge.model.person.Name;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.person.Phone;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class FindCommandFieldsTest {

    @Test
    public void getTaskSearchFields_returnsTaskDescriptionsInPersonOrder() throws Exception {
        List<Project> projects = List.of(
                new Project("Alpha", List.of(new Task("Fix login"), new Task("Write tests"))),
                new Project("Beta", List.of(new Task("Deploy"))));
        Person person = new Person(
                new Name("Alex"),
                new Phone("91234567"),
                new Email("alex@example.com"),
                List.of(new PersonProject(0), new PersonProject(1)),
                List.of(new PersonTask(0, 1), new PersonTask(1, 0)));

        List<String> fields = invokeTaskSearchFields(person, projects);

        assertEquals(List.of("Write tests", "Deploy"), fields);
    }

    @Test
    public void getProjectSearchFields_returnsProjectTitlesInPersonOrder() throws Exception {
        List<Project> projects = List.of(
                new Project("Alpha", List.of(new Task("Fix login"))),
                new Project("Beta", List.of(new Task("Deploy"))));
        Person person = new Person(
                new Name("Alex"),
                new Phone("91234567"),
                new Email("alex@example.com"),
                List.of(new PersonProject(1), new PersonProject(0)),
                List.of());

        List<String> fields = invokeProjectSearchFields(person, projects);

        assertEquals(List.of("Beta", "Alpha"), fields);
    }

    @SuppressWarnings("unchecked")
    private List<String> invokeTaskSearchFields(Person person, List<Project> projects) throws Exception {
        Method method = FindCommand.class.getDeclaredMethod("getTaskSearchFields", Person.class, List.class);
        method.setAccessible(true);
        return (List<String>) method.invoke(null, person, projects);
    }

    @SuppressWarnings("unchecked")
    private List<String> invokeProjectSearchFields(Person person, List<Project> projects) throws Exception {
        Method method = FindCommand.class.getDeclaredMethod("getProjectSearchFields", Person.class, List.class);
        method.setAccessible(true);
        return (List<String>) method.invoke(null, person, projects);
    }
}

