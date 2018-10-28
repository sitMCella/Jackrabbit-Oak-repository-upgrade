package it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve;

import it.mcella.jcr.oak.upgrade.repository.AdministratorCredentials;
import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.JcrRepository;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.Node;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.JcrFileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.file.OakFileNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.JcrFolderNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.folder.OakFolderNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.root.JcrRootNode;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.root.OakRootNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NodeRetrieve {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeRetrieve.class);

    private final JcrRepository jcrRepository;

    public NodeRetrieve(JcrRepository jcrRepository) {
        this.jcrRepository = jcrRepository;
    }

    public Node retrieveRootNode() throws NodeRetrieveException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            JcrNode jcrRootNode = jcrSession.getRootNode();
            return retrieveNode(jcrRootNode).get();
        } catch (Exception e) {
            throw new NodeRetrieveException("Cannot retrieve root node", e);
        }
    }

    public List<Node> retrieveNodesFrom(List<JcrNodeId> jcrNodeIds) throws NodeRetrieveException {
        try (JcrSession jcrSession = jcrRepository.login(AdministratorCredentials.create())) {
            return retrieveNodes(jcrSession.getNodesById(jcrNodeIds));
        } catch (Exception e) {
            throw new NodeRetrieveException("Cannot retrieve nodes", e);
        }
    }

    private List<Node> retrieveNodes(List<JcrNode> jcrNodes) {
        return jcrNodes.stream()
                .map(this::retrieveNode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Node> retrieveNode(JcrNode jcrNode) {
        try {
            if (jcrNode.hasType(JcrRootNode.NODE_TYPE)) {
                return Optional.of(new OakRootNode(jcrNode).toNode());
            }
            if (jcrNode.hasType(JcrFileNode.NODE_TYPE)) {
                return Optional.of(new OakFileNode(jcrNode).toNode());
            }
            if (jcrNode.hasType(JcrFolderNode.NODE_TYPE)) {
                return Optional.of(new OakFolderNode(jcrNode).toNode());
            }
        } catch (RepositoryException e) {
            LOGGER.debug(String.format("Cannot retrieve node %s", jcrNode), e);
        }
        return Optional.empty();
    }

}
