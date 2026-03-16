package seedu.taskforge.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.taskforge.logic.commands.task.AddTaskCommand.AddTaskDescriptor;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.task.Task;

/**
 * A utility class to help with building AddTaskDescriptor objects.
 */
public class AddTaskDescriptorBuilder {

    private AddTaskDescriptor descriptor;

    public AddTaskDescriptorBuilder() {
        descriptor = new AddTaskDescriptor();
    }

    public AddTaskDescriptorBuilder(AddTaskDescriptor descriptor) {
        this.descriptor = new AddTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public AddTaskDescriptorBuilder(Person person) {
        descriptor = new AddTaskDescriptor();
        descriptor.setTasks(person.getTasks());
    }

    /**
     * Parses the {@code tasks} into a {@code List<Task>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public AddTaskDescriptorBuilder withTasks(String... tasks) {
        List<Task> taskSet = Stream.of(tasks).map(Task::new).collect(Collectors.toList());
        descriptor.setTasks(taskSet);
        return this;
    }

    public AddTaskDescriptor build() {
        return descriptor;
    }
}
