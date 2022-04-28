package de.codecentric.totiblogpost

import de.codecentric.totiblogpost.extensions.CatFactExtension
import de.codecentric.totiblogpost.extensions.DynamoDbLocalExtension
import de.codecentric.totiblogpost.extensions.WebTestClientExtention
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(DynamoDbLocalExtension::class)
class CatFactIntegrationTest {
    companion object {

        @JvmStatic
        @RegisterExtension
        val rest = WebTestClientExtention()

        @JvmField
        @RegisterExtension
        val catFactExtension = CatFactExtension()
    }

    @Test
    fun `should return a cat fact`() {
        val catFact = "this is my catfact"
        catFactExtension.givenACatFact(catFact = catFact)
        rest.webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/catfacts").build()
            }
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody().json(
                """
                {
                "catFacts": [{"fact":"$catFact","length":${catFact.length}}]
                }
            """.trimIndent()
            )

    }
}
