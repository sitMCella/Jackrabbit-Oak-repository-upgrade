package it.mcella.jcr.oak.upgrade.repository;

import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OakNodeVersion implements JcrNodeVersion {

    public static final String LAST_VERSION_LABEL = "last_version";

    static final String VERSION_LABEL_PATTERN = "version_";
    static final String FIRST_VERSION_LABEL = VERSION_LABEL_PATTERN + "1";

    private final Version version;

    public OakNodeVersion(Version version) {
        this.version = version;
    }

    @Override
    public JcrNodeId getJcrNodeId() throws RepositoryException {
        return new OakNodeId(version.getFrozenNode().getIdentifier());
    }

    @Override
    public long getVersionNumber() throws RepositoryException {
        List<String> versionLabels = Arrays.asList(version.getContainingHistory().getVersionLabels(version));
        return versionLabels.stream()
                .filter(versionLabel -> versionLabel.startsWith(VERSION_LABEL_PATTERN))
                .findFirst()
                .map(versionLabel -> Long.valueOf(versionLabel.substring(VERSION_LABEL_PATTERN.length())))
                .orElse(-1L);
    }

    @Override
    public String getName() throws RepositoryException {
        return version.getName();
    }

    @Override
    public String addVersionLabel() throws RepositoryException {
        String versionLabel = getVersionLabelFromLastVersionNode()
                .map(lastVersionNodeLabel -> getFollowingVersionLabelOf(lastVersionNodeLabel))
                .orElse(FIRST_VERSION_LABEL);
        VersionHistory versionHistory = version.getContainingHistory();
        versionHistory.addVersionLabel(version.getName(), versionLabel, false);
        versionHistory.addVersionLabel(version.getName(), LAST_VERSION_LABEL, true);
        return versionLabel;
    }

    @Override
    public JcrProperty getProperty(String propertyName) throws RepositoryException {
        return new OakProperty(version.getFrozenNode().getProperty(propertyName));
    }

    @Override
    public List<JcrProperty> getProperties() throws RepositoryException {
        PropertyIterator propertyIterator = version.getFrozenNode().getProperties();
        List<JcrProperty> jcrProperties = new ArrayList<>();
        while (propertyIterator.hasNext()) {
            jcrProperties.add(new OakProperty(propertyIterator.nextProperty()));
        }
        return jcrProperties;
    }

    @Override
    public List<String> getVersionLabels() throws RepositoryException {
        VersionHistory versionHistory = version.getContainingHistory();
        String[] versionLabels = versionHistory.getVersionLabels(version);
        return Arrays.stream(versionLabels)
                .filter(versionLabel -> versionLabel.startsWith(VERSION_LABEL_PATTERN) || versionLabel.equals(LAST_VERSION_LABEL))
                .collect(Collectors.toList());
    }

    @Override
    public void addVersionLabelsFrom(JcrNodeVersion jcrNodeVersion) throws RepositoryException {
        VersionHistory versionHistory = version.getContainingHistory();
        List<String> versionLabels = jcrNodeVersion.getVersionLabels();
        for (String versionLabel : versionLabels) {
            versionHistory.addVersionLabel(version.getName(), versionLabel, true);
        }
    }

    public Version getVersion() {
        return version;
    }

    private Optional<String> getVersionLabelFromLastVersionNode() throws RepositoryException {
        VersionHistory versionHistory = version.getContainingHistory();
        if (versionHistory.hasVersionLabel(LAST_VERSION_LABEL)) {
            Version lastVersion = versionHistory.getVersionByLabel(LAST_VERSION_LABEL);
            String[] versionLabels = versionHistory.getVersionLabels(lastVersion);
            return getVersionLabelFrom(versionLabels);
        }
        return Optional.empty();
    }

    private Optional<String> getVersionLabelFrom(String[] versionLabels) {
        return Arrays.stream(versionLabels)
                .filter(versionLabel -> !versionLabel.equals(LAST_VERSION_LABEL))
                .findFirst();
    }

    private String getFollowingVersionLabelOf(String lastVersionNodeLabel) {
        long lastVersionNumber = Long.parseLong(lastVersionNodeLabel.substring(VERSION_LABEL_PATTERN.length()));
        long followingVersionNumber = lastVersionNumber + 1L;
        return VERSION_LABEL_PATTERN + Long.toString(followingVersionNumber);
    }

}
