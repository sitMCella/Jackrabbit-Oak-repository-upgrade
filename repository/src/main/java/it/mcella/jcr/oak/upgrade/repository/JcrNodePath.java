package it.mcella.jcr.oak.upgrade.repository;

public interface JcrNodePath {

    String getPath();

    String getChildNodePath(String nodeName);

}
