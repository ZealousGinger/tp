package seedu.taskforge.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.project.AssignProjectCommand.AssignProjectDescriptor;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.project.Project;

/**
 * A builder class for constructing an {@code AssignProjectDescriptor} object. Provides methods to
 * configure the descriptor with either a list of projects or project indexes, ensuring mutual
 * exclusivity between the two fields. Supports initialization from a {@code Person} object or an
 * existing descriptor.
 */
public class AssignProjectDescriptorBuilder {
    private final AssignProjectDescriptor descriptor;

    public AssignProjectDescriptorBuilder() {
        descriptor = new AssignProjectDescriptor();
    }

    public AssignProjectDescriptorBuilder(AssignProjectDescriptor descriptor) {
        this.descriptor = new AssignProjectDescriptor(descriptor);
    }

    /**
     * Returns an {@code AssignProjectDescriptor} with fields containing {@code person}'s details
     */
    public AssignProjectDescriptorBuilder(Person person) {
        descriptor = new AssignProjectDescriptor();
    }

    /**
     * Parses the {@code projects} into a {@code List<Project>} and set it to the {@code AssignProjectDescriptor}
     * that we are building.
     */
    public AssignProjectDescriptorBuilder withProjects(String... projects) {
        List<Project> projectSet = Stream.of(projects).map(Project::new).collect(Collectors.toList());
        descriptor.setProjects(projectSet);
        return this;
    }

    /**
     * Parses the {@code indexes} into a {@code List<Index>} and set it to the {@code AssignProjectDescriptor}
     * that we are building.
     */
    public AssignProjectDescriptorBuilder withProjectIndexes(String... indexes) {
        List<Index> projectIndexSet = new ArrayList<>();
        for (String index : indexes) {
            projectIndexSet.add(Index.fromOneBased(Integer.parseInt(index)));
        }
        descriptor.setProjectIndexes(projectIndexSet);
        return this;
    }

    public AssignProjectDescriptor build() {
        return descriptor;
    }
}
