package com.github.rkumsher.junit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class StatisticsJUnitRunner extends BlockJUnit4ClassRunner {

  public StatisticsJUnitRunner(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  @Override
  public void run(RunNotifier notifier) {
    notifier.addListener(new StatisticsRunListener(getDescription()));
    super.run(notifier);
  }
}
