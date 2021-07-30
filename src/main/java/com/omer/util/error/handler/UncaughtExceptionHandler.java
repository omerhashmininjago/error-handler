package com.omer.util.error.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * <p>
 *     The {@link UncaughtExceptionHandler} would need to be set
 *     as the default uncaught exception handler
 * </p>
 * <p>
 *     This would need to be done by passing the instance
 *     UNCAUGHT_EXCEPTION_HANDLER to Thread.setDefaultUncaughtExceptionHandler()
 * </p>
 * <p>
 *     This would need to be set in the main method before the spring container
 *     starts coming up
 * </p>
 * <hr><blockquote><pre>
 *     public static void main(String[] args) {
 *         Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
 *
 *         ......
 *
 *     }
 * </pre></blockquote><hr>
 */
public enum UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    UNCAUGHT_EXCEPTION_HANDLER;

    private static final Logger LOG = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

    /**
     * this method takes care of unhandled exceptions from any thread not managed by a
     * custom ThreadPool executor
     *
     * @param t Thread in which the uncaught exception occured
     * @param e Exception
     */
    public void uncaughtException(Thread t, Throwable e) {
        LOG.error("Uncaught exception; thread {} :: exception {} ", t.getName(), ExceptionUtils.getRootCauseMessage(e), e);
    }

    /**
     * this method will take care of unhandled exceptions from threads managed by custom
     * threadPool executors
     */
    public Function<Throwable, Void> logUncaughtException() {
        return exception -> {
            LOG.error("Uncaught exception :: {}", exception.getCause(), exception);
            return null;
        };
    }
}
