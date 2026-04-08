package seedu.taskforge.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.taskforge.commons.exceptions.DataLoadingException;
import seedu.taskforge.model.ReadOnlyTaskForge;

/**
 * Represents a storage for {@link seedu.taskforge.model.TaskForge}.
 */
public interface TaskForgeStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTaskForgeFilePath();

    /**
     * Returns TaskForge data as a {@link ReadOnlyTaskForge}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTaskForge> readTaskForge() throws DataLoadingException;

    /**
     * @see #getTaskForgeFilePath()
     */
    Optional<ReadOnlyTaskForge> readTaskForge(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTaskForge} to the storage.
     * @param taskForge cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskForge(ReadOnlyTaskForge taskForge) throws IOException;

    /**
     * @see #saveTaskForge(ReadOnlyTaskForge)
     */
    void saveTaskForge(ReadOnlyTaskForge taskForge, Path filePath) throws IOException;

}
