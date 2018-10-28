package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class JcrNodeToDescription {

    static final String DEFAULT_DESCRIPTION = "default description";

    private final Map<JcrNodeId, String> jcrNodeIdToDescription;

    public JcrNodeToDescription(Map<JcrNodeId, String> jcrNodeIdToDescription) {
        this.jcrNodeIdToDescription = jcrNodeIdToDescription;
    }

    public String retrieveDescription(JcrNodeId jcrNodeId) {
        if (jcrNodeIdToDescription == null || !jcrNodeIdToDescription.containsKey(jcrNodeId)) {
            return DEFAULT_DESCRIPTION;
        }
        String descriptionsValue = jcrNodeIdToDescription.get(jcrNodeId);
        String[] descriptions = descriptionsValue.split(RetrieveJcrNodesDescription.DESCRIPTION_SEPARATOR);
        descriptionsValue = Arrays.stream(descriptions)
                .skip(1)
                .collect(Collectors.joining(RetrieveJcrNodesDescription.DESCRIPTION_SEPARATOR));
        if (descriptionsValue.isEmpty()) {
            jcrNodeIdToDescription.remove(jcrNodeId);
        } else {
            jcrNodeIdToDescription.put(jcrNodeId, descriptionsValue);
        }
        return descriptions[0];
    }

}
