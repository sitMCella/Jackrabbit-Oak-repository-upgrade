package it.mcella.jcr.oak.upgrade.repository;

public class OakNodePath implements JcrNodePath {

    private static final String PATH_SEPARATOR = "/";

    private final String path;

    public OakNodePath(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getChildNodePath(String nodeName) {
        return path + PATH_SEPARATOR + nodeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof OakNodePath)) return false;

        OakNodePath oakNodePath = (OakNodePath) o;

        if (path != null ? !path.equals(oakNodePath.path) : oakNodePath.path != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OakNodePath{" +
                "path='" + path + '\'' +
                '}';
    }

}
