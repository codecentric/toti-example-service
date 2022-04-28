package de.codecentric.totiblogpost.extensions

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class CatFactExtension : BeforeAllCallback {
    companion object {
        private const val port = 10011

        private var server: WireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(port))

        @JvmStatic
        private var init = false
    }

    override fun beforeAll(context: ExtensionContext?) {
        synchronized(CatFactExtension::class.java) {
            if (!init) {
                init = true
            }

            server.start()
        }
    }

    fun givenACatFact(
        catFact: String
    ) {
        server.stubFor(
            WireMock.get(WireMock.urlEqualTo("/fact"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            """{
                                   "fact": "$catFact",
                                   "length": ${catFact.length}
                               }""".trimIndent()
                        )
                )
        )


    }
}
