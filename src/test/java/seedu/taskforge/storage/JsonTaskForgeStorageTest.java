package seedu.taskforge.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskforge.testutil.Assert.assertThrows;
import static seedu.taskforge.testutil.TypicalPersons.ALICE;
import static seedu.taskforge.testutil.TypicalPersons.HOON;
import static seedu.taskforge.testutil.TypicalPersons.IDA;
import static seedu.taskforge.testutil.TypicalPersons.getTypicalTaskForge;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.taskforge.commons.exceptions.DataLoadingException;
import seedu.taskforge.model.ReadOnlyTaskForge;
import seedu.taskforge.model.TaskForge;
import seedu.taskforge.model.person.Person;
import seedu.taskforge.model.person.PersonTask;
import seedu.taskforge.model.project.Project;
import seedu.taskforge.model.task.Task;

public class JsonTaskForgeStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTaskForgeStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTaskForge_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTaskForge(null));
    }

    private java.util.Optional<ReadOnlyTaskForge> readTaskForge(String filePath) throws Exception {
        return new JsonTaskForgeStorage(Paths.get(filePath)).readTaskForge(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskForge("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readTaskForge("notJsonFormatTaskForge.json"));
    }

    @Test
    public void readTaskForge_invalidPersonTaskForge_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTaskForge("invalidPersonTaskForge.json"));
    }

    @Test
    public void readTaskForge_invalidAndValidPersonTaskForge_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTaskForge("invalidAndValidPersonTaskForge.json"));
    }

    @Test
    public void readAndSaveTaskForge_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTaskForge.json");
        TaskForge original = getTypicalTaskForge();
        JsonTaskForgeStorage jsonTaskForgeStorage = new JsonTaskForgeStorage(filePath);

        // Save in new file and read back
        jsonTaskForgeStorage.saveTaskForge(original, filePath);
        ReadOnlyTaskForge readBack = jsonTaskForgeStorage.readTaskForge(filePath).get();
        assertEquals(original, new TaskForge(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonTaskForgeStorage.saveTaskForge(original, filePath);
        readBack = jsonTaskForgeStorage.readTaskForge(filePath).get();
        assertEquals(original, new TaskForge(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonTaskForgeStorage.saveTaskForge(original); // file path not specified
        readBack = jsonTaskForgeStorage.readTaskForge().get(); // file path not specified
        assertEquals(original, new TaskForge(readBack));

    }

    @Test
    public void saveTaskForge_nullTaskForge_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskForge(null, "SomeFile.json"));
    }

    /**
     * Saves {@code TaskForge} at the specified {@code filePath}.
     */
    private void saveTaskForge(ReadOnlyTaskForge taskForge, String filePath) {
        try {
            new JsonTaskForgeStorage(Paths.get(filePath))
                    .saveTaskForge(taskForge, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaskForge_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskForge(new TaskForge(), null));
    }

    @Test
    public void readAndSaveTaskForge_markedTaskStatus_persists() throws Exception {
        Path filePath = testFolder.resolve("TempTaskForgeWithStatus.json");
        TaskForge original = getTypicalTaskForge();

        Person firstPerson = original.getPersonList().get(0);
        PersonTask firstPersonTask = firstPerson.getTasks().get(0);
        Project firstProject = original.getProjectList().get(firstPersonTask.getProjectIndex());
        Task firstTask = firstProject.getTasks().get(firstPersonTask.getTaskIndex());
        Task doneTask = new Task(firstTask.description, firstTask.getProjectTitle());
        doneTask.setDone();

        List<Task> updatedTasks = new ArrayList<>(firstProject.getTasks());
        updatedTasks.set(firstPersonTask.getTaskIndex(), doneTask);
        original.setProject(firstProject, new Project(firstProject.title, updatedTasks));

        JsonTaskForgeStorage storage = new JsonTaskForgeStorage(filePath);
        storage.saveTaskForge(original, filePath);
        ReadOnlyTaskForge readBack = storage.readTaskForge(filePath).get();

        Person readBackPerson = readBack.getPersonList().get(0);
        PersonTask readBackTaskRef = readBackPerson.getTasks().get(0);
        Task readBackTask = readBack.getProjectList().get(readBackTaskRef.getProjectIndex())
            .getTasks().get(readBackTaskRef.getTaskIndex());
        assertTrue(readBackTask.getStatus());
    }
}
