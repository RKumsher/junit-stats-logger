package com.github.rkumsher.junit;

import static com.github.rkumsher.collection.RandomCollectionUtils.randomListFrom;
import static org.apache.logging.log4j.Level.ALL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.rkumsher.collection.IterableUtils;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsRunListenerTest {

  private static final Integer TEST_COUNT = RandomUtils.nextInt(1, 10);
  @Mock private Appender mockAppender;
  @Mock private Description mockDescription;
  @Captor private ArgumentCaptor<LogEvent> captor;
  private List<Description> children = randomListFrom(this::createDescription, TEST_COUNT);

  @Before
  public void setUp() {
    when(mockAppender.getName()).thenReturn("MockAppender");
    when(mockAppender.isStarted()).thenReturn(true);
    Configuration config = ((LoggerContext) LogManager.getContext(false)).getConfiguration();
    LoggerConfig loggerConfig = config.getLoggerConfig(StatisticsRunListener.class.getName());
    loggerConfig.addAppender(mockAppender, ALL, null);
    when(mockDescription.getChildren()).thenReturn((ArrayList<Description>) children);
  }

  private Description createDescription() {
    return Description.createSuiteDescription(RandomStringUtils.random(10));
  }

  @Test
  public void testFinished_LogsNumberOfTestsExecuted() throws Exception {
    StatisticsRunListener runListener = new StatisticsRunListener(mockDescription);
    runListener.testFinished(IterableUtils.randomFrom(children));
    assertThat(getLoggedMessage(), is("Executed 1 / " + TEST_COUNT + " tests"));
    runListener.testFinished(IterableUtils.randomFrom(children));
    assertThat(getLoggedMessage(), is("Executed 2 / " + TEST_COUNT + " tests"));
  }

  private String getLoggedMessage() {
    verify(mockAppender, atLeastOnce()).append(captor.capture());
    return captor.getValue().getMessage().getFormattedMessage();
  }
}
