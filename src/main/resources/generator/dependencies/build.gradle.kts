plugins {
  java
  checkstyle
  alias(libs.plugins.jib)
  alias(libs.plugins.protobuf)
  // jhipster-needle-gradle-plugin
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
    vendor = JvmVendorSpec.ADOPTIUM
  }
}

checkstyle {
  toolVersion = libs.versions.checkstyle.get()
}

// jhipster-needle-gradle-project-extension-plugin-configuration

repositories {
  mavenCentral()
  // jhipster-needle-gradle-repositories
}

group = "tech.jhipster.lite"
version = "0.0.1-SNAPSHOT"

ext {
  // jhipster-needle-gradle-properties
}

dependencies {
  implementation(libs.checkstyle)
  // jhipster-needle-gradle-dependencies
  // jhipster-needle-gradle-test-dependencies
}
