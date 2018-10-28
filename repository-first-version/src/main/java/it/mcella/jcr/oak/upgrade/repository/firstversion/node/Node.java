package it.mcella.jcr.oak.upgrade.repository.firstversion.node;

import it.mcella.jcr.oak.upgrade.repository.JcrNodeId;

import java.util.List;

public interface Node {

    JcrNodeId getJcrNodeId();

    List<JcrNodeId> getChildNodeIds();

}
