package seedu.taskforge.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.taskforge.commons.core.LogsCenter;
import seedu.taskforge.commons.exceptions.DataLoadingException;
import seedu.taskforge.model.ReadOnlyTaskForge;
import seedu.taskforge.model.ReadOnlyUserPrefs;
import seedu.taskforge.model.UserPrefs;

/**
 * Manages storage of TaskForge data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TaskForgeStorage taskForgeStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TaskForgeStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TaskForgeStorage taskForgeStorage, UserPrefsStorage userPrefsStorage) {
        this.taskForgeStorage = taskForgeStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TaskForge methods ==============================

    @Override
    public Path getTaskForgeFilePath() {
        return taskForgeStorage.getTaskForgeFilePath();
    }

    @Override
    public Optional<ReadOnlyTaskForge> readTaskForge() throws DataLoadingException {
        return readTaskForge(taskForgeStorage.getTaskForgeFilePath());
    }

    @Override
    public Optional<ReadOnlyTaskForge> readTaskForge(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return taskForgeStorage.readTaskForge(filePath);
    }

    @Override
    public void saveTaskForge(ReadOnlyTaskForge taskForge) throws IOException {
        saveTaskForge(taskForge, taskForgeStorage.getTaskForgeFilePath());
    }

    @Override
    public void saveTaskForge(ReadOnlyTaskForge taskForge, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        taskForgeStorage.saveTaskForge(taskForge, filePath);
    }

}
