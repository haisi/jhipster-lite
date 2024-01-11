package tech.jhipster.lite.generator.server.javatool.protobuf.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.gradleLibsVersionFile;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class ProtobufModuleFactoryTest {

  private static final ProtobufModuleFactory factory = new ProtobufModuleFactory();

  @Test
  void shouldBuildModuleForMaven() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/shared/protobuf",
        "package-info.java",
        "infrastructure/primary/ProtobufDatesReader.java",
        "infrastructure/secondary/ProtobufDatesWriter.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/shared/protobuf",
        "infrastructure/primary/ProtobufDatesReaderTest.java",
        "infrastructure/secondary/ProtobufDatesWriterTest.java"
      )
      .hasFiles("src/main/proto/.gitkeep")
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>com.google.protobuf</groupId>
              <artifactId>protobuf-java</artifactId>
              <version>${protobuf.version}</version>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>com.google.protobuf</groupId>
              <artifactId>protobuf-java-util</artifactId>
              <version>${protobuf.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .containing(
        """
                <plugin>
                  <groupId>org.xolstice.maven.plugins</groupId>
                  <artifactId>protobuf-maven-plugin</artifactId>
                  <version>${protobuf-maven-plugin.version}</version>
                  <executions>
                    <execution>
                      <goals>
                        <goal>compile</goal>
                      </goals>
                    </execution>
                  </executions>
                  <configuration>
                    <protocArtifact>com.google.protobuf:protoc:${protobuf.version}:exe:${os.detected.classifier}</protocArtifact>
                  </configuration>
                </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>org.xolstice.maven.plugins</groupId>
                <artifactId>protobuf-maven-plugin</artifactId>
              </plugin>
        """
      )
      .containing(
        """
          <build>
            <extensions>
              <extension>
                <groupId>kr.motd.maven</groupId>
                <artifactId>os-maven-plugin</artifactId>
                <version>${os-maven-plugin.version}</version>
              </extension>
            </extensions>
        """
      )
      .containing(
        """
                <plugin>
                  <groupId>com.salesforce.servicelibs</groupId>
                  <artifactId>proto-backwards-compatibility</artifactId>
                  <version>${proto-backwards-compatibility.version}</version>
                  <executions>
                    <execution>
                      <goals>
                        <goal>backwards-compatibility-check</goal>
                      </goals>
                    </execution>
                  </executions>
                </plugin>
        """
      )
      .containing(
        """
              <plugin>
                <groupId>com.salesforce.servicelibs</groupId>
                <artifactId>proto-backwards-compatibility</artifactId>
              </plugin>
        """
      );
  }

  @Test
  void shouldBuildModuleForGradle() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, gradleBuildFile(), gradleLibsVersionFile())
      .hasFile("gradle/libs.versions.toml")
      .containing("protobuf = \"")
      .containing("protobuf-plugin = \"")
      .containing(
        """
        \t[plugins.protobuf]
        \t\tid = "com.google.protobuf"

        \t\t[plugins.protobuf.version]
        \t\t\tref = "protobuf-plugin"
        """
      )
      .and()
      .hasFile("build.gradle.kts")
      .containing(
        """
          alias(libs.plugins.protobuf)
          // jhipster-needle-gradle-plugins
        """
      );
  }
}
