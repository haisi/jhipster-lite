package tech.jhipster.lite.generator.server.javatool.protobuf.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCommunityPlugin;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin;
import tech.jhipster.lite.module.domain.mavenplugin.MavenPlugin.MavenPluginOptionalBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.shared.error.domain.Assert;

public class ProtobufModuleFactory {

  private static final JHipsterSource SOURCE = from("server/javatool/protobuf");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final String PROTOBUF_PACKAGE = "shared/protobuf";
  public static final VersionSlug PROTOBUF_VERSION_SLUG = versionSlug("protobuf");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    JHipsterDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(PROTOBUF_PACKAGE);
    JHipsterDestination testDestination = toSrcTestJava().append(properties.packagePath()).append(PROTOBUF_PACKAGE);

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .add(MAIN_SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
        .add(MAIN_SOURCE.template("ProtobufDatesReader.java"), mainDestination.append("infrastructure/primary/ProtobufDatesReader.java"))
        .add(MAIN_SOURCE.template("ProtobufDatesWriter.java"), mainDestination.append("infrastructure/secondary/ProtobufDatesWriter.java"))
        .add(MAIN_SOURCE.append(".gitkeep"), to("src/main/proto/.gitkeep"))
        .add(
          TEST_SOURCE.template("ProtobufDatesReaderTest.java"),
          testDestination.append("infrastructure/primary/ProtobufDatesReaderTest.java")
        )
        .add(
          TEST_SOURCE.template("ProtobufDatesWriterTest.java"),
          testDestination.append("infrastructure/secondary/ProtobufDatesWriterTest.java")
        )
        .and()
      .javaDependencies()
        .addDependency(groupId("com.google.protobuf"), artifactId("protobuf-java"), PROTOBUF_VERSION_SLUG)
        .addTestDependency(groupId("com.google.protobuf"), artifactId("protobuf-java-util"), PROTOBUF_VERSION_SLUG)
        .and()
      .mavenPlugins()
        .pluginManagement(protobufMavenPluginManagement())
        .plugin(protobufMavenPluginBuilder().build())
        .pluginManagement(protoBackwardsCompatibilityMavenPluginManagement())
        .plugin(protoBackwardsCompatibilityMavenPluginBuilder().build())
        .and()
      .mavenBuildExtensions()
        .addExtension(groupId("kr.motd.maven"), artifactId("os-maven-plugin"), versionSlug("os-maven-plugin"))
        .and()
      .gradlePlugins()
        .plugin(protobufGradlePlugin())
        .and()
      .build();
    //@formatter:on
  }

  private static GradleCommunityPlugin protobufGradlePlugin() {
    return gradleCommunityPlugin()
      .id("com.google.protobuf")
      .pluginSlug("protobuf")
      .versionSlug("protobuf-plugin")
      .configuration(
        """
        protobuf {
          protoc {
            artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.asProvider().get()}"
          }
        }
        """
      )
      .build();
  }

  private MavenPlugin protobufMavenPluginManagement() {
    return protobufMavenPluginBuilder()
      .versionSlug("protobuf-maven-plugin")
      .configuration(
        """
        <protocArtifact>com.google.protobuf:protoc:${protobuf.version}:exe:${os.detected.classifier}</protocArtifact>
        """
      )
      .addExecution(pluginExecution().goals("compile"))
      .build();
  }

  private MavenPluginOptionalBuilder protobufMavenPluginBuilder() {
    return javaBuildPlugin().groupId("org.xolstice.maven.plugins").artifactId("protobuf-maven-plugin");
  }

  private MavenPlugin protoBackwardsCompatibilityMavenPluginManagement() {
    return protoBackwardsCompatibilityMavenPluginBuilder()
      .versionSlug("proto-backwards-compatibility")
      .addExecution(pluginExecution().goals("backwards-compatibility-check"))
      .build();
  }

  private MavenPluginOptionalBuilder protoBackwardsCompatibilityMavenPluginBuilder() {
    return javaBuildPlugin().groupId("com.salesforce.servicelibs").artifactId("proto-backwards-compatibility");
  }
}
