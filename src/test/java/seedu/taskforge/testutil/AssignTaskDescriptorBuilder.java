package seedu.taskforge.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.commons.core.index.Index;
import seedu.taskforge.logic.commands.task.AssignTaskCommand.AssignTaskDescriptor;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.task.Task;

/**
 *
 */
public class AssignTaskDescriptorBuilder {

    private final AssignTaskDescriptor descriptor;

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
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public AssignTaskDescriptorBuilder withTasks(String... tasks) {
        List<Task> taskSet = Stream.of(tasks).map(Task::new).collect(Collectors.toList());
        descriptor.setTasks(taskSet);
        return this;
    }

    /**
     * Parses the {@code indexes} into a {@code List<Index>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public AssignTaskDescriptorBuilder withTaskIndexes(String... indexes) {
        List<Index> taskIndexSet = new ArrayList<>();
        for (String index : indexes) {
            taskIndexSet.add(Index.fromOneBased(Integer.parseInt(index)));
        }
        descriptor.setTaskIndexes(taskIndexSet);
        return this;
    }

    public AssignTaskDescriptor build() {
        return descriptor;
    }
}
