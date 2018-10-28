package it.mcella.jcr.oak.upgrade.apprun.secondversion.action;

import it.mcella.jcr.oak.upgrade.commons.ConsoleWriter;
import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.Node;
import it.mcella.jcr.oak.upgrade.repository.secondversion.node.NodeVersion;
import it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve.NodeRetrieve;
import it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve.NodeRetrieveException;
import it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve.NodeVersionRetrieve;
import it.mcella.jcr.oak.upgrade.repository.secondversion.retrieve.NodeVersionRetrieveException;

import java.util.Collections;
import java.util.List;

public class ListNodes implements Action {

    private final NodeRetrieve nodeRetrieve;
    private final NodeVersionRetrieve nodeVersionRetrieve;
    private final ConsoleWriter consoleWriter;

    public ListNodes(NodeRetrieve nodeRetrieve, NodeVersionRetrieve nodeVersionRetrieve, ConsoleWriter consoleWriter) {
        this.nodeRetrieve = nodeRetrieve;
        this.nodeVersionRetrieve = nodeVersionRetrieve;
        this.consoleWriter = consoleWriter;
    }

    @Override
    public ActionType getType() {
        return ActionType.LIST;
    }

    @Override
    public void execute() throws ActionException {
        try {
            Node rootNode = nodeRetrieve.retrieveRootNode();
            printNodesRecursive(Collections.singletonList(rootNode), nodeRetrieve, nodeVersionRetrieve);
        } catch (NodeRetrieveException e) {
            throw new ActionException("Cannot retrieve nodes", e);
        }
    }

    private void printNodesRecursive(List<Node> nodes, NodeRetrieve nodeRetrieve, NodeVersionRetrieve nodeVersionRetrieve) throws ActionException {
        try {
            for (Node node : nodes) {
                consoleWriter.println(node);
                List<NodeVersion> versionNodes = nodeVersionRetrieve.retrieveNodeVersions(node);
                versionNodes.stream().forEach(consoleWriter::println);
                List<JcrNodeId> jcrChildNodeIds = node.getChildNodeIds();
                List<Node> childNodes = nodeRetrieve.retrieveNodesFrom(jcrChildNodeIds);
                printNodesRecursive(childNodes, nodeRetrieve, nodeVersionRetrieve);
            }
        } catch (NodeRetrieveException | NodeVersionRetrieveException e) {
            throw new ActionException("Cannot retrieve nodes", e);
        }
    }

}
