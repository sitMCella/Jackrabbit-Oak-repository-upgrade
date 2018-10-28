package it.mcella.jcr.oak.upgrade.repository.firstversion.retrieve.file;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrBinary;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileRetrieve {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRetrieve.class);

    private final JcrRepository jcrRepository;

    public FileRetrieve(JcrRepository jcrRepository) {
        this.jcrRepository = jcrRepository;
    }

    public Path retrieveFrom(JcrNodeId jcrNodeId) throws FileRetrieveException {
        InputStream inputStream = null;
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            JcrNode file = jcrSession.getNodeById(jcrNodeId);
            JcrBinary binary = file.getBinary();
            Path image = Files.createTempFile("file", ".jpg");
            inputStream = binary.getInputStream();
            Files.copy(inputStream, image, StandardCopyOption.REPLACE_EXISTING);
//            binary.dispose();
            return image;
        } catch (Exception e) {
            throw new FileRetrieveException(jcrNodeId, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.warn("Cannot close binary input stream", e);
                }
            }
        }
    }

}
