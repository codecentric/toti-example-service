package de.codecentric.totiblogpost

import org.springframework.util.SocketUtils
import java.util.*


class IdGenerator {
    companion object {
        @Synchronized
        // TODO migrate to TestSocketUtils once its released. See: https://github.com/spring-projects/spring-framework/issues/28052
        fun nextPort(): Int = SocketUtils.findAvailableTcpPort(10001)

        fun nextAsString(): String = UUID.randomUUID().toString()
        fun nextAsLong(): Long = Random().nextLong()

    }
}
