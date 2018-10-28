package it.mcella.jcr.oak.upgrade.repository.nodetypes.migration;

import it.mcella.jcr.oak.upgrade.repository.JcrNode;
import it.mcella.jcr.oak.upgrade.repository.JcrSession;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OakNodesToMigrate {

    private static final String JCR_SYSTEM = "jcr:system";
    private static final String REP_SECURITY = "rep:security";
    private static final String OAK_INDEX = "oak:index";
    private static final String ASYNC = ":async";
    private static final String REP_POLICY = "rep:policy";

    private static final List<String> PROTECTED_NODE_NAMES = Collections.unmodifiableList(
            Arrays.asList(JCR_SYSTEM, REP_SECURITY, OAK_INDEX, ASYNC, REP_POLICY)
    );
    private static final List<String> PROTECTED_CHILD_NODE_NAMES = Collections.singletonList(REP_POLICY);

    public List<JcrNode> retrieve(JcrSession jcrSession) throws RepositoryException {
        List<JcrNode> jcrNodesToMigrate = new ArrayList<>();
        JcrNode rootNode = jcrSession.getRootNode();
        List<JcrNode> rootChildNodes = rootNode.getChildNodes();
        for (JcrNode jcrNode : rootChildNodes) {
            if (!PROTECTED_NODE_NAMES.contains(jcrNode.getName())) {
                addChildNodesToMigrate(jcrNode, jcrNodesToMigrate);
            }
        }
        return jcrNodesToMigrate;
    }

    private void addChildNodesToMigrate(JcrNode jcrNode, List<JcrNode> jcrNodesToMigrate) throws RepositoryException {
        if (PROTECTED_CHILD_NODE_NAMES.contains(jcrNode.getName())) {
            return;
        }
        jcrNodesToMigrate.add(jcrNode);
        for (JcrNode jcrChildNode : jcrNode.getChildNodes()) {
            addChildNodesToMigrate(jcrChildNode, jcrNodesToMigrate);
        }
    }

}
