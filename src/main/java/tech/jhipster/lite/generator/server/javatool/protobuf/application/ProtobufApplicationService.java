package tech.jhipster.lite.generator.server.javatool.protobuf.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.javatool.protobuf.domain.ProtobufModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class ProtobufApplicationService {

  private final ProtobufModuleFactory factory;

  public ProtobufApplicationService() {
    factory = new ProtobufModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
