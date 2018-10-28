package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.RepositoryException;
import javax.jcr.Value;

public class JcrNamespace {

    static final String PREFIX_NAME_SEPARATOR = ":";

    private final String prefix;
    private final String uri;

    public JcrNamespace(String prefix, String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    public boolean isAppliedTo(JcrNodeType jcrNodeType) {
        return isAppliedTo(jcrNodeType.getName());
    }

    public boolean isAppliedTo(JcrNode jcrNode) throws RepositoryException {
        return isAppliedTo(jcrNode.getName());
    }

    public boolean isAppliedTo(JcrProperty jcrProperty) throws RepositoryException {
        return isAppliedTo(jcrProperty.getName());
    }

    public boolean isAppliedTo(Value value) throws RepositoryException {
        return isAppliedTo(value.getString());
    }

    public String getNodeTypeNameFrom(JcrNodeType jcrNodeType) throws RepositoryException {
        return getNameWithPrefixFrom(jcrNodeType.getName());
    }

    public String getNodeNameFrom(JcrNode jcrNode) throws RepositoryException {
        return getNameWithPrefixFrom(jcrNode.getName());
    }

    public String getPropertyNameFrom(JcrProperty jcrProperty) throws RepositoryException {
        return getNameWithPrefixFrom(jcrProperty.getName());
    }

    public String getValueNameFrom(Value value) throws RepositoryException {
        return getNameWithPrefixFrom(value.getString());
    }

    public String getNodePath(JcrNode jcrNode) throws RepositoryException {
        JcrNode jcrParentNode = jcrNode.getParent();
        return jcrParentNode.getChildNodePath(getNodeNameFrom(jcrNode));
    }

    public boolean hasUri(String uri) {
        return this.uri.equals(uri);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JcrNamespace that = (JcrNamespace) o;

        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prefix != null ? prefix.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JcrNamespace{" +
                "prefix='" + prefix + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }

    private boolean isAppliedTo(String name) {
        return name.startsWith(prefix + PREFIX_NAME_SEPARATOR);
    }

    private String getNameWithPrefixFrom(String name) {
        return prefix + PREFIX_NAME_SEPARATOR + getNameWithoutPrefix(name);
    }

    private String getNameWithoutPrefix(String name) {
        int offset = name.indexOf(PREFIX_NAME_SEPARATOR);
        return name.substring(offset + 1);
    }

}
