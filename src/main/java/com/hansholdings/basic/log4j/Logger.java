package com.hansholdings.basic.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

public class Logger {

    private org.apache.log4j.Logger log4jLogger = null;

    protected Logger(org.apache.log4j.Logger log4jLogger) {
        this.log4jLogger = log4jLogger;
    }

    /**
     * 
     * 功能描述: <br>
     * 
     * <pre>
     * 例子：输出你的名字是halo 
     * <code>
     *  Logger.info("你的名字是%s","halo");
     * </code>
     * 
     * </pre>
     *
     * @param message 内容格式
     * @param args 参数
     * @see String#format(String, Object...)
     * @since 2016-06-03
     */
    public void info(String message, Object... args) {
        log(Level.INFO, message, args);
    }

    /**
     * 
     * @param message
     * @param args
     * @see #info
     */
    public void debug(String message, Object... args) {
        log(Level.DEBUG, message, args);
    }

    /**
     * 
     * @param message
     * @param args
     * @see #info
     */
    public void warn(String message, Object... args) {
        log(Level.WARN, message, args);
    }

    /**
     * 
     * @param message
     * @param args
     * @see #info
     */
    public void error(String message, Object... args) {
        log(Level.ERROR, message, args);
    }

    /**
     * 
     * @param message
     * @param e
     * @param args
     * @see #info
     */
    public void info(String message, Throwable e, Object... args) {
        log(Level.INFO, message, e, args);
    }

    /**
     * 
     * @param message
     * @param e
     * @param args
     * @see #info
     */
    public void debug(String message, Throwable e, Object... args) {
        log(Level.DEBUG, message, e, args);
    }

    /**
     * 
     * @param message
     * @param e
     * @param args
     * @see #info
     */
    public void warn(String message, Throwable e, Object... args) {
        log(Level.WARN, message, e, args);
    }

    /**
     * 
     * @param message
     * @param e
     * @param args
     * @see #info
     */
    public void error(String message, Throwable e, Object... args) {
        log(Level.ERROR, message, e, args);
    }

    /**
     * 
     * @param message
     * @param e
     * @param args
     * @see #info
     */
    public void log(Level level, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        log4jLogger.log(level, message);
    }

    /**
     * 
     * @param message
     * @param e
     * @param args
     * @see #info
     */
    public void log(Level level, String message, Throwable e, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        log4jLogger.log(level, message, e);
    }

    /**
     * 
     * @param message
     * @param e
     * @param args
     * @see #info
     */
    public static Logger getLogger(Class<?> clazz) {
        return new Logger(LogManager.getLogger(clazz.getName()));
    }
}