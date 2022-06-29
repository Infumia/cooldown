# cooldown
[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/tr.com.infumia/cooldown?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/tr.com.infumia/cooldown?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)
## How to Use (Developers)
### Maven
```xml
<dependencies>
  <dependency>
    <groupId>tr.com.infumia</groupId>
    <artifactId>cooldown</artifactId>
    <version>VERSION</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```
### Gradle
```groovy
plugins {
  id "java"
}

dependencies {
  compileOnly "tr.com.infumia:cooldown:VERSION"
}
```
