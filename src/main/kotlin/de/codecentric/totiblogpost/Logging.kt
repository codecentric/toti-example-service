package de.codecentric.totiblogpost

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Obtain a logger instance for the class.
 */
@Suppress("unused")
inline val <reified T : Any> T.log: Logger
    get() = LoggerFactory.getLogger(T::class.java)
