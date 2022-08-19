# Apache Jackrabbit Oak repository upgrade

Apache Jackrabbit Oak is an implementation of JCR 2.0 specification.
https://jackrabbit.apache.org/oak/

This project provides an example of Jackrabbit Oak repository upgrade.

Read the full [documentation](https://github.com/sitMCella/Jackrabbit-Oak-repository-upgrade/wiki/Home) of the project.

The project consists of two application versions.

The first application repository configuration file is "repository-first-version/src/main.resources/cnd.config".

The second application repository configuration file is "repository-second-version/src/main.resources/cnd.config".

The following are the main assumptions:
- The repository is an Oak Segment Tar (Tar file based Segment NodeStore)
- The repository contains custom node types
- The applications use the same Jackrabbit Oak version

The upgrade from the first repository to the second repository consists of:
- Remove the mixin "app:attributes" that contains the property "app:hidden"
- Add a new property "app:system" into an existent mixin "app:properties"
- Remove the property "app:deletable" from the mixin "app:properties"
- Add a new mixin "app:describable" with a property "app:description"

The repositories contain a versionable node "app:file" with a version-related mixin "app:versionInfo".

The upgrade procedure updates the node and node history properties definition. 

## Build project

mvn clean install

## Run the first application version

cd app-run-first-version/target

java -jar app-run-first-version-1.0-SNAPSHOT.jar

## Run the repository upgrade

cd repository-upgrade-second-version/target

java -jar repository-upgrade-second-version-1.0-SNAPSHOT.jar

## Run the second application version

cd app-run-second-version/target

java -jar app-run-second-version-1.0-SNAPSHOT.jar

## Check repository content with oak-run jar

Download oak-run-1.6.22.jar from https://search.maven.org/artifact/org.apache.jackrabbit/oak-run/1.6.22/jar

java -jar oak-run-1.6.22.jar explore /path/to/oak-repository/repository

## Custom node type registration

The custom node types can be registered into the content repository with a "Compact Namespace and Node Type Definition" 
file. See http://jackrabbit.apache.org/jcr/node-types.html
