package com.github.rkumsher.junit;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;

public class StatisticsRunListener extends RunListener {

  private static final Logger logger = LogManager.getLogger(StatisticsRunListener.class);
  private final List<Description> tests;
  private final boolean logConfigured;
  private int completedTestCount;

  public StatisticsRunListener(Description description) {
    tests = description.getChildren();
    Configuration config = ((LoggerContext) LogManager.getContext(false)).getConfiguration();
    logConfigured = config.getConfigurationSource() != ConfigurationSource.NULL_SOURCE;
  }

  @Override
  public void testFinished(Description description) throws Exception {
    super.testFinished(description);
    if (tests.contains(description)) {
      completedTestCount++;
      log("Executed %s / %s tests", completedTestCount, tests.size());
    }
  }

  private void log(String message, Object... params) {
    String formattedMessage = String.format(message, params);
    if (logConfigured) {
      logger.info(formattedMessage);
    } else {
      System.out.println(formattedMessage);
    }
  }
}
