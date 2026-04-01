package seedu.taskforge.model.person;

import java.util.Objects;

import seedu.taskforge.commons.util.ToStringBuilder;

/**
 * Represents a task assigned to a person by reference.
 * The class references a task by storing its index within the project
 * it belongs to and the index of that project.
 */
public class PersonTask {
    private final int projectIndex;
    private final int taskIndex;

    public PersonTask(int projectIndex, int taskIndex) {
        if (projectIndex < 0 || taskIndex < 0) {
            throw new IllegalArgumentException("Project index and task index must be non-negative");
        }
        this.projectIndex = projectIndex;
        this.taskIndex = taskIndex;
    }

    public int getProjectIndex() {
        return projectIndex;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonTask)) {
            return false;
        }

        PersonTask otherPersonTask = (PersonTask) other;
        return projectIndex == otherPersonTask.projectIndex
                && taskIndex == otherPersonTask.taskIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectIndex, taskIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("projectIndex", projectIndex)
                .add("taskIndex", taskIndex)
                .toString();
    }
}
