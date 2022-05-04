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

    @Test
    fun `should return 5 cat facts`() {
        val catFact = "this is my catfact"
        catFactExtension.givenACatFact(catFact = catFact)
        rest.webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/catfacts")
                    .queryParam("count", 5)
                    .build()
            }
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody()
            .jsonPath("$.catFacts.length()").isEqualTo(5)
            .jsonPath("$.catFacts[1].fact").isEqualTo(catFact)
    }

    @Test
    internal fun `should handle error`() {
        val catFact = "this is my catfact"
        catFactExtension.givenACatFact(catFact = catFact, status = 500)
        rest.webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/catfacts")
                    .queryParam("count", 5)
                    .build()
            }
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody()
            .jsonPath("$.message").isEqualTo("CatFact Service is not available.")
    }

    @Test
    internal fun `should handle empty response`() {
        val catFact = "this is my catfact"
        catFactExtension.givenAnEmptyCatFactResponse()
        rest.webTestClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/catfacts")
                    .queryParam("count", 5)
                    .build()
            }
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody()
            .jsonPath("$.message").isEqualTo("CatFact Service returned an empty response.")
    }
}
