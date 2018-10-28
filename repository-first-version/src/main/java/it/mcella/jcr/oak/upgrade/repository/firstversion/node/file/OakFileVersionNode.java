package it.mcella.jcr.oak.upgrade.repository.firstversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeVersion;
import it.mcella.jcr.oak.upgrade.repository.JcrProperty;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.NodeVersion;
import org.apache.jackrabbit.JcrConstants;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OakFileVersionNode extends OakFileNode implements JcrFileVersionNode {

    static final String VERSION_DESCRIPTION_PROPERTY_NAME = "app:versionDescription";

    public OakFileVersionNode(JcrNode jcrNode) {
        super(jcrNode);
    }

    @Override
    public boolean isVersioned() throws RepositoryException {
        if (jcrNode.hasProperty(JcrConstants.JCR_MIXINTYPES)) {
            JcrProperty jcrProperty = jcrNode.getProperty(JcrConstants.JCR_MIXINTYPES);
            return jcrProperty.contains(JcrConstants.MIX_VERSIONABLE);
        }
        return false;
    }

    @Override
    public FileVersionNode firstVersion() throws RepositoryException {
        jcrNode.addMixin(JcrConstants.MIX_VERSIONABLE);
        jcrNode.setProperty(VERSION_DESCRIPTION_PROPERTY_NAME, "");
        jcrNode.save();
        jcrNode.checkout();
        JcrNodeVersion jcrNodeVersion = jcrNode.checkin();
        jcrNodeVersion.addVersionLabel();
        JcrNodeId jcrNodeId = jcrNodeVersion.getJcrNodeId();
        long versionNumber = jcrNodeVersion.getVersionNumber();
        return new FileVersionNode(toNode(), jcrNodeId, versionNumber);
    }

    @Override
    public FileVersionNode newVersion(String versionDescription, JcrSession jcrSession, Path file) throws RepositoryException, IOException {
        try (InputStream inputStream = Files.newInputStream(file)) {
            jcrNode.checkout();
            jcrNode.setProperty(VERSION_DESCRIPTION_PROPERTY_NAME, versionDescription);
            createContent(jcrSession, file, inputStream);
            jcrNode.save();
            JcrNodeVersion jcrNodeVersion = jcrNode.checkin();
            jcrNodeVersion.addVersionLabel();
            JcrNodeId jcrNodeId = jcrNodeVersion.getJcrNodeId();
            long versionNumber = jcrNodeVersion.getVersionNumber();
            return new FileVersionNode(toNode(), jcrNodeId, versionNumber, versionDescription);
        }
    }

    @Override
    public List<NodeVersion> getVersions() throws RepositoryException {
        if (!isVersioned()) {
            return Collections.emptyList();
        }
        List<NodeVersion> fileVersionNodes = new ArrayList<>();
        List<JcrNodeVersion> jcrNodeVersions = jcrNode.getVersions();
        for (JcrNodeVersion jcrNodeVersion : jcrNodeVersions) {
            JcrNodeId jcrNodeId = jcrNodeVersion.getJcrNodeId();
            long versionNumber = jcrNodeVersion.getVersionNumber();
            String versionDescription = jcrNodeVersion.getProperty(VERSION_DESCRIPTION_PROPERTY_NAME).getStringValue();
            fileVersionNodes.add(new FileVersionNode(toNode(), jcrNodeId, versionNumber, versionDescription));
        }
        Comparator<NodeVersion> comparator = (NodeVersion firstFileVersionNode, NodeVersion secondFileVersionNode) -> {
            return Long.compare(((FileVersionNode) firstFileVersionNode).getVersionNumber(), ((FileVersionNode) secondFileVersionNode).getVersionNumber());
        };
        Collections.sort(fileVersionNodes, comparator);
        return fileVersionNodes;
    }

}
