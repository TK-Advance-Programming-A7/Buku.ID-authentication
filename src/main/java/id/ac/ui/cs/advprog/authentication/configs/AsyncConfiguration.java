package id.ac.ui.cs.advprog.authentication.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration {
  @Primary
  @Bean(name = "taskExecutorDefault")
  ThreadPoolTaskExecutor taskExecutorDefault() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("Async-1-");
    executor.initialize();
    return executor;
  }

  @Bean(name = "taskExecutorForHeavyTasks")
  ThreadPoolTaskExecutor taskExecutorRegistration() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("Async2-");
    executor.initialize();
    return executor;
  }
}
