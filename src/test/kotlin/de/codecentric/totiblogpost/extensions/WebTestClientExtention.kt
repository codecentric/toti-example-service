package de.codecentric.totiblogpost.extensions

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration


class WebTestClientExtention : BeforeAllCallback {

    lateinit var webTestClient: WebTestClient

    override fun beforeAll(context: ExtensionContext) {
        val port = SpringExtension.getApplicationContext(context).environment.getProperty("local.server.port", Int::class.java)
        webTestClient = WebTestClient
            .bindToServer()
            .baseUrl("http://localhost:${port}")
            .responseTimeout(Duration.ofMillis(30000))
            .build()
    }
}
