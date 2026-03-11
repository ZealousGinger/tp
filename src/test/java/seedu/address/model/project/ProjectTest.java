package seedu.address.model.project;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ProjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Project(null));
    }

    @Test
    public void constructor_invalidProjectName_throwsIllegalArgumentException() {
        String invalidProjectNameTooShort = "";
        String invalidProjectNameTooLong = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnqrstuvwxyzabcdefghijklm";
        assertThrows(IllegalArgumentException.class, () -> new Project(invalidProjectNameTooShort));
        assertThrows(IllegalArgumentException.class, () -> new Project(invalidProjectNameTooLong));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Project.isValidProjectName(null));
    }

}
