package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.RepositoryException;
import java.util.List;

public interface JcrNode {

    JcrNodeId getNodeId() throws RepositoryException;

    JcrNodePath getNodePath() throws RepositoryException;

    String getName() throws RepositoryException;

    JcrNodeType getNodeType() throws RepositoryException;

    JcrNode addNode(String name, String type) throws RepositoryException;

    JcrNode addNodeWithNameNotEscaped(String name, String type) throws RepositoryException;

    boolean hasNode(String relativePath) throws RepositoryException;

    JcrNode getNode(String relativePath) throws RepositoryException;

    void setProperty(String name, String value) throws RepositoryException;

    void setProperty(String name, boolean value) throws RepositoryException;

    void setProperty(String name, JcrBinary binary) throws RepositoryException;

    void setProperty(JcrProperty jcrProperty) throws RepositoryException;

    boolean hasProperty(String name) throws RepositoryException;

    List<JcrProperty> getProperties() throws RepositoryException;

    JcrProperty getProperty(String name) throws RepositoryException;

    JcrBinary getBinary() throws RepositoryException;

    List<JcrNodeType> getMixinNodeTypes() throws RepositoryException;

    void addMixin(String name) throws RepositoryException;

    boolean isRoot() throws RepositoryException;

    boolean hasType(String type) throws RepositoryException;

    JcrNode getParent() throws RepositoryException;

    void save() throws RepositoryException;

    boolean isVersionable() throws RepositoryException;

    boolean isCheckedOut() throws RepositoryException;

    void checkout() throws RepositoryException;

    JcrNodeVersion checkin() throws RepositoryException;

    List<JcrNodeVersion> getVersions() throws RepositoryException;

    JcrNodeVersion getLastVersion() throws RepositoryException;

    void removeVersion(JcrNodeVersion jcrNodeVersion) throws RepositoryException;

    List<JcrNode> getChildNodes() throws RepositoryException;

    boolean checkNodeNameBelongsTo(JcrNamespace jcrNamespace) throws RepositoryException;

    void changeNodeType(JcrNamespace jcrNamespace) throws RepositoryException;

    void removeMixin(JcrNodeType jcrMixinNodeType) throws RepositoryException;

    void renameMixin(JcrNodeType jcrMixinNodeType, JcrNamespace jcrNamespace) throws RepositoryException;

    void rename(JcrNamespace jcrNamespace) throws RepositoryException;

    void orderBefore(JcrNode jcrFirstNode, JcrNode jcrSecondNode) throws RepositoryException;

    String getRelativePath() throws RepositoryException;

    String getChildNodePath(String nodeName) throws RepositoryException;

    void renameProperty(JcrProperty jcrProperty, JcrNamespace jcrNamespace) throws RepositoryException;

    void removeProperty(JcrProperty jcrProperty) throws RepositoryException;

    JcrValueFactory getJcrValueFactory() throws RepositoryException;

}
