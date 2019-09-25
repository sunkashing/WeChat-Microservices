package edu.cmu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
/**
 * Config async to handle tasks.
 *
 * @author lucas
 */
public class AsyncConfig implements AsyncConfigurer {

    /**
     * logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

    /**
     * Get the executor, customizing thread pool.
     */
    @Override
    public Executor getAsyncExecutor() {
        return new ConcurrentTaskExecutor(
                Executors.newFixedThreadPool(2));
    }

    /**
     * Add exception handler.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }

    /**
     * Handle async method exception.
     */
    static class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        /**
         * Log the exception information.
         */
        @Override
        public void handleUncaughtException(
                Throwable throwable, Method method, Object... obj) {

            LOGGER.debug("Exception message - " + throwable.getMessage());
            LOGGER.debug("Method name - " + method.getName());
            for (Object param : obj) {
                LOGGER.debug("Parameter value - " + param);
            }
        }
    }
}
