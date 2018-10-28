package it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit;

import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeTypeDefinitionsReader;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeTypesUnregister;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakNodeTypeDefinition;
import org.apache.jackrabbit.spi.QNodeTypeDefinition;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.NodeTypeManager;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OakNodeTypesUnregister implements JcrNodeTypesUnregister {

    private final JcrNodeTypeDefinitionsReader jcrNodeTypeDefinitionsReader;

    public OakNodeTypesUnregister(JcrNodeTypeDefinitionsReader jcrNodeTypeDefinitionsReader) {
        this.jcrNodeTypeDefinitionsReader = jcrNodeTypeDefinitionsReader;
    }

    @Override
    public void unregister(InputStream namespaceConfiguration, JcrSession jcrSession) throws JcrNodeTypeUnregisterException {
        try {
            List<OakNodeTypeDefinition> nodeTypes = jcrNodeTypeDefinitionsReader.readFrom(namespaceConfiguration);
            unregister(nodeTypes, jcrSession);
        } catch (JcrNodeTypeDefinitionsException e) {
            throw new JcrNodeTypeUnregisterException(e);
        }
    }

    private void unregister(List<OakNodeTypeDefinition> oakNodeTypeDefinitions, JcrSession jcrSession) throws JcrNodeTypeUnregisterException {
        try {
            Session session = jcrSession.getSession();
            NodeTypeManager nodeTypeManager = session.getWorkspace().getNodeTypeManager();
            ArrayDeque<QNodeTypeDefinition> qNodeTypeDefinitions = getNodeTypeDefinitionsInReverseOrder(oakNodeTypeDefinitions);
            Iterator<QNodeTypeDefinition> qNodeTypeDefinitionIterator = qNodeTypeDefinitions.iterator();
            while (qNodeTypeDefinitionIterator.hasNext()) {
                QNodeTypeDefinition nodeType = qNodeTypeDefinitionIterator.next();
                unregisterNodeType(nodeType, nodeTypeManager);
            }
        } catch (RepositoryException e) {
            throw new JcrNodeTypeUnregisterException(e);
        }
    }

    private void unregisterNodeType(QNodeTypeDefinition nodeType, NodeTypeManager nodeTypeManager) throws JcrNodeTypeUnregisterException {
        try {
            nodeTypeManager.unregisterNodeType(nodeType.getName().toString());
        } catch (RepositoryException e) {
            throw new JcrNodeTypeUnregisterException(e);
        }
    }

    private ArrayDeque<QNodeTypeDefinition> getNodeTypeDefinitionsInReverseOrder(List<OakNodeTypeDefinition> oakNodeTypeDefinitions) {
        List<QNodeTypeDefinition> qNodeTypeDefinitions = getQNodeTypeDefinitions(oakNodeTypeDefinitions);
        return qNodeTypeDefinitions.stream()
                .collect(Collector.of(
                        ArrayDeque::new,
                        (deque, qNodeTypeDefinition) -> deque.addFirst(qNodeTypeDefinition),
                        (orderedQNodeTypeDefinition, deque) -> {
                            deque.addAll(orderedQNodeTypeDefinition);
                            return deque;
                        }));
    }

    private List<QNodeTypeDefinition> getQNodeTypeDefinitions(List<OakNodeTypeDefinition> oakNodeTypeDefinitions) {
        return oakNodeTypeDefinitions.stream()
                .map(oakNodeTypeDefinition -> oakNodeTypeDefinition.getQNodeTypeDefinition())
                .collect(Collectors.toList());
    }

}
