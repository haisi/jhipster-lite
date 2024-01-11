package tech.jhipster.lite.generator.server.javatool.protobuf.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleWithFiles;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.pomFile;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@UnitTest
class ProtobufModuleFactoryTest {

  private static final ProtobufModuleFactory factory = new ProtobufModuleFactory();

  @AfterAll
  static void unregisterReaders() {
    TestJHipsterModules.unregisterReaders();
  }

  @Test
  void shouldBuildModule() {
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
      );
  }
}
