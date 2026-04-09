package seedu.taskforge.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalTaskForge;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.taskforge.commons.core.GuiSettings;
import seedu.taskforge.model.ReadOnlyTaskForge;
import seedu.taskforge.model.TaskForge;
import seedu.taskforge.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonTaskForgeStorage taskForgeStorage = new JsonTaskForgeStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(taskForgeStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void taskForgeReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonTaskForgeStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonTaskForgeStorageTest} class.
         */
        TaskForge original = getTypicalTaskForge();
        storageManager.saveTaskForge(original);
        ReadOnlyTaskForge retrieved = storageManager.readTaskForge().get();
        assertEquals(original, new TaskForge(retrieved));
    }

    @Test
    public void getTaskForgeFilePath() {
        assertNotNull(storageManager.getTaskForgeFilePath());
    }

}
