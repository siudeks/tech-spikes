package net.siudek.demoappinsights;

import java.util.Map;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyRun implements ApplicationRunner {

  private String instrumentationKey;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // var config = new TelemetryConfiguration();
    // config.setInstrumentationKey(instrumentationKey);
    // config.setRoleName("my role name");

    // var client = new TelemetryClient();
    // var props = Map.ofEntries(
    //   Map.entry("prop-1", "prop-1-value"),
    //   Map.entry("prop-2", "prop-2-value"));
    // var metrics = Map.ofEntries(
    //     Map.entry("metric-1", 1.0 ),
    //     Map.entry("metric-2", 2.0));
    // client.trackEvent("app startup",  props, metrics);
    // client.flush();
  }
  
}
