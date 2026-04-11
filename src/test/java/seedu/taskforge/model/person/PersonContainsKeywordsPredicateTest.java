package seedu.taskforge.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;
import seedu.taskforge.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    private static final List<Project> SAMPLE_PROJECTS = List.of(
            new Project("Project1", List.of(new Task("Task1"), new Task("Task2"))),
            new Project("Project2", List.of(new Task("Task3"))));

    @Test
    public void equals() {
        List<String> nameKeywords = Collections.singletonList("Alice");
        List<String> differentNameKeywords = Collections.singletonList("Bob");

        List<String> phoneKeywords = Collections.singletonList("91234567");
        List<String> differentPhoneKeywords = Collections.singletonList("88887777");

        List<String> emailKeywords = Collections.singletonList("alice@example.com");
        List<String> differentEmailKeywords = Collections.singletonList("bob@example.com");

        List<String> taskKeywords = Collections.singletonList("Task1");
        List<String> differentTaskKeywords = Collections.singletonList("Task2");

        List<String> projectKeywords = Collections.singletonList("Project1");
        List<String> differentProjectKeywords = Collections.singletonList("Project2");

        PersonContainsKeywordsPredicate basePredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(emailKeywords)
                .setTaskKeywords(taskKeywords)
                .setProjectKeywords(projectKeywords);

        // same object -> returns true
        assertTrue(basePredicate.equals(basePredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate basePredicateCopy = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(emailKeywords)
                .setTaskKeywords(taskKeywords)
                .setProjectKeywords(projectKeywords);
        assertTrue(basePredicate.equals(basePredicateCopy));

        // different types -> returns false
        assertFalse(basePredicate.equals(1));

        // null -> returns false
        assertFalse(basePredicate.equals((Object) null));

        // different nameKeywords -> returns false
        PersonContainsKeywordsPredicate differentNamePredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(differentNameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(emailKeywords)
                .setTaskKeywords(taskKeywords)
                .setProjectKeywords(projectKeywords);
        assertFalse(basePredicate.equals(differentNamePredicate));

        // different phoneKeywords -> returns false
        PersonContainsKeywordsPredicate differentPhonePredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(differentPhoneKeywords)
                .setEmailKeywords(emailKeywords)
                .setTaskKeywords(taskKeywords)
                .setProjectKeywords(projectKeywords);
        assertFalse(basePredicate.equals(differentPhonePredicate));

        // different emailKeywords -> returns false
        PersonContainsKeywordsPredicate differentEmailPredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(differentEmailKeywords)
                .setTaskKeywords(taskKeywords)
                .setProjectKeywords(projectKeywords);
        assertFalse(basePredicate.equals(differentEmailPredicate));

        // different taskKeywords -> returns false
        PersonContainsKeywordsPredicate differentTaskPredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(emailKeywords)
                .setTaskKeywords(differentTaskKeywords)
                .setProjectKeywords(projectKeywords);
        assertFalse(basePredicate.equals(differentTaskPredicate));

        // different projectKeywords -> returns false
        PersonContainsKeywordsPredicate differentProjectPredicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(nameKeywords)
                .setPhoneKeywords(phoneKeywords)
                .setEmailKeywords(emailKeywords)
                .setTaskKeywords(taskKeywords)
                .setProjectKeywords(differentProjectKeywords);
        assertFalse(basePredicate.equals(differentProjectPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate().setNameKeywords(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate().setNameKeywords(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate().setNameKeywords(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setPhoneKeywords(Collections.singletonList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate()
                .setPhoneKeywords(Arrays.asList("91234567", "88887777"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));

        // Mixed-case keywords (not applicable for phone, but following pattern)
        predicate = new PersonContainsKeywordsPredicate().setPhoneKeywords(Arrays.asList("91234567"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91234567").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setEmailKeywords(Collections.singletonList("alice@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate()
                .setEmailKeywords(Arrays.asList("alice@example.com", "bob@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate()
                .setEmailKeywords(Arrays.asList("aLIce@EXample.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_taskContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = withResolvedFields(new PersonContainsKeywordsPredicate()
                .setTaskKeywords(Collections.singletonList("Project1:Task1")));
        assertTrue(predicate.test(new PersonBuilder().withTasks("Task1", "Task2").build()));

        // Multiple keywords
        predicate = withResolvedFields(new PersonContainsKeywordsPredicate().setTaskKeywords(Arrays.asList(
                "Project1:Task1", "Project1:Task2")));
        assertTrue(predicate.test(new PersonBuilder().withTasks("Task1").build()));

        // Mixed-case keywords
        predicate = withResolvedFields(new PersonContainsKeywordsPredicate().setTaskKeywords(Arrays.asList(
                "pROjEcT1:tASk1")));
        assertTrue(predicate.test(new PersonBuilder().withTasks("Task1").build()));
    }

    @Test
    public void test_projectContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = withResolvedFields(new PersonContainsKeywordsPredicate()
                .setProjectKeywords(Collections.singletonList("Project1")));
        assertTrue(predicate.test(new PersonBuilder().withProjects("Project1", "Project2").build()));

        // Multiple keywords
        predicate = withResolvedFields(new PersonContainsKeywordsPredicate()
                .setProjectKeywords(Arrays.asList("Project1", "Project2")));
        assertTrue(predicate.test(new PersonBuilder().withProjects("Project1").build()));

        // Mixed-case keywords
        predicate = withResolvedFields(new PersonContainsKeywordsPredicate()
                .setProjectKeywords(Arrays.asList("pROjeCT1")));
        assertTrue(predicate.test(new PersonBuilder().withProjects("Project1").build()));
    }

    @Test
    public void test_multipleFieldsContainsKeywords_returnsTrue() {
        PersonContainsKeywordsPredicate predicate = withResolvedFields(new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.singletonList("Alice"))
                .setPhoneKeywords(Collections.singletonList("91234567"))
                .setEmailKeywords(Collections.singletonList("alice@example.com"))
                .setTaskKeywords(Collections.singletonList("Project1:Task1"))
                .setProjectKeywords(Collections.singletonList("Project1")));
        assertTrue(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withTasks("Task1")
                .withProjects("Project1")
                .build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate().setNameKeywords(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone and email, but does not match name
        predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Arrays.asList("12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setPhoneKeywords(Arrays.asList("12345"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("99999").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setEmailKeywords(Arrays.asList("alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("bob@email.com").build()));
    }

    @Test
    public void test_taskDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = withResolvedFields(new PersonContainsKeywordsPredicate()
                .setTaskKeywords(Arrays.asList("Project1:Task3")));
        assertFalse(predicate.test(new PersonBuilder().withTasks("Task2").build()));
    }

    @Test
    public void test_projectDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = withResolvedFields(new PersonContainsKeywordsPredicate()
                .setProjectKeywords(Arrays.asList("Project3")));
        assertFalse(predicate.test(new PersonBuilder().withProjects("Project2").build()));
    }

    @Test
    public void test_multipleFieldsDoesNotContainKeywords_returnsFalse() {
        // Match name but not phone
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.singletonList("Alice"))
                .setPhoneKeywords(Collections.singletonList("91234567"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("99999").build()));

        // Match phone but not name
        predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(Collections.singletonList("Alice"))
                .setPhoneKeywords(Collections.singletonList("91234567"));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").withPhone("91234567").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate()
                .setNameKeywords(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName() + "{nameKeywords=" + keywords
                + ", phoneKeywords=null, emailKeywords=null, taskKeywords=null, projectKeywords=null}";
        assertEquals(expected, predicate.toString());
    }

    private PersonContainsKeywordsPredicate withResolvedFields(PersonContainsKeywordsPredicate predicate) {
        return predicate
                .setTaskFieldMapper(person -> resolveTaskFields(person, SAMPLE_PROJECTS))
                .setProjectFieldMapper(person -> resolveProjectFields(person, SAMPLE_PROJECTS));
    }

    private List<String> resolveTaskFields(Person person, List<Project> projects) {
        List<String> fields = new java.util.ArrayList<>();
        for (PersonTask personTask : person.getTasks()) {
            int projectIndex = personTask.getProjectIndex();
            if (projectIndex < 0 || projectIndex >= projects.size()) {
                continue;
            }
            Project project = projects.get(projectIndex);
            List<Task> tasks = project.getTasks();
            int taskIndex = personTask.getTaskIndex();
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                continue;
            }
            Task task = tasks.get(taskIndex);
            String projectTitle = project.title;
            String description = task.description;
            fields.add(projectTitle + " " + description + " " + projectTitle + ":" + description);
        }
        return fields;
    }

    private List<String> resolveProjectFields(Person person, List<Project> projects) {
        List<String> fields = new java.util.ArrayList<>();
        for (PersonProject personProject : person.getProjects()) {
            int projectIndex = personProject.getProjectIndex();
            if (projectIndex < 0 || projectIndex >= projects.size()) {
                continue;
            }
            fields.add(projects.get(projectIndex).title);
        }
        return fields;
    }
}
