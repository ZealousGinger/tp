package seedu.taskforge.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.taskforge.model.ReadOnlyAddressBook;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonProject;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label availability;
    @FXML
    private Circle availabilityIndicator;
    @FXML
    private FlowPane projects;
    @FXML
    private FlowPane tasks;

    /**
     * Creates a {@code PersonCard} with the given {@code Person}, index, and {@code ReadOnlyAddressBook} to display.
     */
    public PersonCard(Person person, int displayedIndex, ReadOnlyAddressBook addressBook) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);

        String availabilityString = person.getAvailability().toString();
        availability.setText(availabilityString + ".  Workload:  " + person.getWorkload());
        availabilityIndicator.getStyleClass().add(availabilityString);

        List<PersonProject> personProjectList = person.getProjects();
        List<Task> taskList = person.getTasks();
        List<Project> globalProjectList = new ArrayList<>(addressBook.getProjectList());
        
        IntStream.range(0, personProjectList.size())
                .forEach(i -> {
                    PersonProject personProject = personProjectList.get(i);
                    int projectIndex = personProject.getProjectIndex();
                    String projectTitle = "";
                    if (projectIndex >= 0 && projectIndex < globalProjectList.size()) {
                        projectTitle = globalProjectList.get(projectIndex).title;
                    }
                    projects.getChildren().add(
                            new Label((i + 1) + ". " + projectTitle)
                    );
                });
        IntStream.range(0, taskList.size())
                .forEach(i -> tasks.getChildren().add(
                        new Label((i + 1) + ". " + (taskList.get(i).getStatus() ? "[X] " : "[ ] ")
                                + taskList.get(i).description)
                ));
    }
}
