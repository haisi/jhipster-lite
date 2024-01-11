Feature: Protobuf module

  Scenario: Should apply protobuf module
    When I apply modules to default project
      | maven-java |
      | protobuf   |
    Then I should have files in "src/main/java/tech/jhipster/chips/shared/protobuf/infrastructure"
      | primary/ProtobufDatesReader.java   |
      | secondary/ProtobufDatesWriter.java |
