package seedu.taskforge.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.taskforge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskforge.model.person.exceptions.InvalidPersonTaskTypeException;
import seedu.taskforge.model.task.exceptions.DuplicateTaskException;
import seedu.taskforge.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks assigned to a person that enforces uniqueness betwen its elements.
 * A task is considered unique by comparing using {@code PersonTask#equals(Object)}.
 */
public class UniquePersonTaskList implements Iterable<PersonTask> {
    private final ObservableList<PersonTask> internalList = FXCollections.observableArrayList();
    private final ObservableList<PersonTask> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(PersonTask toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    public void add(PersonTask toAdd) {
        requireNonNull(toAdd);
        if (!(toAdd instanceof PersonTask)) {
            throw new InvalidPersonTaskTypeException();
        }
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    public void setPersonTask(PersonTask target, PersonTask editedPersonTask) {
        requireAllNonNull(target, editedPersonTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.equals(editedPersonTask) && contains(editedPersonTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, editedPersonTask);
    }

    public void remove(PersonTask toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
    }

    public void setPersonTasks(UniquePersonTaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setPersonTasks(List<PersonTask> personTasks) {
        requireAllNonNull(personTasks);
        if (!personTasksAreUnique(personTasks)) {
            throw new DuplicateTaskException();
        }
        internalList.setAll(personTasks);
    }

    public ObservableList<PersonTask> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<PersonTask> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniquePersonTaskList)) {
            return false;
        }

        UniquePersonTaskList otherUniquePersonTaskList = (UniquePersonTaskList) other;
        return new HashSet<>(internalList).equals(new HashSet<>(otherUniquePersonTaskList.internalList));
    }

    @Override
    public int hashCode() {
        return new HashSet<>(internalList).hashCode();
    }

    private boolean personTasksAreUnique(List<PersonTask> personTasks) {
        for (int i = 0; i < personTasks.size() - 1; i++) {
            for (int j = i + 1; j < personTasks.size(); j++) {
                if (personTasks.get(i).equals(personTasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
