package it.mcella.jcr.oak.upgrade.repository.firstversion.retrieve;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.Node;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.NodeVersion;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.JcrFileVersionNode;
import it.mcella.jcr.oak.upgrade.repository.firstversion.node.file.OakFileVersionNode;

import java.util.Collections;
import java.util.List;

public class NodeVersionRetrieve {

    private final JcrRepository jcrRepository;

    public NodeVersionRetrieve(JcrRepository jcrRepository) {
        this.jcrRepository = jcrRepository;
    }

    public List<NodeVersion> retrieveNodeVersions(Node node) throws NodeVersionRetrieveException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            JcrNode jcrNode = jcrSession.getNodeById(node.getJcrNodeId());
            if (jcrNode.hasType(JcrFileNode.NODE_TYPE)) {
                JcrFileVersionNode jcrFileVersionNode = new OakFileVersionNode(jcrNode);
                return jcrFileVersionNode.getVersions();
            }
            return Collections.emptyList();
        } catch (Exception e) {
            throw new NodeVersionRetrieveException("Cannot retrieve node versions", e);
        }
    }

}
