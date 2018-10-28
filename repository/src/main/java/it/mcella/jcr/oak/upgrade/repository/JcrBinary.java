package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.Binary;
import javax.jcr.RepositoryException;
import java.io.InputStream;

public interface JcrBinary {

    Binary getBinary();

    InputStream getInputStream() throws RepositoryException;

    void dispose();

}
