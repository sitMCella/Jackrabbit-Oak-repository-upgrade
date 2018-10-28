package it.mcella.jcr.oak.upgrade.repository.nodetypes.jackrabbit;

import it.mcella.jcr.oak.upgrade.repository.JcrSession;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeTypeDefinitionsReader;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.JcrNodeTypesRegister;
import it.mcella.jcr.oak.upgrade.repository.nodetypes.migration.OakNodeTypeDefinition;
import org.apache.jackrabbit.commons.cnd.CndImporter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OakNodeTypesRegister implements JcrNodeTypesRegister {

    private static final boolean RE_REGISTER_EXISTING = true;

    private final JcrNodeTypeDefinitionsReader jcrNodeTypeDefinitionsReader;

    public OakNodeTypesRegister(JcrNodeTypeDefinitionsReader jcrNodeTypeDefinitionsReader) {
        this.jcrNodeTypeDefinitionsReader = jcrNodeTypeDefinitionsReader;
    }

    @Override
    public String retrieveNamespaceUriFrom(InputStream namespaceConfiguration) throws JcrNodeTypeDefinitionsException {
        List<OakNodeTypeDefinition> nodeTypes = jcrNodeTypeDefinitionsReader.readFrom(namespaceConfiguration);
        if (!hasUniqueNamespace(nodeTypes)) {
            throw new JcrNodeTypeDefinitionsException(String.format("Namespace configuration contains more than one namespace definition"));
        }
        return nodeTypes.get(0).getNamespaceURI();
    }

    @Override
    public void registerNodeTypes(InputStream namespaceConfiguration, JcrSession jcrSession) throws JcrNodeTypesRegisterException {
        try (InputStreamReader inputStreamReader = new InputStreamReader(namespaceConfiguration)) {
            CndImporter.registerNodeTypes(inputStreamReader, jcrSession.getSession(), RE_REGISTER_EXISTING);
        } catch (Exception e) {
            throw new JcrNodeTypesRegisterException(e);
        }
    }

    private boolean hasUniqueNamespace(List<OakNodeTypeDefinition> nodeTypes) {
        Set<String> nodeTypeNamespaceURIs = nodeTypes.stream()
                .map(nodeType -> nodeType.getNamespaceURI())
                .collect(Collectors.toSet());
        return nodeTypeNamespaceURIs.size() == 1;
    }

}
