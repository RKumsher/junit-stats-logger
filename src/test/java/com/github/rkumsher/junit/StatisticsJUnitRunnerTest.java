package com.github.rkumsher.junit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsJUnitRunnerTest {

  @Mock private RunNotifier mockNotifier;
  private StatisticsJUnitRunner runner;

  @Before
  public void setUp() throws InitializationError {
    runner = new StatisticsJUnitRunner(getClass());
  }

  @Test
  public void run_RegistersStatisticsRunListener() {
    runner.run(mockNotifier);
    verify(mockNotifier).addListener(any(StatisticsRunListener.class));
  }
}
