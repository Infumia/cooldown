# cooldown
[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/tr.com.infumia/cooldown?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/tr.com.infumia/cooldown?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)
## How to Use (Developers)
### Maven
```xml
<dependencies>
  <dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>guava</artifactId>
    <version>3.1.1</version>
  </dependency>
  <dependency>
    <groupId>tr.com.infumia</groupId>
    <artifactId>cooldown</artifactId>
    <version>VERSION</version>
  </dependency>
</dependencies>
```
### Gradle
```groovy
plugins {
  id "java"
}

dependencies {
  implementation "com.github.ben-manes.caffeine:guava:3.1.1"
  implementation "tr.com.infumia:cooldown:VERSION"
}
```
