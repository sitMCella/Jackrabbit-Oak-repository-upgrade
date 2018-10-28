package it.mcella.jcr.oak.upgrade.repository;

public interface JcrNodeType {

    String getName();

    boolean belongsTo(JcrNamespace jcrNamespace);

    boolean hasOrderableChildNodes();

}
