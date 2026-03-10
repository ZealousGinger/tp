package seedu.address.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.DeleteTaskCommand.DeleteTaskDescriptor;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building DeleteTaskDescriptorBuilder objects.
 */
public class DeleteTaskDescriptorBuilder {

    private DeleteTaskDescriptor descriptor;

    public DeleteTaskDescriptorBuilder() {
        descriptor = new DeleteTaskDescriptor();
    }

    public DeleteTaskDescriptorBuilder(DeleteTaskDescriptor descriptor) {
        this.descriptor = new DeleteTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code DeleteTaskDescriptorBuilder} with fields containing {@code person}'s details
     */
    public DeleteTaskDescriptorBuilder(Person person) {
        descriptor = new DeleteTaskDescriptor();
//        descriptor.setTasks(person.getTasks());
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code DeleteTaskDescriptorBuilder}
     * that we are building.
     */
    public DeleteTaskDescriptorBuilder withTasks(String... indexes) {
        List<Index> taskIndexSet = Stream.of(indexes).map(s -> Index.fromOneBased(Integer.parseInt(s))).collect(Collectors.toList());
        descriptor.setTasksIndexes(taskIndexSet);
        return this;
    }

    public DeleteTaskDescriptor build() {
        return descriptor;
    }
}
