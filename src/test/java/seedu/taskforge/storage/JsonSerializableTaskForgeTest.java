package seedu.taskforge.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.taskforge.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.taskforge.commons.exceptions.IllegalValueException;
import seedu.taskforge.commons.util.JsonUtil;
import seedu.taskforge.model.TaskForge;
import seedu.taskforge.testutil.TypicalPersons;

public class JsonSerializableTaskForgeTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTaskForgeTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsTaskForge.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonTaskForge.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonTaskForge.json");
    private static final Path DUPLICATE_PROJECT_FILE = TEST_DATA_FOLDER.resolve("duplicateProjectTaskForge.json");
    private static final Path PERSON_WITH_MISSING_PROJECT_FILE =
            TEST_DATA_FOLDER.resolve("personWithMissingProjectTaskForge.json");
    private static final Path PERSON_WITH_MISSING_TASK_IN_PROJECTS_FILE =
            TEST_DATA_FOLDER.resolve("personWithMissingTaskInProjectsTaskForge.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableTaskForge dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableTaskForge.class).get();
        TaskForge taskForgeFromFile = dataFromFile.toModelType();
        TaskForge typicalPersonsTaskForge = TypicalPersons.getTypicalTaskForge();
        assertEquals(taskForgeFromFile, typicalPersonsTaskForge);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskForge dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableTaskForge.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskForge dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableTaskForge.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTaskForge.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateProjects_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskForge dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PROJECT_FILE,
            JsonSerializableTaskForge.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTaskForge.MESSAGE_DUPLICATE_PROJECT,
            dataFromFile::toModelType);
    }

    @Test
    public void toModelType_personWithMissingProject_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskForge dataFromFile = JsonUtil.readJsonFile(PERSON_WITH_MISSING_PROJECT_FILE,
                JsonSerializableTaskForge.class).get();
        assertThrows(IllegalValueException.class,
                String.format(JsonSerializableTaskForge.MESSAGE_PERSON_PROJECT_NOT_IN_PROJECT_LIST,
                        "John Doe", 1),
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_personWithMissingTaskInProjects_throwsIllegalValueException() throws Exception {
        JsonSerializableTaskForge dataFromFile = JsonUtil.readJsonFile(PERSON_WITH_MISSING_TASK_IN_PROJECTS_FILE,
                JsonSerializableTaskForge.class).get();
        assertThrows(IllegalValueException.class,
                String.format(JsonSerializableTaskForge.MESSAGE_PERSON_TASK_NOT_IN_ASSIGNED_PROJECTS,
                        "John Doe", "[invalid-task-index]"),
                dataFromFile::toModelType);
    }

    @Test
    public void constructor_nullProjects_success() throws Exception {
        JsonSerializableTaskForge serializableTaskForge = new JsonSerializableTaskForge(
            Collections.emptyList(), null);

        assertEquals(new TaskForge(), serializableTaskForge.toModelType());
    }
}
