package it.mcella.jcr.oak.upgrade.repository.secondversion.update;

import it.mcella.jcr.oak.upgrade.repository.JcrNamespace;

public class JcrUpgradeConstants {

    public static final JcrNamespace JCR_OLD_NAMESPACE = new JcrNamespace("app", "http://www.jcr.mcella.it/repository-upgrade/1.0");

    public static final JcrNamespace JCR_INTERMEDIATE_NAMESPACE = new JcrNamespace("intermediate", "http://www.jcr.mcella.it/repository-upgrade/2.0");

    public static final JcrNamespace JCR_NEW_NAMESPACE = new JcrNamespace("app", "http://www.jcr.mcella.it/repository-upgrade/1.0");

}
