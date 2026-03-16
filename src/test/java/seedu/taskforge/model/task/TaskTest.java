package seedu.taskforge.model.task;

import static seedu.taskforge.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(null));
    }

    @Test
    public void constructor_invalidTaskDescription_throwsIllegalArgumentException() {
        String invalidTaskDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Task(invalidTaskDescription));
    }

    @Test
    public void isValidTaskDescription() {
        // null task name
        assertThrows(NullPointerException.class, () -> Task.isValidTaskDescription(null));
    }

}
