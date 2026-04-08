package seedu.taskforge.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.model.person.Person;

/**
 * A utility class to help with building AssignTaskDescriptor objects.
 */
public class AssignTaskDescriptorBuilder {

    private AssignTaskDescriptor descriptor;

    public AssignTaskDescriptorBuilder() {
        descriptor = new AssignTaskDescriptor();
    }

    public AssignTaskDescriptorBuilder(AssignTaskDescriptor descriptor) {
        this.descriptor = new AssignTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public AssignTaskDescriptorBuilder(Person person) {
        descriptor = new AssignTaskDescriptor();
    }

    /**
     * Sets the task indexes to the {@code AssignTaskDescriptor} being built.
     * The provided indexes are parsed as one-based integers and converted to {@code Index} objects.
     *
     * @param indexes One-based string representations of task indexes to be assigned.
     * @return The {@code AssignTaskDescriptorBuilder} instance with the updated task indexes.
     */
    public AssignTaskDescriptorBuilder withTaskIndexes(String... indexes) {
        List<Index> taskIndexSet = Stream.of(indexes)
                .map(s -> Index.fromOneBased(Integer.parseInt(s)))
                .collect(Collectors.toList());
        descriptor.setTasksIndexes(taskIndexSet);
        return this;
    }

    public AssignTaskDescriptor build() {
        return descriptor;
    }
}
