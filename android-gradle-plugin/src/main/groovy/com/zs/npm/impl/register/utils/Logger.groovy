package com.zs.npm.impl.register.utils
import org.gradle.api.Project
import org.gradle.api.logging.Logging

/**
 * log工具类
 */
class Logger {
    static org.gradle.api.logging.Logger logger

    static void make(Project project) {
        logger = Logging.getLogger "StartUp"
    }

    static void i(String info) {
        if (null != info && null != logger) {
            logger.lifecycle(":Register >>> " + info)
        }
    }

    static void e(String error) {
        if (null != error && null != logger) {
            logger.lifecycle("::Register >>> " + error)
        }
    }

    static void w(String warning) {
        if (null != warning && null != logger) {
            logger.lifecycle("::Register >>> " + warning)
        }
    }
}
