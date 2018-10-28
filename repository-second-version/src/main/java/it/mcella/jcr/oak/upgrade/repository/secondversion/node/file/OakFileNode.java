package it.mcella.jcr.oak.upgrade.repository.secondversion.node.file;

import it.mcella.jcr.oak.upgrade.repository.JcrBinary;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrNodePath;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import org.apache.jackrabbit.JcrConstants;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class OakFileNode implements JcrFileNode {

    private static final String DEFAULT_ENCODING = "";

    protected final JcrNode jcrNode;

    public OakFileNode(JcrNode jcrNode) {
        this.jcrNode = jcrNode;
    }

    @Override
    public void setSystem(boolean system) throws RepositoryException {
        jcrNode.setProperty(JcrFileNode.SYSTEM_PROPERTY_NAME, system);
    }

    @Override
    public void setDescription(String description) throws RepositoryException {
        jcrNode.setProperty(JcrFileNode.DESCRIPTION_PROPERTY_NAME, description);
    }

    @Override
    public JcrNode createContent(JcrSession jcrSession, Path file, InputStream inputStream) throws RepositoryException, IOException {
        JcrNode content = createContentNode();
        String mime = Files.probeContentType(file);
        content.setProperty(JcrConstants.JCR_MIMETYPE, mime);
        content.setProperty(JcrConstants.JCR_ENCODING, getEncoding(mime));
        JcrBinary binary = jcrSession.createBinary(inputStream);
        content.setProperty(JcrConstants.JCR_DATA, binary);
        binary.dispose();
        return content;
    }

    @Override
    public FileNode toNode() throws RepositoryException {
        JcrNodeId jcrNodeId = jcrNode.getNodeId();
        JcrNodePath jcrNodePath = jcrNode.getNodePath();
        String name = jcrNode.getName();
        boolean system = jcrNode.getProperty(SYSTEM_PROPERTY_NAME).getBooleanValue();
        String description = jcrNode.getProperty(JcrFileNode.DESCRIPTION_PROPERTY_NAME).getStringValue();
        JcrNode jcrContentNode = jcrNode.getNode(JcrConstants.JCR_CONTENT);
        String mimeType = jcrContentNode.getProperty(JcrConstants.JCR_MIMETYPE).getStringValue();
        return new FileNode(jcrNodeId, jcrNodePath, name, system, description, mimeType);
    }

    private JcrNode createContentNode() throws RepositoryException {
        return jcrNode.hasNode(JcrConstants.JCR_CONTENT) ?
                jcrNode.getNode(JcrConstants.JCR_CONTENT) :
                jcrNode.addNodeWithNameNotEscaped(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
    }

    private String getEncoding(String mime) {
        if (mime == null || mime.trim().isEmpty()) {
            return DEFAULT_ENCODING;
        }
        String[] parameters = mime.split(";");
        for (String parameter : parameters) {
            int equals = parameter.indexOf("=");
            if (equals != -1) {
                String key = parameter.substring(0, equals);
                if ("charset".equalsIgnoreCase(key.trim())) {
                    return parameter.substring(equals + 1).trim();
                }
            }
        }
        return DEFAULT_ENCODING;
    }

}
