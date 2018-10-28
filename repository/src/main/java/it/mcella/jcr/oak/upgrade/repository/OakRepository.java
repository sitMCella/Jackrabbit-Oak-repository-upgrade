package it.mcella.jcr.oak.upgrade.repository;

import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.plugins.nodetype.write.InitialContent;
import org.apache.jackrabbit.oak.segment.SegmentNodeStore;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStore;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;
import org.apache.jackrabbit.oak.spi.blob.BlobStore;
import org.apache.jackrabbit.oak.spi.blob.FileBlobStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OakRepository implements JcrRepository, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(OakRepository.class);
    private static final String REPOSITORY = "repository";
    private static final String DATASTORE = "datastore";
    private static final String LOCK_FILE_NAME = "repo.lock";
    private static final String INITIALIZATION_COMPLETED = "Oak repository initialization complete";
    private static final String SHUTDOWN_COMPLETED = "Oak repository shutdown complete";

    private final Path oakFileSystemFolder;
    private final JcrCustomNodeTypeDefinition jcrCustomNodeTypeDefinition;

    private FileStore repositoryFileStore;
    private SegmentNodeStore segmentNodeStore;
    private Repository repository;

    public OakRepository(Path oakFileSystemFolder, JcrCustomNodeTypeDefinition jcrCustomNodeTypeDefinition) {
        this.oakFileSystemFolder = oakFileSystemFolder;
        this.jcrCustomNodeTypeDefinition = jcrCustomNodeTypeDefinition;
    }

    public void init() throws RepositoryInitializationException {
        try {
            Path repositoryFolder = oakFileSystemFolder.resolve(REPOSITORY);
            Path dataStoreFolder = oakFileSystemFolder.resolve(DATASTORE);
            removeLockFile(repositoryFolder);

            BlobStore blobStore = new FileBlobStore(dataStoreFolder.toAbsolutePath().toString());
            repositoryFileStore = FileStoreBuilder.fileStoreBuilder(repositoryFolder.toFile())
                    .withBlobStore(blobStore).build();
            segmentNodeStore = SegmentNodeStoreBuilders.builder(repositoryFileStore).build();
            Jcr jcr = new Jcr(segmentNodeStore)
                    .with(new InitialContent());
            repository = jcr.createRepository();
            jcrCustomNodeTypeDefinition.load(repository);
            LOGGER.info(INITIALIZATION_COMPLETED);
        } catch (IOException | InvalidFileStoreVersionException e) {
            if (repositoryFileStore != null) {
                repositoryFileStore.close();
            }
            throw new RepositoryInitializationException(e);
        }
    }

    @Override
    public JcrSession login(Credentials credentials) throws RepositoryException {
        return new OakSession(repository.login(credentials));
    }

    @Override
    public void close() throws Exception {
        if (repositoryFileStore != null) {
            repositoryFileStore.close();
        }
        if (repository != null && repository instanceof JackrabbitRepository) {
            ((JackrabbitRepository) repository).shutdown();
            LOGGER.info(SHUTDOWN_COMPLETED);
            repository = null;
        }
    }

    private void removeLockFile(Path repositoryFolder) throws IOException {
        Path lockFile = repositoryFolder.resolve(LOCK_FILE_NAME);
        if (Files.exists(lockFile)) {
            Files.delete(lockFile);
        }
    }

}
