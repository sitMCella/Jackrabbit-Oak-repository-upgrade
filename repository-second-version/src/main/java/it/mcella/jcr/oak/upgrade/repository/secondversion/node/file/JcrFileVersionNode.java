package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.NodeVersion;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface JcrFileVersionNode {

    boolean isVersioned() throws RepositoryException;

    FileVersionNode firstVersion() throws RepositoryException;

    FileVersionNode newVersion(boolean system, String versionDescription, JcrSession jcrSession, Path file) throws RepositoryException, IOException;

    List<NodeVersion> getVersions() throws RepositoryException;

}
