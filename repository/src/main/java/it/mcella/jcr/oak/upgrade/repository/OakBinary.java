package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.Binary;
import javax.jcr.RepositoryException;
import java.io.InputStream;

public class OakBinary implements JcrBinary {

    private final Binary binary;

    public OakBinary(Binary binary) {
        this.binary = binary;
    }

    @Override
    public Binary getBinary() {
        return binary;
    }

    @Override
    public InputStream getInputStream() throws RepositoryException {
        return binary.getStream();
    }

    @Override
    public void dispose() {
        binary.dispose();
    }

}
